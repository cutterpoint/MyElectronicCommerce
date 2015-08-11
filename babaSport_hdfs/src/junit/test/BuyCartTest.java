/**
 * 功能：购物车的单元测试
 * 时间：2015年6月6日09:05:30
 * 文件：BuyCartTest.java
 * 作者：cutter_point
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
		Assert.assertTrue("对象不相等", buyItem1.equals(buyItem2));
		
	}

}
