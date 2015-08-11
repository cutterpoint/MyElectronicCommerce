/**
 * ���ܣ�����Ʒ������ʵ��bean
 * �ļ���Brand.java
 * ʱ�䣺2015��5��22��16:33:49
 * ���ߣ�cutter_point
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
	private String code;	//Ʒ�ƵĴ���
	private String name;	//Ʒ����
	private String logopath;		//���ͼƬ��·��
	private boolean visible = true;  //�����ŵ��������ǲ���ɾ������������ֻ���Ƿ�ɼ��Ĳ���
	
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
	 * �����ͼƬ��·��
	 * ��ʽ�� /images/brand/��/��/��/ʱ/aaaa.gif  �����趨������80
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
