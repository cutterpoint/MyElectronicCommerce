/**
 * 功能：这是品牌类别的单元测试
 * 文件：BrandTest.java
 * 时间：2015年5月22日17:20:28
 * 作者：cutter_point
 */
package junit.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cutter_point.bean.product.Brand;
import com.cutter_point.service.product.BrandService;

public class BrandTest
{
	//测试spring是否可以运作
	private static ApplicationContext cxt;
	private static BrandService bs;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		try
		{
			cxt = new ClassPathXmlApplicationContext("config/spring/beans.xml");
			bs = (BrandService) cxt.getBean("brandServiceBean");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testSave()
	{
		for(int i = 0; i < 20; ++i)
		{
			Brand b = new Brand();
			b.setName(i + "肖锋瑜伽");
			b.setLogopath("image/brand/2015/05/22/cutter_point.gif");
			bs.save(b);
		}
	}
}
