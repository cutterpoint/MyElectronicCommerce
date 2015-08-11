/**
 * ���ܣ������ʵ��Ʒ�����web��Ľ���
 * ʱ�䣺2015��5��23��10:31:07
 * �ļ���BrandAction.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.web.action.product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.bean.PageView;
import com.cutter_point.bean.QueryResult;
import com.cutter_point.bean.product.Brand;
import com.cutter_point.service.product.BrandService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class BrandAction extends ActionSupport implements ServletRequestAware
{
	private static final long serialVersionUID = 3382755123319100431L;
	@Resource
	private BrandService brandService;	//ͨ���ӿ�ע�룬aopĬ�ϵķ�ʽ
	private int page;
	private String query;		//�ж��Ǵ��Ǹ�ҳ������
	private String name;	//Ҫ��ѯ������
	private HttpServletRequest request;
	
	
	@Override
	public String execute() throws Exception
	{
		PageView<Brand> pageview = new PageView<Brand>(12, this.getPage());
		int firstindex = (pageview.getCurrentpage() - 1) * pageview.getMaxresult();	//�õ����ĸ���ʼ������ֵ
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("code", "asc");
		StringBuilder hsql = new StringBuilder("o.visible = ?");
		List<Object> params = new ArrayList<Object>();	//������������Ҫ������ʽ
		params.add(true);
		
		//�ж�����ҳ�����Ǹ�
		if("true".equals(this.getQuery()))
		{
			//�ǴӲ�ѯҳ����������
			//����ǲ�ѯ�Ļ�����ô�����ж�һ��name�ǲ���Ϊ�յ�
			if(this.getName() != null && !"".equals(this.getName().trim())) //�����Ǹ�trim��ȥ���ո������
			{
				//ģ����ѯһ��
				hsql.append(" and o.name like ?");
				params.add("%" + this.getName() + "%");		//���ʺŸ�ֵ
			}
		}
		
		QueryResult<Brand> qr = brandService.getScrollData(Brand.class, firstindex, pageview.getMaxresult(), hsql.toString(), 
				params.toArray(), orderby);
		pageview.setQueryResult(qr);
		request.setAttribute("pageView", pageview);
		
		return "list";
	}
	
	public void setPage(int page)
	{
		this.page = page;
	}
	
	public int getPage()
	{
		return page < 1 ? 1 : page;
	}

	public BrandService getBrandService()
	{
		return brandService;
	}

	public void setBrandService(BrandService brandService)
	{
		this.brandService = brandService;
	}

	public String getQuery()
	{
		return query;
	}

	public void setQuery(String query)
	{
		this.query = query;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;
	}
}
