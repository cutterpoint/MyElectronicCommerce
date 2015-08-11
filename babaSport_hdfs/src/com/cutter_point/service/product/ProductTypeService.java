/**
 * 功能：这个是产品功能的接口
 * 时间：2015年5月25日09:29:07
 * 文件：ProductTypeService.java
 * 作者：cutter_point
 */
package com.cutter_point.service.product;

import java.util.List;

import com.cutter_point.service.base.DAO;

public interface ProductTypeService extends DAO
{
	//这里面定义ProductTypeService专有的方法
	/**
	 * 获取类别ids下的子类别id
	 * @param parentids 父类id数组
	 * @return
	 */
	public List<Integer> getSubTypeid(Integer[] parentids);
}