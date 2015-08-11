/**
 * 功能：这是产品的服务类
 * 文件：ProductInfoServiceBean.java
 * 时间：2015年5月27日10:17:45
 * 作者：cutter_point
 */
package com.cutter_point.service.product.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cutter_point.bean.product.Brand;
import com.cutter_point.bean.product.ProductInfo;
import com.cutter_point.service.base.DaoSupport;
import com.cutter_point.service.product.ProductInfoService;
import com.cutter_point.service.product.ProductTypeService;

@Service	//相当于在spring里面定义一个bean,这是注解的方式<context:component-scan base-package="com.cutter_point" />
@Transactional		//在方法执行的时候开启事务
public class ProductInfoServiceBean extends DaoSupport implements ProductInfoService
{
	@Resource
	private ProductTypeService productTypeService;	//业务注入
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)	//这个方法不用事务提交
	public List<ProductInfo> getViewHistory(Integer[] productids, int maxResult)
	{
		Session session = this.sessionFactory.getCurrentSession();	//得到当前的session连接
		
		StringBuilder sql = new StringBuilder();	//用来组装相应的sql语句
		for(int i = 0; i < productids.length; ++i)
		{
			sql.append("?,");
		}
		//去掉最后一个逗号
		sql.deleteCharAt(sql.length() - 1);
		Query query = session.createSQLQuery("select * from productinfo o where o.id in("+ sql.toString() +")").addEntity(ProductInfo.class);
		for(int i = 0; i < productids.length; ++i)
		{
			query.setParameter(i, productids[i]);
		}
		//查询数据
		query.setFirstResult(0).setMaxResults(maxResult);	//设定查询的起始位置和个数
		return query.list();		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)	//这个方法不用事务提交
	public List<ProductInfo> getTopSell(Integer typeid, int maxResult)
	{
		Session session = this.sessionFactory.getCurrentSession();	//得到当前的session连接
		
		//获取所有的子类，包括子类的子类
		List<Integer> typeids = new ArrayList<Integer>();
		List<ProductInfo> ls = new ArrayList<ProductInfo>();
		//首先把顶级要查询的父类放进去
		//判断是不是有typeid
		if(typeid != null)
		{
			typeids.add(typeid);
			this.getTypeids(typeids, new Integer[]{typeid});
			//吧查询出来的所有类型号查询出来
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < typeids.size(); ++i)
			{
				sb.append("?,");
			}
			//去掉逗号
			sb.deleteCharAt(sb.length() - 1);
			Query query = session.createSQLQuery("select * from productinfo o where o.commend=? and "
					+ "	typeid in ("+ sb.toString() +") order by o.sellcount").addEntity(ProductInfo.class);
			//设置参数
			query.setParameter(0, true);
			for(int i = 0; i < typeids.size(); ++i)
			{
				query.setParameter(i + 1, typeids.get(i));
			}
			query.setFirstResult(0).setMaxResults(maxResult);
			ls = query.list();
		}
		
		return ls;
	}
	
	
	/**
	 * 查询出所有的子类id(子类的子类全部获取)
	 * @param outtypeids	这个是查询出来的所有的有关id号
	 * @param typeids	父类id
	 * @return
	 */
	private void getTypeids(List<Integer> outtypeids, Integer[] typeids)
	{
		//首先查出父类id的所有子类id
		List<Integer> subtypeids = productTypeService.getSubTypeid(typeids);
		//只要查出来的子类id号不为空说明子类可能还有子类，一直把最后一层之类查询不出来为止
		if(subtypeids != null && subtypeids.size() > 0)
		{
			//吧查询出来的子类放出到参数中存放
			outtypeids.addAll(subtypeids);
			//然后把查询出来的子类当做父类进行查询
			Integer[] ids = new Integer[subtypeids.size()];
			for(int i = 0; i < subtypeids.size(); ++i)
			{
				ids[i] = Integer.valueOf(subtypeids.get(i).toString());
			}
			getTypeids(outtypeids, ids);	//吧List转化为数组
		}
	}

	@Override
	public void setVisibleStatus(Integer[] productids, boolean status) 
	{
		Session session = this.sessionFactory.getCurrentSession();	//得到当前的session连接
		
		//组装sql语句
		if(productids != null && productids.length > 0)
		{
			StringBuilder sql = new StringBuilder();
			//循环设定?
			for(int i = 0; i < productids.length; ++i)
			{
				sql.append(" ?,");
			}
			//最后一个,多余删除
			sql.deleteCharAt(sql.length() - 1);	//这个索引从0开始，所以最后一个是长度-1
			Query query = session.createSQLQuery("update productinfo o set o.visible = ? where o.id in ("+ sql.toString() +")");  		//用SQL语句进行修改
			//设置问号里面的额参数
			query.setParameter(0, status);
			for(int i = 0; i < productids.length; ++i)
			{
				//?计数从0开始
				query.setParameter(i + 1, productids[i]);
			}
			//执行sql语句在数据库中更新结果
			query.executeUpdate();
		}
	}

	@Override
	public void setCommendStatus(Integer[] productids, boolean status) 
	{
		Session session = this.sessionFactory.getCurrentSession();	//得到当前的session连接
		
		//组装sql语句
		if(productids != null && productids.length > 0)
		{
			StringBuilder sql = new StringBuilder();
			//循环设定?
			for(int i = 0; i < productids.length; ++i)
			{
				sql.append(" ?,");
			}
			//最后一个,多余删除
			sql.deleteCharAt(sql.length() - 1);	//这个索引从0开始，所以最后一个是长度-1
			Query query = session.createSQLQuery("update productinfo o set o.commend = ? where o.id in ("+ sql.toString() +")");  		//用SQL语句进行修改
			//设置问号里面的额参数
			query.setParameter(0, status);
			for(int i = 0; i < productids.length; ++i)
			{
				//?计数从0开始
				query.setParameter(i + 1, productids[i]);
			}
			//执行sql语句在数据库中更新结果
			query.executeUpdate();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)	//这个方法不用事务提交
	public List<Brand> getBrandsByProductTypeid(Integer[] typeids) 
	{
		Session session = this.sessionFactory.getCurrentSession();	//得到当前的session
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < typeids.length; ++i)
		{
			//组装sql语句
			sb.append(" ?,");
		}
		sb.deleteCharAt(sb.length() - 1);
		//组织相应的sql语句
		String sql = "select * from brand o where o.code in ( select p.brandid from productinfo p where typeid in ("+ sb.toString() +") group by p.brandid)";
		Query query = session.createSQLQuery(sql).addEntity(Brand.class);	//预处理指令,后面加一个addEntity是为了从数据库查询出来的对象是一个实体对象，进行转化
		//设置相应的参数
		for(int i = 0; i < typeids.length; ++i)
		{
			//?计数从0开始
			query.setParameter(i, typeids[i]);
		}
		//返回结果集		
		return query.list();
	}

}
