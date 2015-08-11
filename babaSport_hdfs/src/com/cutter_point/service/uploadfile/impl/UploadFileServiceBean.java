/**
 * ���ܣ������ļ�����ķ�����
 * �ļ���UploadFileServiceBean.java
 * ʱ�䣺2015��5��25��09:32:38
 * ���ߣ�cutter_point
 */
package com.cutter_point.service.uploadfile.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cutter_point.service.base.DaoSupport;
import com.cutter_point.service.uploadfile.UploadFileService;

//Ϊ������ÿ������������񣬲��Ұ�����ཻ��spring�й�
@Service
@Transactional
public class UploadFileServiceBean extends DaoSupport implements UploadFileService
{
	//��һ���������Ը���id������������ȡ����
	@SuppressWarnings("unchecked")
	public List<String> getFilepath(Integer[] ids)
	{
		//����ids���ǿյģ���ô�Ϳ���ִ���������
		if(ids != null && ids.length > 0)
		{
			Session session = this.sessionFactory.getCurrentSession();	//�õ����ݿ������
			StringBuilder sql = new StringBuilder();
			//���ǰ���Ӧ������Ҫһ��һ����ȡ�������͵�ƴ��SQL���
			for(int i = 0; i < ids.length; ++i)
			{
				sql.append(" ?").append(",");
			}
			sql.deleteCharAt(sql.length() - 1);	//ȥ�����ġ�����
			//ִ�����õ���Ӧ���
			Query query = session.createSQLQuery("select o.filepath from uploadfile o where id in (" + sql + ")");
			for(int i = 0; i < ids.length; ++i)
			{
				query.setParameter(i, ids[i]);	//Ϊÿ��?����������Ӧ��ֵ
			}
			return query.list();		//���صĽ����
		}
		return null;
	}

}
