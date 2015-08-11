/**
 * 功能：这个类是一个接受购物车的表单数据的类
 * 文件：CartForm.java
 * 时间：2015年6月6日10:30:10
 * 作者：cutter_point
 */
package com.cutter_point.web.formbean.cart;

import com.cutter_point.web.formbean.BaseForm;

public class CartForm extends BaseForm 
{
	private Integer productid;  	//产品的id
	private Integer styleid;	//样式的id
	private String buyitemid;
	
	public Integer getProductid() {
		return productid;
	}
	public void setProductid(Integer productid) {
		this.productid = productid;
	}
	public Integer getStyleid() {
		return styleid;
	}
	public void setStyleid(Integer styleid) {
		this.styleid = styleid;
	}
	public String getBuyitemid() {
		return buyitemid;
	}
	public void setBuyitemid(String buyitemid) 
	{
		this.buyitemid = buyitemid;
		//productid-styleid这中格式来设定
		if(this.buyitemid != null)
		{
			String[] values = this.buyitemid.split("-");
			if(values.length == 2)
			{
				//吧取出来的指端设定到相应的数据里面
				this.productid = new Integer(values[0]);
				this.styleid = new Integer(values[1]);
			}
		}
	}
	
}
