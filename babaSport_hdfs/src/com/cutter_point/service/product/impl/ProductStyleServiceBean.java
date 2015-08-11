/**
 * 功能：这是品牌样式的服务类
 * 文件：ProductStyleServiceBean.java
 * 时间：2015年5月31日19:34:15
 * 作者：cutter_point
 */
package com.cutter_point.service.product.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cutter_point.service.base.DaoSupport;
import com.cutter_point.service.product.ProductStyleService;

@Service	//相当于在spring里面定义一个bean,这是注解的方式<context:component-scan base-package="com.cutter_point" />
@Transactional		//在方法执行的时候开启事务
public class ProductStyleServiceBean extends DaoSupport implements	ProductStyleService 
{

	@Override
	public void setVisibleStatus(Integer[] productstyleids, boolean status) 
	{
		//获取数据库的链接
		Session session = this.sessionFactory.getCurrentSession();	//得到当前的session连接
		
		//组装sql语句
		if(productstyleids != null && productstyleids.length > 0)
		{
			//组建相应的sql语句
			StringBuilder sql = new StringBuilder();
			for(int i = 0; i < productstyleids.length; ++i)
			{
				sql.append(" ?,");
			}
			//去掉最后一个","
			sql.deleteCharAt(sql.length() - 1);	//这个索引从0开始，所以最后一个是长度-1
			//得到相应的预处理指令
			Query query = session.createSQLQuery("update productstyle o set o.visible = ? where o.id in ( " + sql.toString() + ")");
			//设置上下架
			query.setParameter(0, status);
			for(int i = 0; i < productstyleids.length; ++i)
			{
				//?计数从0开始
				query.setParameter(i + 1, productstyleids[i]);
			}
			//执行sql语句在数据库中更新结果
			query.executeUpdate();
		}
	}
}
