/**
 * ���ܣ��������һ�����ܹ��ﳵ�ı����ݵ���
 * �ļ���CartForm.java
 * ʱ�䣺2015��6��6��10:30:10
 * ���ߣ�cutter_point
 */
package com.cutter_point.web.formbean.cart;

import com.cutter_point.web.formbean.BaseForm;

public class CartForm extends BaseForm 
{
	private Integer productid;  	//��Ʒ��id
	private Integer styleid;	//��ʽ��id
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
		//productid-styleid���и�ʽ���趨
		if(this.buyitemid != null)
		{
			String[] values = this.buyitemid.split("-");
			if(values.length == 2)
			{
				//��ȡ������ָ���趨����Ӧ����������
				this.productid = new Integer(values[0]);
				this.styleid = new Integer(values[1]);
			}
		}
	}
	
}
