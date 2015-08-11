/**
 * 功能：获取配置文件里面的属性
 * 时间：2015年5月24日20:52:02
 * 文件：SiteUrl.java
 * 作者：cutter_point
 */
package com.cutter_point.utils;

import java.io.IOException;
import java.util.Properties;

public class SiteUrl
{
	private static Properties properties = new Properties();		//配置文件的集合
	
	public static Properties readallowtyope()
	{
		try
		{
			properties.load(SiteUrl.class.getClassLoader().getResourceAsStream("config/properties/allowuploadfiletype.properties"));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return properties;
	}
	
	public static String readUrl(String key)
	{
		Properties properties = new Properties();	//得到属性类型的集合类
		//获取属性的集合
		try
		{
			//得到这个类的类加载路径，然后根据这个类加载器的路径找到对应的配置文件
			properties.load(SiteUrl.class.getClassLoader().getResourceAsStream("config/properties/siteurl.properties"));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return (String) properties.get(key);	//根据key值获取相应的属性值
	}
	
}
