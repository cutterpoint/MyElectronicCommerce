/**
 * ���ܣ���ŵ�һ��ҳ������һ��ҳ��
 * ʱ�䣺2015��5��19��09:06:26
 * �ļ���WebTool.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.bean;

public class PageIndex
{
	private long startpage;
	private long endpage;
	
	
	
	public PageIndex()
	{
	}

	public PageIndex(long startpage, long endpage)
	{
		this.startpage = startpage;
		this.endpage = endpage;
	}
	
	public long getStartpage()
	{
		return startpage;
	}
	public void setStartpage(long startpage)
	{
		this.startpage = startpage;
	}
	public long getEndpage()
	{
		return endpage;
	}
	public void setEndpage(long endpage)
	{
		this.endpage = endpage;
	}
}
