/**
 * ���ܣ��Ա����
 * �ļ���Sex.java
 * ʱ�䣺2015��5��26��19:25:01
 * ���ߣ�cutter_point
 */
package com.cutter_point.bean.product;

public enum Sex
{
	NONE 
	{
		@Override
		public String getName()
		{
			return "��Ů����";
		}
	},
	MAN 
	{
		@Override
		public String getName()
		{
			return "��ʿ";
		}
	},
	WOMEN 
	{
		@Override
		public String getName()
		{
			return "Ůʿ";
		}
	};
	public abstract String getName();
}
