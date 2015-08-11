/**
 * ���ܣ������ר��Ϊ����ʾ��Ʒ�Ŀ�����
 * ʱ�䣺2015��6��4��19:20:20
 * �ļ���ViewProductAction.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.web.action.product;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.bean.product.ProductInfo;
import com.cutter_point.bean.product.ProductType;
import com.cutter_point.service.product.ProductInfoService;
import com.cutter_point.utils.WebUtil;
import com.cutter_point.web.formbean.product.FrontProductForm;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class ViewProductAction extends ActionSupport implements	ServletRequestAware, ModelDriven<FrontProductForm>
{
	private static final long serialVersionUID = -886191288027224488L;
	@Resource
	private ProductInfoService productInfoService;	//ҵ��ע��
	private HttpServletRequest request;
	private FrontProductForm pf;	//һ����bean

	
	@Override
	public String execute() throws Exception 
	{
		//ͨ��ҳ��Ĵ�ֵ�õ���Ʒ��id
		ProductInfo product = productInfoService.find(ProductInfo.class, pf.getProductid());
		HttpServletResponse response = ServletActionContext.getResponse();
		if(product == null)
		{
			request.setAttribute("message", "��ȡ������Ʒ");
			request.setAttribute("urladdress", "/");
			return "message";
		}
		//��ȡ��cookie,���30�죬����һ��cookie
		WebUtil.addCookie(response, "productViewHistory", this.buildViewHistory(pf.getProductid()), 30*24*60*60);
		List<ProductType> types = new ArrayList<ProductType>();
		ProductType parent = product.getType();	//��ȡ�����Ʒ���
		while(parent != null)
		{
			//�����
			types.add(parent);	//�������ӵ�һ��
			parent = parent.getParent();	//�õ�����,Ȼ���ڰɸ���ŵ���������ȥ
		}
		request.setAttribute("types", types);
		request.setAttribute("product", product);
		return "product";
	}
	
	/**
	 * ���������Ϊ�˻�ȡ����Ӧcookie��ֵ
	 * @param currentProductId
	 * @return
	 */
	public String buildViewHistory(Integer currentProductId)
	{
		//��ȡ����Ӧ��cookie��ֵ
		//1.�����ǰ�����id����������ʷ�����ˣ����ǰ���Ҫ�ƶ�����ǰ��
		//2.��������ʷ�ﵽ��10����Ʒ��������Ҫ�����ȱ�����Ĳ�Ʒid��ɾ����
		String cookieValue = WebUtil.getCookieByName(request, "productViewHistory");
		LinkedList<Integer> productids = new LinkedList<Integer>();
		//���������Ϊ�˴�����ǰ������Ķ�����Ԫ������
		if(cookieValue != null && !"".equals(cookieValue.trim()))
		{
			String[] ids = cookieValue.split("-");	//�ָ�cookie��ֵ
			//�ɷָ�֮������ݴ�ŵ�һ��list����
			//������һ��һ���ؼӽ�ȥ
			for(String id : ids)
			{
				//��������ӵ�list���棬������������е������ʷ��¼
				productids.offer(new Integer(id.trim()));
			}
			if(productids.contains(currentProductId)) //���list������ڵ�ǰ�Ĳ�Ʒid�ŵĻ�
			{
				//�����Ȱ���ɾ����
				productids.remove(currentProductId);
			}
			//�����ʷ��¼�Լ�������10������ô�Ͱ���ǰ���Ǹ�ȥ��
			if(productids.size() >= 10)
			{
				productids.poll();
			}
		}
		//Ȼ���ټӽ���
		productids.add(currentProductId);
		//����������Ķ�����ת��Ϊcookie�������������
		StringBuilder outcookie = new StringBuilder();
		for(Integer id : productids)
		{
			outcookie.append(id).append("-");
		}
		outcookie.deleteCharAt(outcookie.length() - 1);
		return outcookie.toString();
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) 
	{
		this.request = arg0;
	}

	@Override
	public FrontProductForm getModel() 
	{
		//��ҳ���ȡ��ֵ
		if(pf == null)
			pf = new FrontProductForm();
		
		return pf;
	}

	public FrontProductForm getPf() {
		return pf;
	}

	public void setPf(FrontProductForm pf) {
		this.pf = pf;
	}
}
