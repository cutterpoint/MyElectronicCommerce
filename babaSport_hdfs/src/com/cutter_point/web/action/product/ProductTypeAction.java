/**
 * 功能：这个是实现产品类和web层的交互
 * 时间：2015年5月16日10:50:36
 * 文件：ProductTypeAction.java
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
import org.springframework.stereotype.Component;

import com.cutter_point.bean.PageView;
import com.cutter_point.bean.QueryResult;
import com.cutter_point.bean.product.ProductType;
import com.cutter_point.service.product.ProductTypeService;
import com.opensymphony.xwork2.ActionSupport;

//@Namespace("/product")
@Component
@Scope("prototype")
public class ProductTypeAction extends ActionSupport implements ServletRequestAware
{
	private static final long serialVersionUID = -5425836309572789406L;
	@Resource
	private ProductTypeService productTypeService;
	private int page;
	private Integer parentid;		//父类id
	private String name;
	private String query;		//判断是从那个页面来的
	private HttpServletRequest request;
	
	@Override
	public String execute() throws Exception
	{
		PageView<ProductType> pageview = new PageView<ProductType>(12, this.getPage());
		int firstindex = (pageview.getCurrentpage() - 1) * pageview.getMaxresult();	//得到从哪个开始索引的值
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("typeid", "desc");
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
		else
		{
			//不是从查询来的需求
			//这里判定一下SQL语句写什么
			if(this.getParentid() != null && this.getParentid() > 0)
			{
				hsql.append(" and o.parentid = ?");
				params.add(this.getParentid());		//这个是用来设定?的值的
			}
			else
			{
				//如果父类不存在的话
				hsql.append(" and o.parentid is null");
			}
		}
		
		QueryResult<ProductType> qr = productTypeService.getScrollData(ProductType.class, firstindex, pageview.getMaxresult(), hsql.toString(), 
				params.toArray(), orderby);
		pageview.setQueryResult(qr);
		request.setAttribute("pageView", pageview);
		
//		ProductType pt = productTypeService.find(ProductType.class, 3);
//		int maxresult = 12;	//每页显示最多的条数	
//		long pageuse1 = qr.getTotalrecord() % pageview.getMaxresult();
//		long pageuse2 = qr.getTotalrecord() / pageview.getMaxresult();
//		long totalpage = pageuse1 == 0 ? pageuse2 : pageuse2 + 1;
//		PageIndex pageindex = WebTool.getPageIndex(pageview.getMaxresult(), this.getPage(), totalpage);
//		request.put("productType", qr.getResultList());
//		request.put("totalpage", totalpage);
//		System.out.println("pageindex = " + pageindex.getStartpage() + " <=> " + pageindex.getEndpage());
//		System.out.println(pt);
		//System.out.println(qr.getResultList().get(0).getChildtypes().size()); 延迟加载测试
		return "list";
	}
	
	public int getPage()
	{
		return page < 1 ? 1 : page;
	}

	public Integer getParentid()
	{
		return parentid;
	}

	public void setParentid(Integer parentid)
	{
		this.parentid = parentid;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public ProductTypeService getProductTypeService()
	{
		return productTypeService;
	}

	public void setProductTypeService(ProductTypeService productTypeService)
	{
		this.productTypeService = productTypeService;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getQuery()
	{
		return query;
	}

	public void setQuery(String query)
	{
		this.query = query;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;
		
	}
}
