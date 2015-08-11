/**
 * ���ܣ�����Ʒ�����ĵ�Ԫ����
 * �ļ���BrandTest.java
 * ʱ�䣺2015��5��22��17:20:28
 * ���ߣ�cutter_point
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
	//����spring�Ƿ��������
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
			b.setName(i + "Ф���٤");
			b.setLogopath("image/brand/2015/05/22/cutter_point.gif");
			bs.save(b);
		}
	}
}
