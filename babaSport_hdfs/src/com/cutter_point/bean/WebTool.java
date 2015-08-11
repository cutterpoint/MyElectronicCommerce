/**
 * ���ܣ������ҳ�����ʾ
 * ʱ�䣺2015��5��19��09:06:26
 * �ļ���WebTool.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.bean;

public class WebTool
{
	/**
	 * ��ȡҳ��������Χ
	 * @param viewpagecount ��ʾ��ҳ����
	 * @param currentpage	��ǰҳ��
	 * @param totalpage		��ҳ��
	 * @return
	 */
	public static PageIndex getPageIndex(long viewpagecount, int currentpage, long totalpage)
	{
		long startpage = currentpage - (viewpagecount % 2 == 0 ? viewpagecount/2 - 1 : viewpagecount/2);	//ҳ����ʾ�Ŀ�ʼҳ��
		long endpage = currentpage + viewpagecount / 2;	//�õ�ҳ����ֹ��ҳ��
		
		if(startpage < 1)
		{
			startpage = 1;
			if(totalpage >= viewpagecount)	//�����ҳ������ʾ��ҳ���Ļ�
			{
				endpage = viewpagecount;	//��ô���һ��ҳ�������ʾ�ĸ���
			}
			else
			{
				endpage = totalpage;		//�����ҳ���Ҫ����ʾ��ҳ�뻹Ҫ�ٵĻ�����ô�Ͱ��ܵ�ҳ�뵱������ʾ������ҳ��
			}
		}
		
		if(endpage > totalpage)	//���ҳ������һλ����ҳ������
		{
			endpage = totalpage;		//����ҳ��������Ӧ����ʾ�����һ��ҳ��
			if((endpage - viewpagecount) > 0)
			{
				//�������ҳ���Ҫ����ʾ��ҳ���Ļ�
				startpage = endpage - viewpagecount + 1;	//�趨��ʼҳ��
			}
			else
			{
				//�������ҳ���Ҫ����ʾ����ҳ��С�Ļ�
				startpage = 1;
			}
		}
		
		return new PageIndex(startpage, endpage);
	}
}
