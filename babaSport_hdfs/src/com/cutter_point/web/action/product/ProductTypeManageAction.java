/**
 * 功能：这个是父类与之类的添加与修改
 * 时间：2015年5月20日15:40:17
 * 文件：ProductTypeManageAction.java
 * 作者：cutter_point
 */
package com.cutter_point.web.action.product;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cutter_point.bean.product.ProductType;
import com.cutter_point.service.product.ProductTypeService;
import com.cutter_point.utils.SiteUrl;
import com.opensymphony.xwork2.ActionSupport;

@Component
@Scope("prototype")
public class ProductTypeManageAction extends ActionSupport implements ServletRequestAware
{
	private static final long serialVersionUID = 4667795083950094509L;
	@Resource
	private ProductTypeService productTypeService;
	private Integer parentid;		//父类id
	private String name;
	private String note;
	private Integer typeid;	//当前类别的id号
	private HttpServletRequest request;

	/**
	 * 显示类别添加界面
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String addUI() throws Exception
	{
		return "add";
	}
	
	/**
	 * 类别添加操作
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception
	{
		//System.out.println(parentid + "      123132132131");
		ProductType type = new ProductType(this.getName(), this.getNote());
		if(this.getParentid() != null && this.getParentid() > 0)
			type.setParent(new ProductType(this.getParentid()));
		productTypeService.save(type);	//保存到数据库
		request.setAttribute("message", "添加类别成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.producttype.list"));
		return "message";
	}
	
	/**
	 * 显示类别修改界面
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String editUI() throws Exception
	{
		//取得相应的值传到修改的地方去
		ProductType pt = productTypeService.find(ProductType.class, this.getTypeid());	//得到对应的类别信息
		//吧要显示的信息放到界面上
		request.setAttribute("name", pt.getName());
		request.setAttribute("note", pt.getNote());		
		return "edit";
	}
	
	/**
	 * 类别修改操作
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception
	{
		ProductType pt = productTypeService.find(ProductType.class, this.getTypeid());	//得到对应的类别信息
		//我们修改他
		pt.setName(this.getName());
		pt.setNote(this.getNote());
		//修改之后用update方法跟正他
		productTypeService.update(pt);		
		request.setAttribute("message", "修改类别成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.producttype.list"));
		return "message";
	}
	
	/**
	 * 显示类别查询界面
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String queryUI() throws Exception
	{
		return "query";
	}
	
	public ProductTypeService getProductTypeService()
	{
		return productTypeService;
	}

	public void setProductTypeService(ProductTypeService productTypeService)
	{
		this.productTypeService = productTypeService;
	}

	public Integer getParentid()
	{
		return parentid;
	}

	public void setParentid(Integer parentid)
	{
		this.parentid = parentid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public Integer getTypeid()
	{
		return typeid;
	}

	public void setTypeid(Integer typeid)
	{
		this.typeid = typeid;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;
	}
}

