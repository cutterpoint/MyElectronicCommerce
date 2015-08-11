/**
 * ���ܣ�ʵ���޸���Ӣ�����������
 * �ļ���SetCodeFilter.java
 * ʱ�䣺2015��5��21��15:06:15
 * ���ߣ�cutter_point
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
	
	private String encoding = null;		//�����������Ҫ���õı����ʽ
	@Override
	public void destroy()
	{
		this.encoding = null;		//��ԭΪnull
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain fc) throws IOException, ServletException
	{
		HttpServletRequest hsr = (HttpServletRequest) request;	//ת��Ϊ��Ҫ��request
		hsr.setCharacterEncoding(this.encoding);
		fc.doFilter(request, response);		
	}
 
	@Override
	public void init(FilterConfig fc) throws ServletException
	{
		//�������ǳ�ʼ��Ϊweb.xml�ı��뷽ʽҲ����utf-8
		this.encoding = fc.getInitParameter("encoding");
	}

}
