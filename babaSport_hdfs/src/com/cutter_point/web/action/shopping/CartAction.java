/**
 * ���ܣ������ʵ�ֹ��ﳵ��action������
 * ʱ�䣺2015��6��5��20:35:44
 * �ļ���CartAction.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.web.action.shopping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.bean.BuyCart;
import com.cutter_point.bean.BuyItem;
import com.cutter_point.bean.product.ProductInfo;
import com.cutter_point.bean.product.ProductStyle;
import com.cutter_point.service.product.ProductInfoService;
import com.cutter_point.utils.WebUtil;
import com.cutter_point.web.formbean.cart.CartForm;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class CartAction extends ActionSupport implements ServletRequestAware, ModelDriven<CartForm>
{
	private static final long serialVersionUID = 3759248656930302751L;
	
	@Resource
	private ProductInfoService productInfoService;
	private HttpServletRequest request;
	private CartForm formbean;

	@Override
	public String execute() throws Exception 
	{
		BuyCart buyCart = (BuyCart) request.getSession().getAttribute("buycart"); //ȡ�ù��ﳵ����
		//Ȼ������session��ŵ�cookie��ȥ
		HttpServletResponse response = ServletActionContext.getResponse();
		if(buyCart == null)
		{
			//��������µ������������Ҫ�һ�ԭ���еĹ��ﳵ,���ֻ��ȡ������
			String sid = WebUtil.getCookieByName(request, "buyCartID"); //����������ȡ��һ��cookie
			if(sid != null)
			{
				HttpSession session = SiteSession.getSession(sid);
				//ȡ����ǰ��session�����������
				if(session != null)
				{
					buyCart = (BuyCart) session.getAttribute("buycart");
					if(buyCart != null)		//���ȡ�������session
					{
						//����ȡ����ǰ��session�����ǻ�Ҫ��session�ŵ���ǰ��session����ȥ
						SiteSession.removeSession(sid); //ȥ����ǰ�����sessionͬ����id����
						request.getSession().setAttribute("buycart", buyCart);
						//��������һ��cookie
						WebUtil.addCookie(response, "buyCartID", request.getSession().getId(), request.getSession().getMaxInactiveInterval());
					}
				}
			}
		}
		//�����ǰsessionȢ��������ǰ��ҲȢ����,����һ���µĹ��ﳵ
		if(buyCart == null)
		{
			buyCart = new BuyCart();//����һ�����ﳵ
			request.getSession().setAttribute("buycart", buyCart);
			WebUtil.addCookie(response, "buyCartID", request.getSession().getId(), request.getSession().getMaxInactiveInterval());
		}
		
		//ȡ�ò�Ʒ��id����Ϊ���Ҵ���0�Ļ�
		if(formbean.getProductid() != null && formbean.getProductid() > 0)
		{
			//����id��ȡ��Ӧ�Ĳ�Ʒ
			ProductInfo product = productInfoService.find(ProductInfo.class, formbean.getProductid());
			//��ȡ����Ʒ�˵Ļ�,Ȼ���ѭ����Ʒ�������ʽ�������ǲ�������Ҫ�Ĳ�Ʒid
			if(product != null)
			{
				ProductStyle currentStyle = null;
				for(ProductStyle style : product.getStyles())
				{
					if(style.getVisible() && style.getId().equals(formbean.getStyleid()))
					{
						//���Ҫ��ӵ���ʽ����������
						currentStyle = style;
					}
				}
				//�������ʽ��ӵ������Ʒ�У���ȡ��������Ʒ��Ϣ
				product.getStyles().clear();  //����ա���ʾ�����һ����Ʒ
				if(currentStyle != null)
				{
					//�������ʽ��ӽ�ȥ
					product.addProductStyle(currentStyle);
				}
				//�����ӽ��뵽���ﳵ��
				buyCart.addItem(new BuyItem(product, 1));
			}
		}
		request.setAttribute("buyCart", buyCart);
		return "cart";
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) 
	{
		this.request = arg0;
	}

	@Override
	public CartForm getModel() 
	{
		//��ҳ���ȡ��ֵ
		if(formbean == null)
			formbean = new CartForm();
		
		return formbean;
	}

	public ProductInfoService getProductInfoService() {
		return productInfoService;
	}

	public void setProductInfoService(ProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}

	public CartForm getFormbean() {
		return formbean;
	}

	public void setFormbean(CartForm formbean) {
		this.formbean = formbean;
	}

}
