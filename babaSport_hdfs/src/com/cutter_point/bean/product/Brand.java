/**
 * 功能：这是品牌类别的实体bean
 * 文件：Brand.java
 * 时间：2015年5月22日16:33:49
 * 作者：cutter_point
 */
package com.cutter_point.bean.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Brand implements Serializable
{
	private static final long serialVersionUID = -2902298582228419157L;
	private String code;	//品牌的代码
	private String name;	//品牌名
	private String logopath;		//存放图片的路径
	private boolean visible = true;  //这里存放的数据我们不做删除操作，我们只做是否可见的操作
	
	public Brand()
	{
	}
	public Brand(String name, String logopath)
	{
		this.name = name;
		this.logopath = logopath;
	}
	
	public Brand(String code)
	{
		this.code = code;
	}
	@Id
	@Column(length = 36)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	
	@Column(length = 40, nullable = false)
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * 这个是图片的路径
	 * 格式： /images/brand/年/月/日/时/aaaa.gif  长度设定可以是80
	 * @return
	 */
	@Column(length = 200)
	public String getLogopath()
	{
		return logopath;
	}
	public void setLogopath(String logopath)
	{
		this.logopath = logopath;
	}
	
	@Column(columnDefinition="char(1)", nullable = false)
	public boolean isVisible()
	{
		return visible;
	}
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Brand other = (Brand) obj;
		if (code == null)
		{
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
}
