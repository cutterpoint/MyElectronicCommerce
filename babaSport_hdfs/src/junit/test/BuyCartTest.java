/**
 * ���ܣ����ﳵ�ĵ�Ԫ����
 * ʱ�䣺2015��6��6��09:05:30
 * �ļ���BuyCartTest.java
 * ���ߣ�cutter_point
 */
package junit.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.cutter_point.bean.BuyItem;
import com.cutter_point.bean.product.ProductInfo;
import com.cutter_point.bean.product.ProductStyle;

public class BuyCartTest {

	@Test
	public void test() 
	{
		List<BuyItem> items = new ArrayList<BuyItem>();
		
		ProductInfo product = new ProductInfo(1);
		product.addProductStyle(new ProductStyle(998));
		BuyItem buyItem1 = new BuyItem(product, 20);
		items.add(buyItem1);
		
		ProductInfo product1 = new ProductInfo(19);
		product1.addProductStyle(new ProductStyle(998));
		BuyItem buyItem2 = new BuyItem(product1, 20);
		
		System.out.println(items.contains(buyItem2));
		Assert.assertTrue("�������", buyItem1.equals(buyItem2));
		
	}

}
