/**
 * ���ܣ������ʵ�ֹ��ﳵ�Ĺ���action������
 * ʱ�䣺2015��6��6��12:24:40
 * �ļ���CartAction.java
 * ���ߣ�cutter_point
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
	 * ɾ��ָ���Ĺ�����Ŀ
	 * @return
	 */
	public String delete()
	{
		//�����ж�ȡ�õĹ��ﳵ���ǿյ�
		BuyCart buyCart = this.getBuyCart(request);
		if(buyCart != null)
		{
			//ɾ����Ӧ�Ĳ�Ʒ
			ProductInfo product = new ProductInfo(formbean.getProductid());
			product.addProductStyle(new ProductStyle(formbean.getStyleid()));
			buyCart.removeBuyItem(new BuyItem(product)); //�Ƴ���Ӧ�����������
		}
		return "cart";
	}
	
	/**
	 * ɾ�����еĹ�����
	 * @return
	 */
	public String deleteAll()
	{
		//�����ж�ȡ�õĹ��ﳵ���ǿյ�
		BuyCart buyCart = this.getBuyCart(request);
		if(buyCart != null)
		{
			//ɾ����Ӧ�Ĳ�Ʒ
			buyCart.removeAll();; //�Ƴ���Ӧ�����������
		}
		return "cart";
	}
	
	/**
	 * �������еĹ�������
	 * @return
	 */
	public String updateAmount()
	{
		//����ȡ�õ�ǰ�ػ�����Ӧ�Ĺ��ﳵ
		BuyCart buyCart = this.getBuyCart(request);
		//������ﳵ��Ϊ��
		if(buyCart != null)
		{
			//ѭ����ȡ�����еĹ�����
			for(BuyItem item : buyCart.getItems())
			{
				StringBuilder key = new StringBuilder("amount_");
				//���ݶ�Ӧ���ַ�����ȡ�ö�Ӧ���޸�����
				key.append(item.getProduct().getId()).append("-");
				//Ȼ���ڼ�����Ӧ������id
				if(item.getProduct().getStyles().size() > 0)
				{
					//��������Ʒ��������Ӧ�����ͺŵĻ�
					key.append(item.getProduct().getStyles().iterator().next().getId());
				}
				//�������key��ҳ��ȡ����Ӧ������
				String amountStr = request.getParameter(key.toString());
				if(amountStr != null && !"".equals(amountStr))
				{
					try 
					{
						//�����Ϊ�գ��Ҳ��ǿ��ַ���,ת��Ϊ����
						int amount = Integer.parseInt(amountStr);
						if(amount > 0)
						{
							//С��0��������
							item.setAmount(amount); //�����������Ϊ�µ�����
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
		//��ҳ���ȡ��ֵ
		if(formbean == null)
			formbean = new CartForm();
		
		return formbean;
	}

}
