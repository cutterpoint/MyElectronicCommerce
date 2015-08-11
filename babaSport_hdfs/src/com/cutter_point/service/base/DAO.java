/**
 * ���ܣ����Ƕ����ݿ������ɾ�Ĳ�Ľӿ���
 * �ļ���DAO.java
 * ʱ�䣺2015��5��14��18:51:24
 * ���ߣ�cutter_point
 */
package com.cutter_point.service.base;

import java.util.LinkedHashMap;

import com.cutter_point.bean.QueryResult;

public interface DAO
{
	/**
	 * ����һ��ʵ����
	 * @param entity ʵ����
	 */
	public void save(Object entity);
	
	/**
	 * ����id��ɾ������
	 * @param entityClass ���
	 * @param entityid ʵ����id��
	 */
	public <T> void delete(Class<T> entityClass, Object entityid);
	
	/**
	 * ��������idɾ��һ������
	 * @param entityClass ���
	 * @param entityids id�ŵ�����
	 */
	public <T> void delete(Class<T> entityClass, Object[] entityids);
	
	/**
	 * ����ʵ�����޸���Ӧ������
	 * @param entity ʵ����
	 */
	public void update(Object entity);
	
	/**
	 * ��������id�����Ҳ�ͬ������µ�ʵ��
	 * @param entityClass ���
	 * @param entityId ʵ��id
	 * @return ����һ��ʵ����
	 */
	public <T> T find(Class<T> entityClass, Object entityId);
	
	/**
	 * ����Ƕ���Ӧ��ʵ��bean���з���
	 * @param entityClass ʵ������
	 * @param firstindex  ��һ������
	 * @param maxresult		��Ҫ��ȡ�ļ�¼��
	 * @return
	 */
	//���һ���������ǣ��ж��������ǽ���
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult,String whereSql, Object[] queryParams,
			LinkedHashMap<String, String> orderby);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult,String whereSql, Object[] queryParams);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult, LinkedHashMap<String, String> orderby);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass);
}
