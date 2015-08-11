/**
 * ���ܣ����ǲ�Ʒ�ķ�����
 * �ļ���ProductInfoServiceBean.java
 * ʱ�䣺2015��5��27��10:17:45
 * ���ߣ�cutter_point
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

@Service	//�൱����spring���涨��һ��bean,����ע��ķ�ʽ<context:component-scan base-package="com.cutter_point" />
@Transactional		//�ڷ���ִ�е�ʱ��������
public class ProductInfoServiceBean extends DaoSupport implements ProductInfoService
{
	@Resource
	private ProductTypeService productTypeService;	//ҵ��ע��
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)	//����������������ύ
	public List<ProductInfo> getViewHistory(Integer[] productids, int maxResult)
	{
		Session session = this.sessionFactory.getCurrentSession();	//�õ���ǰ��session����
		
		StringBuilder sql = new StringBuilder();	//������װ��Ӧ��sql���
		for(int i = 0; i < productids.length; ++i)
		{
			sql.append("?,");
		}
		//ȥ�����һ������
		sql.deleteCharAt(sql.length() - 1);
		Query query = session.createSQLQuery("select * from productinfo o where o.id in("+ sql.toString() +")").addEntity(ProductInfo.class);
		for(int i = 0; i < productids.length; ++i)
		{
			query.setParameter(i, productids[i]);
		}
		//��ѯ����
		query.setFirstResult(0).setMaxResults(maxResult);	//�趨��ѯ����ʼλ�ú͸���
		return query.list();		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)	//����������������ύ
	public List<ProductInfo> getTopSell(Integer typeid, int maxResult)
	{
		Session session = this.sessionFactory.getCurrentSession();	//�õ���ǰ��session����
		
		//��ȡ���е����࣬�������������
		List<Integer> typeids = new ArrayList<Integer>();
		List<ProductInfo> ls = new ArrayList<ProductInfo>();
		//���ȰѶ���Ҫ��ѯ�ĸ���Ž�ȥ
		//�ж��ǲ�����typeid
		if(typeid != null)
		{
			typeids.add(typeid);
			this.getTypeids(typeids, new Integer[]{typeid});
			//�ɲ�ѯ�������������ͺŲ�ѯ����
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < typeids.size(); ++i)
			{
				sb.append("?,");
			}
			//ȥ������
			sb.deleteCharAt(sb.length() - 1);
			Query query = session.createSQLQuery("select * from productinfo o where o.commend=? and "
					+ "	typeid in ("+ sb.toString() +") order by o.sellcount").addEntity(ProductInfo.class);
			//���ò���
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
	 * ��ѯ�����е�����id(���������ȫ����ȡ)
	 * @param outtypeids	����ǲ�ѯ���������е��й�id��
	 * @param typeids	����id
	 * @return
	 */
	private void getTypeids(List<Integer> outtypeids, Integer[] typeids)
	{
		//���Ȳ������id����������id
		List<Integer> subtypeids = productTypeService.getSubTypeid(typeids);
		//ֻҪ�����������id�Ų�Ϊ��˵��������ܻ������࣬һֱ�����һ��֮���ѯ������Ϊֹ
		if(subtypeids != null && subtypeids.size() > 0)
		{
			//�ɲ�ѯ����������ų��������д��
			outtypeids.addAll(subtypeids);
			//Ȼ��Ѳ�ѯ���������൱��������в�ѯ
			Integer[] ids = new Integer[subtypeids.size()];
			for(int i = 0; i < subtypeids.size(); ++i)
			{
				ids[i] = Integer.valueOf(subtypeids.get(i).toString());
			}
			getTypeids(outtypeids, ids);	//��Listת��Ϊ����
		}
	}

	@Override
	public void setVisibleStatus(Integer[] productids, boolean status) 
	{
		Session session = this.sessionFactory.getCurrentSession();	//�õ���ǰ��session����
		
		//��װsql���
		if(productids != null && productids.length > 0)
		{
			StringBuilder sql = new StringBuilder();
			//ѭ���趨?
			for(int i = 0; i < productids.length; ++i)
			{
				sql.append(" ?,");
			}
			//���һ��,����ɾ��
			sql.deleteCharAt(sql.length() - 1);	//���������0��ʼ���������һ���ǳ���-1
			Query query = session.createSQLQuery("update productinfo o set o.visible = ? where o.id in ("+ sql.toString() +")");  		//��SQL�������޸�
			//�����ʺ�����Ķ����
			query.setParameter(0, status);
			for(int i = 0; i < productids.length; ++i)
			{
				//?������0��ʼ
				query.setParameter(i + 1, productids[i]);
			}
			//ִ��sql��������ݿ��и��½��
			query.executeUpdate();
		}
	}

	@Override
	public void setCommendStatus(Integer[] productids, boolean status) 
	{
		Session session = this.sessionFactory.getCurrentSession();	//�õ���ǰ��session����
		
		//��װsql���
		if(productids != null && productids.length > 0)
		{
			StringBuilder sql = new StringBuilder();
			//ѭ���趨?
			for(int i = 0; i < productids.length; ++i)
			{
				sql.append(" ?,");
			}
			//���һ��,����ɾ��
			sql.deleteCharAt(sql.length() - 1);	//���������0��ʼ���������һ���ǳ���-1
			Query query = session.createSQLQuery("update productinfo o set o.commend = ? where o.id in ("+ sql.toString() +")");  		//��SQL�������޸�
			//�����ʺ�����Ķ����
			query.setParameter(0, status);
			for(int i = 0; i < productids.length; ++i)
			{
				//?������0��ʼ
				query.setParameter(i + 1, productids[i]);
			}
			//ִ��sql��������ݿ��и��½��
			query.executeUpdate();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)	//����������������ύ
	public List<Brand> getBrandsByProductTypeid(Integer[] typeids) 
	{
		Session session = this.sessionFactory.getCurrentSession();	//�õ���ǰ��session
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < typeids.length; ++i)
		{
			//��װsql���
			sb.append(" ?,");
		}
		sb.deleteCharAt(sb.length() - 1);
		//��֯��Ӧ��sql���
		String sql = "select * from brand o where o.code in ( select p.brandid from productinfo p where typeid in ("+ sb.toString() +") group by p.brandid)";
		Query query = session.createSQLQuery(sql).addEntity(Brand.class);	//Ԥ����ָ��,�����һ��addEntity��Ϊ�˴����ݿ��ѯ�����Ķ�����һ��ʵ����󣬽���ת��
		//������Ӧ�Ĳ���
		for(int i = 0; i < typeids.length; ++i)
		{
			//?������0��ʼ
			query.setParameter(i, typeids[i]);
		}
		//���ؽ����		
		return query.list();
	}

}
