/**
 * ���ܣ����ǲ�Ʒ�ĵ�Ԫ����
 * �ļ���ProductInfoServiceTest.java
 * ʱ�䣺2015��5��27��15:42:04
 * ���ߣ�cutter_point
 */
package junit.test;

import java.util.List;

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

public class ProductInfoServiceTest
{
	//����spring�Ƿ��������
	private static ApplicationContext cxt;
	private static ProductInfoService pis;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		//��ȡ��Ӧ��bean
		try 
		{
			cxt = new ClassPathXmlApplicationContext("config/spring/beans.xml");
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
		ProductInfo pi = new ProductInfo();
		pi.setBaseprice(100f);
		pi.setBrand(new Brand("402881e44dadde2e014dadde30ea0002"));
		pi.setCode("UI007");
		pi.setDescription("����ͯ�����ۣ�66666666");
		pi.setMarketprice(600f);
		pi.setModel("K760E");
		pi.setName("����˹�걬һ��");
		pi.setSellprice(300f);
		pi.setSexrequest(Sex.NONE);
		pi.setType(new ProductType(9));
		pi.setWeight(50);
		pi.addProductStyle(new ProductStyle("��ɫ�ڿ��", "�ڿ��޵�.avi"));
		pis.save(pi);
	}
	
	@Test
	public void testGetTopSell()
	{
		List<ProductInfo> products = pis.getTopSell(5, 2);
		for(ProductInfo p : products)
		{
			System.out.println(p.getName());
		}
	}

}
