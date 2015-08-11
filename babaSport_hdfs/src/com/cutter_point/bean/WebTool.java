/**
 * 功能：计算出页码的显示
 * 时间：2015年5月19日09:06:26
 * 文件：WebTool.java
 * 作者：cutter_point
 */
package com.cutter_point.bean;

public class WebTool
{
	/**
	 * 获取页码索引范围
	 * @param viewpagecount 显示的页码数
	 * @param currentpage	当前页码
	 * @param totalpage		总页码
	 * @return
	 */
	public static PageIndex getPageIndex(long viewpagecount, int currentpage, long totalpage)
	{
		long startpage = currentpage - (viewpagecount % 2 == 0 ? viewpagecount/2 - 1 : viewpagecount/2);	//页面显示的开始页面
		long endpage = currentpage + viewpagecount / 2;	//得到页面终止的页面
		
		if(startpage < 1)
		{
			startpage = 1;
			if(totalpage >= viewpagecount)	//如果总页数比显示的页面大的话
			{
				endpage = viewpagecount;	//那么最后一个页码就是显示的个数
			}
			else
			{
				endpage = totalpage;		//如果总页码比要求显示的页码还要少的话，那么就把总得页码当做该显示的最后的页码
			}
		}
		
		if(endpage > totalpage)	//如果页码的最后一位比总页数还大
		{
			endpage = totalpage;		//吧总页码数当做应该显示的最后一个页码
			if((endpage - viewpagecount) > 0)
			{
				//如果最后的页码比要求显示的页码大的话
				startpage = endpage - viewpagecount + 1;	//设定开始页码
			}
			else
			{
				//如果最后的页码比要求显示的总页码小的话
				startpage = 1;
			}
		}
		
		return new PageIndex(startpage, endpage);
	}
}
