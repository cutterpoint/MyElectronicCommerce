/**
 * ���ܣ������ʵ�ֲ�Ʒ���web��Ľ���
 * ʱ�䣺2015��5��16��10:50:36
 * �ļ���ProductTypeAction.java
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
	private Integer parentid;		//����id
	private String name;
	private String query;		//�ж��Ǵ��Ǹ�ҳ������
	private HttpServletRequest request;
	
	@Override
	public String execute() throws Exception
	{
		PageView<ProductType> pageview = new PageView<ProductType>(12, this.getPage());
		int firstindex = (pageview.getCurrentpage() - 1) * pageview.getMaxresult();	//�õ����ĸ���ʼ������ֵ
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("typeid", "desc");
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
		else
		{
			//���ǴӲ�ѯ��������
			//�����ж�һ��SQL���дʲô
			if(this.getParentid() != null && this.getParentid() > 0)
			{
				hsql.append(" and o.parentid = ?");
				params.add(this.getParentid());		//����������趨?��ֵ��
			}
			else
			{
				//������಻���ڵĻ�
				hsql.append(" and o.parentid is null");
			}
		}
		
		QueryResult<ProductType> qr = productTypeService.getScrollData(ProductType.class, firstindex, pageview.getMaxresult(), hsql.toString(), 
				params.toArray(), orderby);
		pageview.setQueryResult(qr);
		request.setAttribute("pageView", pageview);
		
//		ProductType pt = productTypeService.find(ProductType.class, 3);
//		int maxresult = 12;	//ÿҳ��ʾ��������	
//		long pageuse1 = qr.getTotalrecord() % pageview.getMaxresult();
//		long pageuse2 = qr.getTotalrecord() / pageview.getMaxresult();
//		long totalpage = pageuse1 == 0 ? pageuse2 : pageuse2 + 1;
//		PageIndex pageindex = WebTool.getPageIndex(pageview.getMaxresult(), this.getPage(), totalpage);
//		request.put("productType", qr.getResultList());
//		request.put("totalpage", totalpage);
//		System.out.println("pageindex = " + pageindex.getStartpage() + " <=> " + pageindex.getEndpage());
//		System.out.println(pt);
		//System.out.println(qr.getResultList().get(0).getChildtypes().size()); �ӳټ��ز���
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
