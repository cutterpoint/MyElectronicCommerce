/**
 * ���ܣ��������ܴӱ��ύ����������
 * �ļ���BaseForm.java
 * ʱ�䣺2015��5��27��19:35:02
 * ���ߣ�cutter_poin
 */
package com.cutter_point.web.formbean;

public class BaseForm
{
	/** ��ȡ��ǰҳ **/
	private int page;
	/** �����Ƿ���в��� **/
	private String query;
	
	public BaseForm()
	{
	}
	public int getPage()
	{
		return page < 1 ? 1 : page;
	}
	public void setPage(int page)
	{
		this.page = page;
	}
	public String getQuery()
	{
		return query;
	}
	public void setQuery(String query)
	{
		this.query = query;
	}
}
