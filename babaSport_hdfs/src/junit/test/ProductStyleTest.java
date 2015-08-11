/**
 * 功能：这是产品类别的单元测试
 * 文件：ProductTest.java
 * 时间：2015年5月12日10:27:24
 * 作者：cutter_point
 */
package junit.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cutter_point.bean.product.Brand;
import com.cutter_point.bean.product.ProductInfo;
import com.cutter_point.bean.product.ProductStyle;
import com.cutter_point.bean.product.ProductType;
import com.cutter_point.bean.product.Sex;
import com.cutter_point.service.product.ProductInfoService;
import com.cutter_point.service.product.ProductStyleService;

public class ProductStyleTest 
{
	//测试spring是否可以运作
	private static ApplicationContext cxt;
	private static ProductStyleService pss;
	private static ProductInfoService pis;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		try
		{
			cxt = new ClassPathXmlApplicationContext("config/spring/beans.xml");
			pss = (ProductStyleService) cxt.getBean("productStyleServiceBean");
			pis = (ProductInfoService) cxt.getBean("productInfoServiceBean");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void test() 
	{
		ProductStyle ps = new ProductStyle();
		ProductInfo pi;
		pi = pis.getScrollData(ProductInfo.class).getResultList().get(0);
/*		pi.setBaseprice(100f);
		pi.setBrand(new Brand("402881e44d85e594014d85e7fa270001"));
		pi.setCode("UI007");
		pi.setDescription("本店童叟无欺，66666666");
		pi.setMarketprice(600f);
		pi.setModel("K760E");
		pi.setName("杜蕾斯完爆一切");
		pi.setSellprice(300f);
		pi.setSexrequest(Sex.NONE);
		pi.setType(new ProductType(9));
		pi.setWeight(50);
		pi.addProductStyle(new ProductStyle("红色内裤号", "内裤无敌.avi"));*/
		ps.setName("不能没有名字");
		ps.setImagename("haha");
		ps.setVisible(true);
		ps.setProduct(pi);
		pss.save(ps);
	}

}
