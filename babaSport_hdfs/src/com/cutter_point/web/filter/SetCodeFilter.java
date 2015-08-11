/**
 * 功能：实现修改中英文乱码的问题
 * 文件：SetCodeFilter.java
 * 时间：2015年5月21日15:06:15
 * 作者：cutter_point
 */
package com.cutter_point.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class SetCodeFilter implements Filter
{
	
	private String encoding = null;		//这个就是我们要设置的编码格式
	@Override
	public void destroy()
	{
		this.encoding = null;		//还原为null
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain fc) throws IOException, ServletException
	{
		HttpServletRequest hsr = (HttpServletRequest) request;	//转化为需要的request
		hsr.setCharacterEncoding(this.encoding);
		fc.doFilter(request, response);		
	}
 
	@Override
	public void init(FilterConfig fc) throws ServletException
	{
		//这里我们初始化为web.xml的编码方式也就是utf-8
		this.encoding = fc.getInitParameter("encoding");
	}

}
