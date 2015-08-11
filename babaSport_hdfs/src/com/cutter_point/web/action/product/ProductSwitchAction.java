/**
 * ���ܣ������ǰ̨��ʾ�Ƽ������==����ajax����ʾ
 * ʱ�䣺2015��6��4��15:25:50
 * �ļ���ProductSwitchAction.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.web.action.product;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.service.product.ProductInfoService;
import com.cutter_point.utils.WebUtil;
import com.cutter_point.web.formbean.product.FrontProductForm;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")		//�����ʾÿ�η��ʵ�ʱ�򶼻���һ���µĶ��󴴽�
public class ProductSwitchAction extends ActionSupport implements ServletRequestAware, ModelDriven<FrontProductForm>
{

	private static final long serialVersionUID = 4327341396079235931L;
	@Resource
	private ProductInfoService productInfoService;	//ҵ��ע��
	private HttpServletRequest request;
	private FrontProductForm formbean;
	
	/**
	 * ��ʾ��Ʒ��ͼ
	 * @return
	 * @throws Exception
	 */
	public String showimage() throws Exception
	{
		return "showimage";
	}
	
	/**
	 * ��ȡ10������Ĳ�Ʒ
	 * @return
	 * @throws Exception
	 */
	public String topsell() throws Exception
	{
		request.setAttribute("topsellproducts", productInfoService.getTopSell(formbean.getTypeid(), 10));
		return "topsell";
	}
	
	/**
	 * ��ȡ10���û����������Ʒ
	 * @return
	 * @throws Exception
	 */
	public String viewHistory() throws Exception
	{
		//������ôҪ��ȡcookie��ֵ
		String cookieValue = WebUtil.getCookieByName(request, "productViewHistory");
		//cookie�Ĵ�Ÿ�ʽ12-45-67-89
		if(cookieValue != null && !"".equals(cookieValue))
		{
			String[] ids = cookieValue.split("-"); //���ַ���-�ָ
			Integer[] productids = new Integer[ids.length]; //��һ���ȳ���������
			StringBuilder sql = new StringBuilder();	//������װ��Ӧ��sql���
			for(int i = 0; i < ids.length; ++i)
			{
				//ѭ������Ʒ��id��Ž�ȥ
				productids[i] = new Integer(ids[i].trim()); //ȥ����Ӧ�Ŀո񣬵õ���Ӧֵ
				sql.append("?,");
			}
			//ȥ�����һ������
			sql.deleteCharAt(sql.length() - 1);
			//��ȡ��Ӧ�Ĳ�Ʒ��Ϣ����0��ʼ��ȡ10��
			request.setAttribute("viewHistory", productInfoService.getViewHistory(productids, 10));
		}
		return "viewHistory";
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
		if(formbean == null)
			formbean = new FrontProductForm();
		
		return formbean;
	}

}
