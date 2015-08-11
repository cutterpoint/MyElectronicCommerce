/**
 * 功能：这是文件管理的服务类
 * 文件：UploadFileServiceBean.java
 * 时间：2015年5月25日09:32:38
 * 作者：cutter_point
 */
package com.cutter_point.service.uploadfile.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cutter_point.service.base.DaoSupport;
import com.cutter_point.service.uploadfile.UploadFileService;

//为这个类的每个方法添加事务，并且把这个类交给spring托管
@Service
@Transactional
public class UploadFileServiceBean extends DaoSupport implements UploadFileService
{
	//有一个方法可以根据id数组来批量获取数据
	@SuppressWarnings("unchecked")
	public List<String> getFilepath(Integer[] ids)
	{
		//首先ids不是空的，那么就可以执行下面操作
		if(ids != null && ids.length > 0)
		{
			Session session = this.sessionFactory.getCurrentSession();	//得到数据库的连接
			StringBuilder sql = new StringBuilder();
			//我们把相应的数据要一个一个地取出来，就得拼接SQL语句
			for(int i = 0; i < ids.length; ++i)
			{
				sql.append(" ?").append(",");
			}
			sql.deleteCharAt(sql.length() - 1);	//去掉最后的“，”
			//执行语句得到相应结果
			Query query = session.createSQLQuery("select o.filepath from uploadfile o where id in (" + sql + ")");
			for(int i = 0; i < ids.length; ++i)
			{
				query.setParameter(i, ids[i]);	//为每个?依次填入相应的值
			}
			return query.list();		//返回的结果集
		}
		return null;
	}

}
