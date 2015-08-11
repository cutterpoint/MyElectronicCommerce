/**
 * 功能：这个是实现购物车的action控制器
 * 时间：2015年6月5日20:35:44
 * 文件：CartAction.java
 * 作者：cutter_point
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
		BuyCart buyCart = (BuyCart) request.getSession().getAttribute("buycart"); //取得购物车对象
		//然后给这个session存放到cookie中去
		HttpServletResponse response = ServletActionContext.getResponse();
		if(buyCart == null)
		{
			//如果打开了新的浏览器，我们要找回原来有的购物车,这个只获取不创建
			String sid = WebUtil.getCookieByName(request, "buyCartID"); //创建或者是取出一个cookie
			if(sid != null)
			{
				HttpSession session = SiteSession.getSession(sid);
				//取得以前的session，如果不存在
				if(session != null)
				{
					buyCart = (BuyCart) session.getAttribute("buycart");
					if(buyCart != null)		//如果取到了这个session
					{
						//不仅取到以前的session，我们还要把session放到当前的session里面去
						SiteSession.removeSession(sid); //去除以前的这个session同名的id引用
						request.getSession().setAttribute("buycart", buyCart);
						//给他创建一个cookie
						WebUtil.addCookie(response, "buyCartID", request.getSession().getId(), request.getSession().getMaxInactiveInterval());
					}
				}
			}
		}
		//如果当前session娶不到，以前的也娶不到,创建一个新的购物车
		if(buyCart == null)
		{
			buyCart = new BuyCart();//创建一个购物车
			request.getSession().setAttribute("buycart", buyCart);
			WebUtil.addCookie(response, "buyCartID", request.getSession().getId(), request.getSession().getMaxInactiveInterval());
		}
		
		//取得产品的id，不为空且大于0的话
		if(formbean.getProductid() != null && formbean.getProductid() > 0)
		{
			//根据id获取相应的产品
			ProductInfo product = productInfoService.find(ProductInfo.class, formbean.getProductid());
			//获取到产品了的话,然后就循环产品里面的样式，看看是不是我们要的产品id
			if(product != null)
			{
				ProductStyle currentStyle = null;
				for(ProductStyle style : product.getStyles())
				{
					if(style.getVisible() && style.getId().equals(formbean.getStyleid()))
					{
						//如果要添加的样式存在且在售
						currentStyle = style;
					}
				}
				//吧这个样式添加到这个产品中，获取到单个产品信息
				product.getStyles().clear();  //先清空。表示买的是一个产品
				if(currentStyle != null)
				{
					//吧这个样式添加进去
					product.addProductStyle(currentStyle);
				}
				//最后添加进入到购物车中
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
		//从页面获取表单值
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
