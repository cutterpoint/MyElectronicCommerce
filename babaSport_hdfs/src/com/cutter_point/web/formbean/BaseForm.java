/**
 * 功能：用来接受从表单提交上来的数据
 * 文件：BaseForm.java
 * 时间：2015年5月27日19:35:02
 * 作者：cutter_poin
 */
package com.cutter_point.web.formbean;

public class BaseForm
{
	/** 获取当前页 **/
	private int page;
	/** 设置是否进行查找 **/
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
