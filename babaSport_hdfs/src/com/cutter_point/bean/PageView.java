/**
 * 功能：封装页面需要显示的信息
 * 时间：2015年5月19日10:59:35
 * 文件：PageView.java
 * 作者：cutter_point
 */
package com.cutter_point.bean;

import java.util.List;

public class PageView<T>
{
	private List<T> records;	//要显示的当前页记录数据
	private PageIndex pageindex;	//页码显示的范围
	private long totalpage = 1; 	//总页数，先默认值是1
	private int maxresult = 12;	//每页需要显示的个数，首先默认是12
	private int currentpage = 1;	//当前的页数
	private long totalrecords;		//显示的总的记录数
	
	private int pagecode = 10;	//每页默认显示的页码数量
	
	public PageView(int maxresult, int currentpage)	//构造函数
	{
		this.maxresult = maxresult;
		this.currentpage = currentpage;
	}
	
	public void setQueryResult(QueryResult<T> qr)
	{
		this.setTotalrecords(qr.getTotalrecord());	//得到全部的记录数
		this.setTotalpage(totalpage);
		this.setRecords(qr.getResultList()); 	//设置要显示的记录
	}
	
	public int getPagecode() {
		return pagecode;
	}

	public void setPagecode(int pagecode) {
		this.pagecode = pagecode;
	}

	public List<T> getRecords()
	{
		return records;
	}
	public void setRecords(List<T> records)
	{
		this.records = records;
	}
	public PageIndex getPageindex()
	{
		return pageindex;
	}
	public void setPageindex(PageIndex pageindex)
	{
		this.pageindex = pageindex;
	}
	public long getTotalpage()
	{
		return totalpage;
	}
	//总页数，先默认值是1
	public void setTotalpage(long totalpage)
	{
		this.totalpage = totalpage;
		this.pageindex = WebTool.getPageIndex(pagecode, currentpage, totalpage);
	}
	public int getMaxresult()
	{
		return maxresult;
	}
	public void setMaxresult(int maxresult)
	{
		this.maxresult = maxresult;
	}
	public int getCurrentpage()
	{
		return currentpage;
	}
	public void setCurrentpage(int currentpage)
	{
		this.currentpage = currentpage;
	}
	public long getTotalrecords()
	{
		return totalrecords;
	}
	public void setTotalrecords(long totalrecords)
	{
		this.totalrecords = totalrecords;	//得到数据库中的总记录数
		long pageuse1 = totalrecords % maxresult;
		long pageuse2 = totalrecords / maxresult;
		totalpage = pageuse1 == 0 ? pageuse2 : pageuse2 + 1;	//得到总页数
	}
}
