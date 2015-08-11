/**
 * 功能：这个是品牌样式的接口
 * 文件：ProductStyleService.java
 * 时间：2015年5月31日19:33:19
 * 作者：cutter_point
 */
package com.cutter_point.service.product;

import com.cutter_point.service.base.DAO;

public interface ProductStyleService extends DAO
{
	//这里面定义ProductStyleService专有的方法
	/**
	 * 这个是为了设置产品是否上架的功能
	 * @param productids 设置的一堆产品的id号
	 * @param status 设置状态
	 */
	public void setVisibleStatus(Integer[] productstyleids, boolean status);
}
