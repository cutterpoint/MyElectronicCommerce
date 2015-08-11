/**
 * 功能：这是对数据库进行增删改查的抽象类，实现一些方法
 * 文件：DaoSupport.java
 * 时间：2015年5月14日19:03:52
 * 作者：cutter_point
 */
package com.cutter_point.service.base;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Entity;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cutter_point.bean.QueryResult;

@Transactional		//吧这个类里面的方法提交给spring管理，方法都开上事务
public abstract class DaoSupport implements DAO
{
	//通过spring取得sessionFactory
	@Resource
	public SessionFactory sessionFactory;

	@Override
	public void save(Object entity)
	{
		Session session = sessionFactory.getCurrentSession();
		session.persist(entity);
		//session.save(entity);
	}

	/**
	 * 开启事务
	 */
	@Override
	public <T> void delete(Class<T> entityClass, Object entityid)
	{
		this.delete(entityClass, new Object[]{entityid});
	}

	/**
	 * 关闭事务
	 */
	@Override
	public <T> void delete(Class<T> entityClass, Object[] entityids)
	{
		Session session = sessionFactory.getCurrentSession();
		for(Object id : entityids)
		{
			//循环删除相应的id号
			session.delete(session.get(entityClass, (Serializable) id));
		}
	}

	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,
			int firstindex, int maxresult)
	{
		return this.getScrollData(entityClass, firstindex, maxresult, null, null, null);
	}

	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass)
	{
		return this.getScrollData(entityClass, -1, -1);
	}

	@Override
	public void update(Object entity)
	{
		Session session = sessionFactory.getCurrentSession();
		session.merge(entity);
	}

	/**
	 * 这个方法不需要开启事务,而且不会更改数据库的数据
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
	@Override
	public <T> T find(Class<T> entityClass, Object entityId)
	{
		Session session = sessionFactory.getCurrentSession();
		return (T) session.get(entityClass, (Serializable) entityId);
	}
	
	/**
	 * 从firstindex开始，查询长度为maxresult的结果出来，用来分页
	 * @param entityClass	实体类型
	 * @param firstindex	从第几个开始查询
	 * @param maxresult	查询的数目
	 * @param whereSql	就是预处理的指令，里面包含?
	 * @param queryParams	SQL中where 后面接的条件集合，需要传什么参数
	 * @param orderby	排序的方式，升序，降序，首先以那个元素为基准，然后以那个元素为基准
	 * @return QueryResult<T> 这是一个包含long和List的类型的类，保存返回的结果集，和记录的个数
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult,String whereSql, Object[] queryParams,
			LinkedHashMap<String, String> orderby)
	{
		Session s = sessionFactory.getCurrentSession();	//得到当前的连接
		QueryResult qr = new QueryResult<T>();	//结果集
		String entityname = this.getEntityName(entityClass);
		Query query = s.createSQLQuery("select * from "+entityname+" o " + (whereSql == null ? "" : "where " + whereSql) + this.buildOrderBy(orderby)).addEntity(entityClass);
		this.setQueryParams(query, queryParams);
		//如果初始的索引不是-1，或者查询的个数不是-1
		if(firstindex != -1 && maxresult != -1)
		{
			//获取第一个结果索引,以及设置获取的数目
			query.setFirstResult(firstindex).setMaxResults(maxresult);
		}
		//获取结果赋值给qr
		List<T> ls = query.list();
		qr.setResultList(ls);
		//接下来我们要取出记录的数目
		query = s.createSQLQuery("select count(*) from "+entityname+" o " + (whereSql == null ? "" : "where " + whereSql));
		this.setQueryParams(query, queryParams);
//		System.out.println(query.uniqueResult());
		//进行一下数据类型的转换
		String st = query.uniqueResult().toString();
		qr.setTotalrecord(Long.valueOf(st));	//取得单一的结果
		
		return qr;
	}
	
	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,
			int firstindex, int maxresult, String whereSql, Object[] queryParams)
	{
		return this.getScrollData(entityClass, firstindex, maxresult, whereSql, queryParams, null);
	}

	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,
			int firstindex, int maxresult, LinkedHashMap<String, String> orderby)
	{
		return this.getScrollData(entityClass, firstindex, maxresult, null, null, orderby);
	}

	/**
	 * 这个就是用来处理SQL语句里面有很多的限制条件的时候，用这个来填补预处理的?
	 * @param query
	 * @param queryParams
	 */
	protected void setQueryParams(Query query, Object[] queryParams)
	{
		if(queryParams != null && queryParams.length > 0)
		{
			//如果我们要限制的元素个数不是空，那么就可以循环
			for(int i = 0; i < queryParams.length; ++i)
			{
				query.setParameter(i, queryParams[i]);		//吧?一个个地填上
			}
		}
	}
	
	/**
	 * 组装order by语句
	 * @param orderby
	 * @return
	 */
	//组成的语句是首先按什么排序，然后按什么key排序
	protected String buildOrderBy(LinkedHashMap<String, String> orderby)
	{
		StringBuilder orderbyq1 = new StringBuilder();	//等会用来组合语句
		if(orderby != null && orderby.size() > 0)
		{
			orderbyq1.append(" order by ");//注意留空格，免得后面直接加上去之后和那个by连在了一起
			//map不是为空
			for(String key : orderby.keySet())
			{
				//拼接语句
				orderbyq1.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
			}
			orderbyq1.deleteCharAt(orderbyq1.length() - 1);		//去掉最后的那个逗号
		}
		return orderbyq1.toString();
	}
	
	/**
	 * 获取实体的名称
	 * @param entityClass 实体类
	 * @return
	 */
	protected <T> String getEntityName(Class<T> entityClass)
	{
		String entityname = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		if(entity.name() != null && !"".equals(entity.name()))
		{
			entityname = entity.name();
		}
		
		return entityname;
	}
}
