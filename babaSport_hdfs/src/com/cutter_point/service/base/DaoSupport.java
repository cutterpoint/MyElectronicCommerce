/**
 * ���ܣ����Ƕ����ݿ������ɾ�Ĳ�ĳ����࣬ʵ��һЩ����
 * �ļ���DaoSupport.java
 * ʱ�䣺2015��5��14��19:03:52
 * ���ߣ�cutter_point
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

@Transactional		//�����������ķ����ύ��spring������������������
public abstract class DaoSupport implements DAO
{
	//ͨ��springȡ��sessionFactory
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
	 * ��������
	 */
	@Override
	public <T> void delete(Class<T> entityClass, Object entityid)
	{
		this.delete(entityClass, new Object[]{entityid});
	}

	/**
	 * �ر�����
	 */
	@Override
	public <T> void delete(Class<T> entityClass, Object[] entityids)
	{
		Session session = sessionFactory.getCurrentSession();
		for(Object id : entityids)
		{
			//ѭ��ɾ����Ӧ��id��
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
	 * �����������Ҫ��������,���Ҳ���������ݿ������
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
	 * ��firstindex��ʼ����ѯ����Ϊmaxresult�Ľ��������������ҳ
	 * @param entityClass	ʵ������
	 * @param firstindex	�ӵڼ�����ʼ��ѯ
	 * @param maxresult	��ѯ����Ŀ
	 * @param whereSql	����Ԥ�����ָ��������?
	 * @param queryParams	SQL��where ����ӵ��������ϣ���Ҫ��ʲô����
	 * @param orderby	����ķ�ʽ�����򣬽����������Ǹ�Ԫ��Ϊ��׼��Ȼ�����Ǹ�Ԫ��Ϊ��׼
	 * @return QueryResult<T> ����һ������long��List�����͵��࣬���淵�صĽ�������ͼ�¼�ĸ���
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult,String whereSql, Object[] queryParams,
			LinkedHashMap<String, String> orderby)
	{
		Session s = sessionFactory.getCurrentSession();	//�õ���ǰ������
		QueryResult qr = new QueryResult<T>();	//�����
		String entityname = this.getEntityName(entityClass);
		Query query = s.createSQLQuery("select * from "+entityname+" o " + (whereSql == null ? "" : "where " + whereSql) + this.buildOrderBy(orderby)).addEntity(entityClass);
		this.setQueryParams(query, queryParams);
		//�����ʼ����������-1�����߲�ѯ�ĸ�������-1
		if(firstindex != -1 && maxresult != -1)
		{
			//��ȡ��һ���������,�Լ����û�ȡ����Ŀ
			query.setFirstResult(firstindex).setMaxResults(maxresult);
		}
		//��ȡ�����ֵ��qr
		List<T> ls = query.list();
		qr.setResultList(ls);
		//����������Ҫȡ����¼����Ŀ
		query = s.createSQLQuery("select count(*) from "+entityname+" o " + (whereSql == null ? "" : "where " + whereSql));
		this.setQueryParams(query, queryParams);
//		System.out.println(query.uniqueResult());
		//����һ���������͵�ת��
		String st = query.uniqueResult().toString();
		qr.setTotalrecord(Long.valueOf(st));	//ȡ�õ�һ�Ľ��
		
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
	 * ���������������SQL��������кܶ������������ʱ����������Ԥ�����?
	 * @param query
	 * @param queryParams
	 */
	protected void setQueryParams(Query query, Object[] queryParams)
	{
		if(queryParams != null && queryParams.length > 0)
		{
			//�������Ҫ���Ƶ�Ԫ�ظ������ǿգ���ô�Ϳ���ѭ��
			for(int i = 0; i < queryParams.length; ++i)
			{
				query.setParameter(i, queryParams[i]);		//��?һ����������
			}
		}
	}
	
	/**
	 * ��װorder by���
	 * @param orderby
	 * @return
	 */
	//��ɵ���������Ȱ�ʲô����Ȼ��ʲôkey����
	protected String buildOrderBy(LinkedHashMap<String, String> orderby)
	{
		StringBuilder orderbyq1 = new StringBuilder();	//�Ȼ�����������
		if(orderby != null && orderby.size() > 0)
		{
			orderbyq1.append(" order by ");//ע�����ո���ú���ֱ�Ӽ���ȥ֮����Ǹ�by������һ��
			//map����Ϊ��
			for(String key : orderby.keySet())
			{
				//ƴ�����
				orderbyq1.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
			}
			orderbyq1.deleteCharAt(orderbyq1.length() - 1);		//ȥ�������Ǹ�����
		}
		return orderbyq1.toString();
	}
	
	/**
	 * ��ȡʵ�������
	 * @param entityClass ʵ����
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
