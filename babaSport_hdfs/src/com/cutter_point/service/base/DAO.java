/**
 * 功能：这是对数据库进行增删改查的接口类
 * 文件：DAO.java
 * 时间：2015年5月14日18:51:24
 * 作者：cutter_point
 */
package com.cutter_point.service.base;

import java.util.LinkedHashMap;

import com.cutter_point.bean.QueryResult;

public interface DAO
{
	/**
	 * 保存一个实体类
	 * @param entity 实体类
	 */
	public void save(Object entity);
	
	/**
	 * 根据id号删除数据
	 * @param entityClass 类别
	 * @param entityid 实体类id号
	 */
	public <T> void delete(Class<T> entityClass, Object entityid);
	
	/**
	 * 根据数组id删除一堆数据
	 * @param entityClass 类别
	 * @param entityids id号的数组
	 */
	public <T> void delete(Class<T> entityClass, Object[] entityids);
	
	/**
	 * 更具实体类修改相应的数据
	 * @param entity 实体类
	 */
	public void update(Object entity);
	
	/**
	 * 根据类别和id来查找不同的类别下的实体
	 * @param entityClass 类别
	 * @param entityId 实体id
	 * @return 返回一个实体类
	 */
	public <T> T find(Class<T> entityClass, Object entityId);
	
	/**
	 * 这个是对相应的实体bean进行分类
	 * @param entityClass 实体类型
	 * @param firstindex  第一个索引
	 * @param maxresult		需要获取的记录数
	 * @return
	 */
	//最后一个参数就是，判定是升序还是降序
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult,String whereSql, Object[] queryParams,
			LinkedHashMap<String, String> orderby);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult,String whereSql, Object[] queryParams);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult, LinkedHashMap<String, String> orderby);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass);
}
