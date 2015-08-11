/**
 * ���ܣ���ȡ�����ļ����������
 * ʱ�䣺2015��5��24��20:52:02
 * �ļ���SiteUrl.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.utils;

import java.io.IOException;
import java.util.Properties;

public class SiteUrl
{
	private static Properties properties = new Properties();		//�����ļ��ļ���
	
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
		Properties properties = new Properties();	//�õ��������͵ļ�����
		//��ȡ���Եļ���
		try
		{
			//�õ������������·����Ȼ�����������������·���ҵ���Ӧ�������ļ�
			properties.load(SiteUrl.class.getClassLoader().getResourceAsStream("config/properties/siteurl.properties"));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return (String) properties.get(key);	//����keyֵ��ȡ��Ӧ������ֵ
	}
	
}
