/**
 * 功能：性别类别
 * 文件：Sex.java
 * 时间：2015年5月26日19:25:01
 * 作者：cutter_point
 */
package com.cutter_point.bean.product;

public enum Sex
{
	NONE 
	{
		@Override
		public String getName()
		{
			return "男女不限";
		}
	},
	MAN 
	{
		@Override
		public String getName()
		{
			return "男士";
		}
	},
	WOMEN 
	{
		@Override
		public String getName()
		{
			return "女士";
		}
	};
	public abstract String getName();
}
