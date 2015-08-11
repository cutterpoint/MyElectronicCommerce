/**
 * 功能：这个是实现购物车的管理action控制器
 * 时间：2015年6月6日12:24:40
 * 文件：CartAction.java
 * 作者：cutter_point
 */
package com.cutter_point.web.action.shopping;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.bean.BuyCart;
import com.cutter_point.bean.BuyItem;
import com.cutter_point.bean.product.ProductInfo;
import com.cutter_point.bean.product.ProductStyle;
import com.cutter_point.web.formbean.cart.CartForm;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class CartManageAction extends ActionSupport implements	ServletRequestAware, ModelDriven<CartForm>
{
	private static final long serialVersionUID = 3076346960806313949L;
	private HttpServletRequest request;
	private CartForm formbean;
	
	private BuyCart getBuyCart(HttpServletRequest req)
	{
		return (BuyCart) req.getSession().getAttribute("buycart");
	}
	
	/**
	 * 删除指定的购买项目
	 * @return
	 */
	public String delete()
	{
		//首先判断取得的购物车不是空的
		BuyCart buyCart = this.getBuyCart(request);
		if(buyCart != null)
		{
			//删除对应的产品
			ProductInfo product = new ProductInfo(formbean.getProductid());
			product.addProductStyle(new ProductStyle(formbean.getStyleid()));
			buyCart.removeBuyItem(new BuyItem(product)); //移除对应的这个购物项
		}
		return "cart";
	}
	
	/**
	 * 删除所有的购物项
	 * @return
	 */
	public String deleteAll()
	{
		//首先判断取得的购物车不是空的
		BuyCart buyCart = this.getBuyCart(request);
		if(buyCart != null)
		{
			//删除对应的产品
			buyCart.removeAll();; //移除对应的这个购物项
		}
		return "cart";
	}
	
	/**
	 * 更新所有的购买数量
	 * @return
	 */
	public String updateAmount()
	{
		//首先取得当前回话的相应的购物车
		BuyCart buyCart = this.getBuyCart(request);
		//如果购物车不为空
		if(buyCart != null)
		{
			//循环，取得所有的购物项
			for(BuyItem item : buyCart.getItems())
			{
				StringBuilder key = new StringBuilder("amount_");
				//根据对应的字符串，取得对应的修改数量
				key.append(item.getProduct().getId()).append("-");
				//然后在加上相应的类型id
				if(item.getProduct().getStyles().size() > 0)
				{
					//如果这个商品里面有相应的类型号的话
					key.append(item.getProduct().getStyles().iterator().next().getId());
				}
				//根据这个key在页面取得相应的数量
				String amountStr = request.getParameter(key.toString());
				if(amountStr != null && !"".equals(amountStr))
				{
					try 
					{
						//如果不为空，且不是空字符传,转换为整形
						int amount = Integer.parseInt(amountStr);
						if(amount > 0)
						{
							//小于0不做处理
							item.setAmount(amount); //设置这个数量为新的数量
						}
					} 
					catch (NumberFormatException e) 
					{
						e.printStackTrace();
					}
				}
			}
		}
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

}
