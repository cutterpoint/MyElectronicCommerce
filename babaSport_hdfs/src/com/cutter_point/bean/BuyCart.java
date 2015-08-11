/**
 * 功能：这个是一个购物车实体
 * 时间：2015年6月6日08:48:09
 * 文件：BuyCart.java
 * 作者：cutter_point
 */
package com.cutter_point.bean;

import java.util.ArrayList;
import java.util.List;

public class BuyCart 
{
	private List<BuyItem> items = new ArrayList<BuyItem>();
	
	/**
	 * 添加购物项
	 * @param item
	 */
	public void addItem(BuyItem item)
	{
		if(!items.contains(item)) //比较是否存在里面必须重载两个方法equals和hashCode
		{
			items.add(item); //如果不存在的话就添加一个
		}
		else	//如果已经存在这个购物项，那么就添加一个购买数量
		{
			for(BuyItem bi : items)
			{
				if(bi.equals(item))
				{
					bi.setAmount(bi.getAmount() + 1);
					break;
				}
			}
		}
	}
	
	/**
	 * 清除所有购物项
	 */
	public void removeAll()
	{
		items.clear();
	}
	
	/**
	 * 删除购物项
	 * @param item
	 */
	public void removeBuyItem(BuyItem item)
	{
		if(items.contains(item))
			items.remove(item);
	}
	
	/**
	 * 更新购物车里面商品项的购买数量
	 * @param item
	 */
	public void updateAmount(BuyItem item)
	{
		for(BuyItem bi : items)
		{
			if(bi.equals(item))
			{
				//修改数量
				bi.setAmount(item.getAmount());
				break;
			}
		}
	}
	
	/**
	 * 批量修改更新购物车的购买数量
	 * @param items
	 */
	public void updateAmount(BuyItem[] items)
	{
		for(BuyItem bi : this.items)
		{
			for(BuyItem item : items)
			{
				if(item.equals(item))
				{
					//修改数量
					bi.setAmount(item.getAmount());
					break;
				}
			}
		}
	}
	
	/**
	 * 获取应付总金额
	 * @return
	 */
	public Float getTotalPrice()
	{
		float allprice = 0f;
		for(BuyItem item : items)
		{
			//获取总金额
			allprice += item.getProduct().getSellprice() * item.getAmount();
		}
		
		return allprice;
	}
	
	/**
	 * 获取市场总价
	 * @return
	 */
	public Float getTotalMarketPrice()
	{
		float allprice = 0f;
		for(BuyItem item : items)
		{
			//获取总金额
			allprice += item.getProduct().getMarketprice() * item.getAmount();
		}
		
		return allprice;
	}
	
	/**
	 * 总节省金额
	 * @return
	 */
	public float getTotalSavePrice()
	{
		return this.getTotalMarketPrice() - this.getTotalPrice();
	}

	public List<BuyItem> getItems() {
		return items;
	}

	public void setItems(List<BuyItem> items) {
		this.items = items;
	}
	
}
