/**
 * ���ܣ�����һ��ͼƬѹ���ĵ�Ԫ����
 * �ļ���imageSizeTest.java
 * ʱ�䣺2015��6��2��16:08:08
 * ���ߣ�cutter_point
 */
package junit.test;

import java.io.File;

import org.junit.Test;

import com.cutter_point.utils.ImageSizer;

public class imageSizeTest {

	@Test
	public void test() 
	{
		try {
			ImageSizer.resize(new File("C:\\Users\\feng\\Desktop\\123.png"), new File("C:\\Users\\feng\\Desktop\\222.png"), 150, "gif");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
