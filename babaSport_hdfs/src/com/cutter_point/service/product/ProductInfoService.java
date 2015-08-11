/**
 * 功能：这个是产品业务的接口
 * 文件：ProductInfoService.java
 * 时间：2015年5月22日16:48:25
 * 作者：cutter_point
 */
package com.cutter_point.service.product;

import java.util.List;

import com.cutter_point.bean.product.Brand;
import com.cutter_point.bean.product.ProductInfo;
import com.cutter_point.service.base.DAO;

public interface ProductInfoService extends DAO
{
	//这里面定义ProductInfoService专有的方法
	
	/**
	 * 这个是为了设置产品是否上架的功能
	 * @param productids 设置的一堆产品的id号
	 * @param status 设置状态
	 */
	public void setVisibleStatus(Integer[] productids, boolean status);
	
	/**
	 * 设置产品是否要推荐
	 * @param productids 设置的一堆产品的id号
	 * @param status 设置状态
	 */
	public void setCommendStatus(Integer[] productids, boolean status);
	
	/**
	 * 这个是根据类别的ids号获取对应的品牌
	 * @param typeid
	 * @return
	 */
	public List<Brand> getBrandsByProductTypeid(Integer[] typeids);
	
	/**
	 * 获取销量最多并且被推荐的产品
	 * @param typeid 类别id
	 * @param maxResult 获取的产品数量
	 * @return
	 */
	public List<ProductInfo> getTopSell(Integer typeid, int maxResult);
	
	/**
	 * 获取指定的id的产品信息
	 * @param productids 产品的id数组
	 * @param maxResult 最大的获取的记录数
	 * @return
	 */
	public List<ProductInfo> getViewHistory(Integer[] productids, int maxResult);

}
