/**
 * 功能：这个是一个购物车的清单
 * 时间：2015年6月6日08:50:06
 * 文件：BuyItem.java
 * 作者：cutter_point
 */
package com.cutter_point.bean;

import com.cutter_point.bean.product.ProductInfo;
import com.cutter_point.bean.product.ProductStyle;

public class BuyItem 
{
	/** 购买的商品 **/
	private ProductInfo product;
	/** 购买的数量 **/
	private int amount;
	
	public BuyItem(ProductInfo product) {
		this.product = product;
	}
	public BuyItem(ProductInfo product, int amount) {
		this.product = product;
		this.amount = amount;
	}
	public ProductInfo getProduct() {
		return product;
	}
	public void setProduct(ProductInfo product) {
		this.product = product;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public int hashCode() {
		String buyitem = product.hashCode() + "-";
		if(product.getStyles().size() > 0)
		{
			//取得第一个样式的id
			buyitem += product.getStyles().iterator().next().getId();
		}
		
		return buyitem.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuyItem other = (BuyItem) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		
		if(product.getStyles().size() != other.getProduct().getStyles().size())
		{
			return false;
		}
		
		if(product.getStyles().size() > 0)
		{
			ProductStyle style = product.getStyles().iterator().next();
			ProductStyle otherstyle = other.product.getStyles().iterator().next();
			if(!style.equals(otherstyle))
				return false;
		}
		
		return true;
	}
	
}
