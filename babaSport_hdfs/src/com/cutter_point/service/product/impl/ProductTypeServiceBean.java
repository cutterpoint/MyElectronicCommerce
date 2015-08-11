/**
 * 功能：这是产品类别的服务类
 * 文件：ProductService.java
 * 时间：2015年5月13日15:36:06
 * 作者：cutter_point
 */
package com.cutter_point.service.product.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cutter_point.service.base.DaoSupport;
import com.cutter_point.service.product.ProductTypeService;

@Service		//相当于在spring里面定义一个bean,这是注解的方式<context:component-scan base-package="com.cutter_point" />
@Transactional		//在方法执行的时候开启事务
public class ProductTypeServiceBean extends DaoSupport implements ProductTypeService
{
	/**
	 * 当我们删除的时候，会吧相应的对象进行假删除，也就是把相应的对象设置为不可见
	 */
	@Override
	public <T> void delete(Class<T> entityClass, Object[] entityids)
	{
		//取得和数据库的连接
		Session s = sessionFactory.getCurrentSession();
		//我们首先判断这个传进来的id不是空的
		if(entityids != null && entityids.length > 0)
		{
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < entityids.length; ++i)
			{
				sb.append("?").append(",");
			}
			//在这个语句添加到最后的时候会多余一个,
			sb.deleteCharAt(sb.length()-1);	//删除最后一个就是,号
			Query query = s.createSQLQuery("update producttype p set p.visible = ? where p.typeid in("+sb.toString()+")").setParameter(0, false);
			for(int i = 0; i < entityids.length; ++i)
			{
				query.setParameter(i + 1, entityids[i]);
			}
			query.executeUpdate();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
	public List<Integer> getSubTypeid(Integer[] parentids)
	{
		//获取数据库的链接
		Session session = this.sessionFactory.getCurrentSession();	//得到当前的session连接
		List<Integer> ls = new ArrayList<Integer>();
		if(parentids != null && parentids.length > 0)
		{
			//查询所有的子类id号
			StringBuilder sql = new StringBuilder();
			for(int i = 0; i < parentids.length; ++i)
			{
				sql.append("?,");
			}
			//去掉最后一个逗号
			sql.deleteCharAt(sql.length() - 1);
			//得到相应的预处理指令
			Query query = session.createSQLQuery("select o.typeid from producttype o where parentid in ("+ sql.toString() +")");
			//设置参数
			for(int i = 0; i < parentids.length; ++i)
			{
				query.setParameter(i, parentids[i]);	//查找类别相应id号的子类id
			}
			//设置返回类型
			List<BigDecimal> lb = query.list();
			for(int i = 0; i < lb.size(); ++i)
			{
				ls.add(lb.get(i).intValue());
			}
		}
		return ls;
	}
}
