/**
 * 功能：这儿类是为了存放分页的时候从数据库里面查找出来的数据和记录数
 * 文件：QueryResult.java
 * 时间：2015年5月14日21:45:42
 * 作者：cutter_point
 */
package com.cutter_point.bean;

import java.util.List;

public class QueryResult<T>
{
	private List<T> resultList;
	private long totalrecord;	//总记录数
	
	public List<T> getResultList()
	{
		return resultList;
	}
	public void setResultList(List<T> resultList)
	{
		this.resultList = resultList;
	}
	public long getTotalrecord()
	{
		return totalrecord;
	}
	public void setTotalrecord(long totalrecord)
	{
		this.totalrecord = totalrecord;
	}

}
