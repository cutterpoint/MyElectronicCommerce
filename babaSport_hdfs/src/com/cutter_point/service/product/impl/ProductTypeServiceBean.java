/**
 * ���ܣ����ǲ�Ʒ���ķ�����
 * �ļ���ProductService.java
 * ʱ�䣺2015��5��13��15:36:06
 * ���ߣ�cutter_point
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

@Service		//�൱����spring���涨��һ��bean,����ע��ķ�ʽ<context:component-scan base-package="com.cutter_point" />
@Transactional		//�ڷ���ִ�е�ʱ��������
public class ProductTypeServiceBean extends DaoSupport implements ProductTypeService
{
	/**
	 * ������ɾ����ʱ�򣬻����Ӧ�Ķ�����м�ɾ����Ҳ���ǰ���Ӧ�Ķ�������Ϊ���ɼ�
	 */
	@Override
	public <T> void delete(Class<T> entityClass, Object[] entityids)
	{
		//ȡ�ú����ݿ������
		Session s = sessionFactory.getCurrentSession();
		//���������ж������������id���ǿյ�
		if(entityids != null && entityids.length > 0)
		{
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < entityids.length; ++i)
			{
				sb.append("?").append(",");
			}
			//����������ӵ�����ʱ������һ��,
			sb.deleteCharAt(sb.length()-1);	//ɾ�����һ������,��
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
		//��ȡ���ݿ������
		Session session = this.sessionFactory.getCurrentSession();	//�õ���ǰ��session����
		List<Integer> ls = new ArrayList<Integer>();
		if(parentids != null && parentids.length > 0)
		{
			//��ѯ���е�����id��
			StringBuilder sql = new StringBuilder();
			for(int i = 0; i < parentids.length; ++i)
			{
				sql.append("?,");
			}
			//ȥ�����һ������
			sql.deleteCharAt(sql.length() - 1);
			//�õ���Ӧ��Ԥ����ָ��
			Query query = session.createSQLQuery("select o.typeid from producttype o where parentid in ("+ sql.toString() +")");
			//���ò���
			for(int i = 0; i < parentids.length; ++i)
			{
				query.setParameter(i, parentids[i]);	//���������Ӧid�ŵ�����id
			}
			//���÷�������
			List<BigDecimal> lb = query.list();
			for(int i = 0; i < lb.size(); ++i)
			{
				ls.add(lb.get(i).intValue());
			}
		}
		return ls;
	}
}
