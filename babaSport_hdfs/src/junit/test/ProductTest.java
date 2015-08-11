/**
 * ���ܣ����ǲ�Ʒ���ĵ�Ԫ����
 * �ļ���ProductTest.java
 * ʱ�䣺2015��5��12��10:27:24
 * ���ߣ�cutter_point
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
	//����spring�Ƿ��������
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
		ProductType pt = new ProductType();	//newһ������
		pt.setTypeid(78);	//����id����
		Configuration cfg = new Configuration();	//�õ�Configuration
		@SuppressWarnings("deprecation")
		SessionFactory sf = cfg.configure("/config/hibernate/hibernate.cfg.xml").buildSessionFactory();	//ȡ��session����
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
		//����spring�Ƿ��������
		ApplicationContext cxt = new ClassPathXmlApplicationContext("config/spring/beans.xml");
		DataSource datasource = (DataSource)cxt.getBean("myDataSource");	//ȡ��һ������
		System.out.println(datasource);	//�ж��ǲ���Ϊ�գ�
	}
	
	@SuppressWarnings("resource")
	@Test
	public void testSH()
	{
		//����spring�Ƿ��������
		try
		{
			ApplicationContext cxt = new ClassPathXmlApplicationContext("config/spring/beans.xml");
			ProductTypeService productService = (ProductTypeService)cxt.getBean(ProductTypeService.class);	//ȡ��һ������
			ProductType pt = new ProductType();
			pt.setName("cutter_point");
			pt.setNote("�ǳ���");
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
			type.setName(i + " �ܲ���Ʒ");
			type.setNote("�й����ܲ�2");
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
		pt.setNote("�����й���");
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
		QueryResult<ProductType> qr = pts.getScrollData(ProductType.class);//ȡ��0��5��������
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
