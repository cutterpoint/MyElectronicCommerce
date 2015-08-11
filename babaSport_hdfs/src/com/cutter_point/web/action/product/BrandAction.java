/**
 * 功能：这个是实现品牌类和web层的交互
 * 时间：2015年5月23日10:31:07
 * 文件：BrandAction.java
 * 作者：cutter_point
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
	private BrandService brandService;	//通过接口注入，aop默认的方式
	private int page;
	private String query;		//判断是从那个页面来的
	private String name;	//要查询的名字
	private HttpServletRequest request;
	
	
	@Override
	public String execute() throws Exception
	{
		PageView<Brand> pageview = new PageView<Brand>(12, this.getPage());
		int firstindex = (pageview.getCurrentpage() - 1) * pageview.getMaxresult();	//得到从哪个开始索引的值
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("code", "asc");
		StringBuilder hsql = new StringBuilder("o.visible = ?");
		List<Object> params = new ArrayList<Object>();	//这个用来存放需要的排序方式
		params.add(true);
		
		//判断来的页面是那个
		if("true".equals(this.getQuery()))
		{
			//是从查询页面来的需求
			//如果是查询的话，那么我们判定一下name是不是为空的
			if(this.getName() != null && !"".equals(this.getName().trim())) //后面那个trim是去掉空格的作用
			{
				//模糊查询一下
				hsql.append(" and o.name like ?");
				params.add("%" + this.getName() + "%");		//给问号赋值
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
