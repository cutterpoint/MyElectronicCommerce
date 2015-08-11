/**
 * 功能：这是一个图片压缩的单元测试
 * 文件：imageSizeTest.java
 * 时间：2015年6月2日16:08:08
 * 作者：cutter_point
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
