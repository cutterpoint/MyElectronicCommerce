/**
 * ���ܣ��������������ǰ̨�������ı�����
 * ʱ�䣺2015��6��3��10:18:18
 * �ļ���FrontProductForm.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.web.formbean.product;

import com.cutter_point.web.formbean.BaseForm;

public class FrontProductForm extends BaseForm 
{
	/** һ�����������ж���ʲô�������� **/
	private String sort;
	/** ���id�� **/
	private Integer typeid;
	/** ��ȡ����Ʒ��id **/
	private String brandid;
	/** ��ȡ�����Ա�Ҫ�� **/
	private String sex;
	/** ��Ʒ��id **/
	private Integer productid;
	/** ��ʾ����ʽ��ͼ�İ滹��ͼƬ��  **/
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
