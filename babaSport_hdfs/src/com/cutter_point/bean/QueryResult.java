/**
 * ���ܣ��������Ϊ�˴�ŷ�ҳ��ʱ������ݿ�������ҳ��������ݺͼ�¼��
 * �ļ���QueryResult.java
 * ʱ�䣺2015��5��14��21:45:42
 * ���ߣ�cutter_point
 */
package com.cutter_point.bean;

import java.util.List;

public class QueryResult<T>
{
	private List<T> resultList;
	private long totalrecord;	//�ܼ�¼��
	
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
