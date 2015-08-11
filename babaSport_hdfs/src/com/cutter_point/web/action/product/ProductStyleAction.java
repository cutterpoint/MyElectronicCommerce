/**
 * ���ܣ������ʵ�ֲ�Ʒ��ʽ��web������
 * ʱ�䣺2015��5��31��19:28:31
 * �ļ���ProductStyleAction.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.web.action.product;

import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.bean.QueryResult;
import com.cutter_point.bean.product.ProductStyle;
import com.cutter_point.service.product.ProductStyleService;
import com.cutter_point.web.formbean.product.ProductForm;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class ProductStyleAction extends ActionSupport implements ServletRequestAware, ModelDriven<ProductForm>
{
	private static final long serialVersionUID = 1880713443264679601L;
	//��IOCע��service��
	@Resource
	private ProductStyleService productStyleService;
	private HttpServletRequest request;
	private ProductForm pf;
	
	public String execute() throws Exception
	{
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("visible", "desc");
		orderby.put("id", "asc");
		//���ݲ�Ʒid�ţ���ȡ��Ӧ������
		QueryResult<ProductStyle> qr = productStyleService.getScrollData(ProductStyle.class, -1, -1, 
								" o.productid = ?", new Object[]{pf.getProductid()}, orderby);
		//�ɲ�ѯ�����Ķ����ŵ�ҳ����
		request.setAttribute("styles", qr.getResultList());
		
		return "list";
	}
	
	public void setServletRequest(HttpServletRequest arg0) 
	{
		this.request = arg0;
	}

	@Override
	public ProductForm getModel() 
	{
		if(pf == null)
			pf = new ProductForm();
		return this.pf;		//�������ǵı���
	}

	public ProductStyleService getProductStyleService() {
		return productStyleService;
	}

	public void setProductStyleService(ProductStyleService productStyleService) {
		this.productStyleService = productStyleService;
	}

	public ProductForm getPf() {
		return pf;
	}

	public void setPf(ProductForm pf) {
		this.pf = pf;
	}
}
