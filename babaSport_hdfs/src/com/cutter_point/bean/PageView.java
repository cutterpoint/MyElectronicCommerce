/**
 * ���ܣ���װҳ����Ҫ��ʾ����Ϣ
 * ʱ�䣺2015��5��19��10:59:35
 * �ļ���PageView.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.bean;

import java.util.List;

public class PageView<T>
{
	private List<T> records;	//Ҫ��ʾ�ĵ�ǰҳ��¼����
	private PageIndex pageindex;	//ҳ����ʾ�ķ�Χ
	private long totalpage = 1; 	//��ҳ������Ĭ��ֵ��1
	private int maxresult = 12;	//ÿҳ��Ҫ��ʾ�ĸ���������Ĭ����12
	private int currentpage = 1;	//��ǰ��ҳ��
	private long totalrecords;		//��ʾ���ܵļ�¼��
	
	private int pagecode = 10;	//ÿҳĬ����ʾ��ҳ������
	
	public PageView(int maxresult, int currentpage)	//���캯��
	{
		this.maxresult = maxresult;
		this.currentpage = currentpage;
	}
	
	public void setQueryResult(QueryResult<T> qr)
	{
		this.setTotalrecords(qr.getTotalrecord());	//�õ�ȫ���ļ�¼��
		this.setTotalpage(totalpage);
		this.setRecords(qr.getResultList()); 	//����Ҫ��ʾ�ļ�¼
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
	//��ҳ������Ĭ��ֵ��1
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
		this.totalrecords = totalrecords;	//�õ����ݿ��е��ܼ�¼��
		long pageuse1 = totalrecords % maxresult;
		long pageuse2 = totalrecords / maxresult;
		totalpage = pageuse1 == 0 ? pageuse2 : pageuse2 + 1;	//�õ���ҳ��
	}
}
