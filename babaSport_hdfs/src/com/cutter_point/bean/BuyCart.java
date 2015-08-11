/**
 * ���ܣ������һ�����ﳵʵ��
 * ʱ�䣺2015��6��6��08:48:09
 * �ļ���BuyCart.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.bean;

import java.util.ArrayList;
import java.util.List;

public class BuyCart 
{
	private List<BuyItem> items = new ArrayList<BuyItem>();
	
	/**
	 * ��ӹ�����
	 * @param item
	 */
	public void addItem(BuyItem item)
	{
		if(!items.contains(item)) //�Ƚ��Ƿ�����������������������equals��hashCode
		{
			items.add(item); //��������ڵĻ������һ��
		}
		else	//����Ѿ���������������ô�����һ����������
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
	 * ������й�����
	 */
	public void removeAll()
	{
		items.clear();
	}
	
	/**
	 * ɾ��������
	 * @param item
	 */
	public void removeBuyItem(BuyItem item)
	{
		if(items.contains(item))
			items.remove(item);
	}
	
	/**
	 * ���¹��ﳵ������Ʒ��Ĺ�������
	 * @param item
	 */
	public void updateAmount(BuyItem item)
	{
		for(BuyItem bi : items)
		{
			if(bi.equals(item))
			{
				//�޸�����
				bi.setAmount(item.getAmount());
				break;
			}
		}
	}
	
	/**
	 * �����޸ĸ��¹��ﳵ�Ĺ�������
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
					//�޸�����
					bi.setAmount(item.getAmount());
					break;
				}
			}
		}
	}
	
	/**
	 * ��ȡӦ���ܽ��
	 * @return
	 */
	public Float getTotalPrice()
	{
		float allprice = 0f;
		for(BuyItem item : items)
		{
			//��ȡ�ܽ��
			allprice += item.getProduct().getSellprice() * item.getAmount();
		}
		
		return allprice;
	}
	
	/**
	 * ��ȡ�г��ܼ�
	 * @return
	 */
	public Float getTotalMarketPrice()
	{
		float allprice = 0f;
		for(BuyItem item : items)
		{
			//��ȡ�ܽ��
			allprice += item.getProduct().getMarketprice() * item.getAmount();
		}
		
		return allprice;
	}
	
	/**
	 * �ܽ�ʡ���
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
