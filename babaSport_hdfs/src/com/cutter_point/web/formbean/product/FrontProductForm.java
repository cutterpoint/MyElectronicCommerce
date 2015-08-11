/**
 * 功能：这个是用来接收前台传过来的表单数据
 * 时间：2015年6月3日10:18:18
 * 文件：FrontProductForm.java
 * 作者：cutter_point
 */
package com.cutter_point.web.formbean.product;

import com.cutter_point.web.formbean.BaseForm;

public class FrontProductForm extends BaseForm 
{
	/** 一个参数用来判断是什么样的排序 **/
	private String sort;
	/** 类别id号 **/
	private Integer typeid;
	/** 获取到的品怕id **/
	private String brandid;
	/** 获取到的性别要求 **/
	private String sex;
	/** 商品的id **/
	private Integer productid;
	/** 显示的样式是图文版还是图片版  **/
	private String showstyle;

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public String getShowstyle() {
		return showstyle;
	}

	public void setShowstyle(String showstyle) {
		this.showstyle = showstyle;
	}
	
}
