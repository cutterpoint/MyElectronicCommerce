/**
 * 功能：这是产品类别的单元测试
 * 文件：ProductTest.java
 * 时间：2015年5月12日10:27:24
 * 作者：cutter_point
 */
package junit.test;

import java.util.LinkedHashMap;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cutter_point.bean.QueryResult;
import com.cutter_point.bean.product.ProductType;
import com.cutter_point.service.product.ProductTypeService;

public class ProductTest
{
	//测试spring是否可以运作
	private static ApplicationContext cxt;
	private static ProductTypeService pts;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		try
		{
			
			cxt = new ClassPathXmlApplicationContext("config/spring/beans.xml");
			pts = (ProductTypeService) cxt.getBean("productTypeServiceBean");
		} 
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
	}

	@Test
	public void test()
	{
		ProductType pt = new ProductType();	//new一个对象
		pt.setTypeid(78);	//设置id号码
		Configuration cfg = new Configuration();	//得到Configuration
		@SuppressWarnings("deprecation")
		SessionFactory sf = cfg.configure("/config/hibernate/hibernate.cfg.xml").buildSessionFactory();	//取得session工厂
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(pt);
		session.getTransaction().commit();
		session.close();
		sf.close();
	}
	
	@SuppressWarnings("resource")
	@Test
	public void testSpring()
	{
		//测试spring是否可以运作
		ApplicationContext cxt = new ClassPathXmlApplicationContext("config/spring/beans.xml");
		DataSource datasource = (DataSource)cxt.getBean("myDataSource");	//取出一个对象
		System.out.println(datasource);	//判断是不是为空，
	}
	
	@SuppressWarnings("resource")
	@Test
	public void testSH()
	{
		//测试spring是否可以运作
		try
		{
			ApplicationContext cxt = new ClassPathXmlApplicationContext("config/spring/beans.xml");
			ProductTypeService productService = (ProductTypeService)cxt.getBean(ProductTypeService.class);	//取出一个对象
			ProductType pt = new ProductType();
			pt.setName("cutter_point");
			pt.setNote("非常好");
			productService.save(pt);
		} catch (BeansException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSave()
	{
		for(int i = 0; i < 20; ++i)
		{
			ProductType type = new ProductType();
			type.setName(i + " 跑步用品");
			type.setNote("中国好跑步2");
			pts.save(type);
		}
	}
	
	@Test
	public void testFind()
	{
		ProductType pt = pts.find(ProductType.class, 3);
		Assert.assertNotNull(pt);
		System.out.println(pt.getName());
		
	}
	
	@Test
	public void testUpdate()
	{
		ProductType pt = pts.find(ProductType.class, 5);
		pt.setName("cutter_point666");
		pt.setNote("出彩中国人");
		pts.update(pt);
	}
	
	@Test
	public void testDelete()
	{
		pts.delete(ProductType.class, 3);
	}
	
	@Test
	public void testgetScrollData()
	{
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("typeid", "asc");
		QueryResult<ProductType> qr = pts.getScrollData(ProductType.class);//取出0到5条的数据
//		Iterator<ProductType> it = qr.getResultList().iterator();
//		while(it.hasNext())
//		{
//			ProductType pt = it.next();
//			System.out.println(pt.getName());
//		}
		for(Object o : qr.getResultList())
		{
			ProductType t = (ProductType) o;
			System.out.println(t.getName());
			t.getChildtypes().size();
		}
	}
}
