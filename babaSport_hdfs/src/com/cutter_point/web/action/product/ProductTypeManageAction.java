/**
 * ���ܣ�����Ǹ�����֮���������޸�
 * ʱ�䣺2015��5��20��15:40:17
 * �ļ���ProductTypeManageAction.java
 * ���ߣ�cutter_point
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
	private Integer parentid;		//����id
	private String name;
	private String note;
	private Integer typeid;	//��ǰ����id��
	private HttpServletRequest request;

	/**
	 * ��ʾ�����ӽ���
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String addUI() throws Exception
	{
		return "add";
	}
	
	/**
	 * �����Ӳ���
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception
	{
		//System.out.println(parentid + "      123132132131");
		ProductType type = new ProductType(this.getName(), this.getNote());
		if(this.getParentid() != null && this.getParentid() > 0)
			type.setParent(new ProductType(this.getParentid()));
		productTypeService.save(type);	//���浽���ݿ�
		request.setAttribute("message", "������ɹ�");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.producttype.list"));
		return "message";
	}
	
	/**
	 * ��ʾ����޸Ľ���
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String editUI() throws Exception
	{
		//ȡ����Ӧ��ֵ�����޸ĵĵط�ȥ
		ProductType pt = productTypeService.find(ProductType.class, this.getTypeid());	//�õ���Ӧ�������Ϣ
		//��Ҫ��ʾ����Ϣ�ŵ�������
		request.setAttribute("name", pt.getName());
		request.setAttribute("note", pt.getNote());		
		return "edit";
	}
	
	/**
	 * ����޸Ĳ���
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception
	{
		ProductType pt = productTypeService.find(ProductType.class, this.getTypeid());	//�õ���Ӧ�������Ϣ
		//�����޸���
		pt.setName(this.getName());
		pt.setNote(this.getNote());
		//�޸�֮����update����������
		productTypeService.update(pt);		
		request.setAttribute("message", "�޸����ɹ�");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.producttype.list"));
		return "message";
	}
	
	/**
	 * ��ʾ����ѯ����
	 * @return String struts2�ķ�����תresult
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

