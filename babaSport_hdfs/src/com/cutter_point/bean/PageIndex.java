/**
 * 功能：存放第一个页码和最后一个页码
 * 时间：2015年5月19日09:06:26
 * 文件：WebTool.java
 * 作者：cutter_point
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
