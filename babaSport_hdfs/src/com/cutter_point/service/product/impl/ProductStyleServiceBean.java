/**
 * ���ܣ�����Ʒ����ʽ�ķ�����
 * �ļ���ProductStyleServiceBean.java
 * ʱ�䣺2015��5��31��19:34:15
 * ���ߣ�cutter_point
 */
package com.cutter_point.service.product.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cutter_point.service.base.DaoSupport;
import com.cutter_point.service.product.ProductStyleService;

@Service	//�൱����spring���涨��һ��bean,����ע��ķ�ʽ<context:component-scan base-package="com.cutter_point" />
@Transactional		//�ڷ���ִ�е�ʱ��������
public class ProductStyleServiceBean extends DaoSupport implements	ProductStyleService 
{

	@Override
	public void setVisibleStatus(Integer[] productstyleids, boolean status) 
	{
		//��ȡ���ݿ������
		Session session = this.sessionFactory.getCurrentSession();	//�õ���ǰ��session����
		
		//��װsql���
		if(productstyleids != null && productstyleids.length > 0)
		{
			//�齨��Ӧ��sql���
			StringBuilder sql = new StringBuilder();
			for(int i = 0; i < productstyleids.length; ++i)
			{
				sql.append(" ?,");
			}
			//ȥ�����һ��","
			sql.deleteCharAt(sql.length() - 1);	//���������0��ʼ���������һ���ǳ���-1
			//�õ���Ӧ��Ԥ����ָ��
			Query query = session.createSQLQuery("update productstyle o set o.visible = ? where o.id in ( " + sql.toString() + ")");
			//�������¼�
			query.setParameter(0, status);
			for(int i = 0; i < productstyleids.length; ++i)
			{
				//?������0��ʼ
				query.setParameter(i + 1, productstyleids[i]);
			}
			//ִ��sql��������ݿ��и��½��
			query.executeUpdate();
		}
	}
}
