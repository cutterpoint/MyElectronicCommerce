/**
 * 功能：这是产品样式的实体
 * 文件：ProductStyle.java
 * 时间：2015年5月27日09:18:23
 * 作者：cutter_point
 */
package com.cutter_point.bean.product;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
@SequenceGenerator(name="seq_productstyle",sequenceName="seq_productstyle",allocationSize=1,initialValue=1)
public class ProductStyle implements Serializable
{
	private static final long serialVersionUID = -6670329064347582109L;
	//id号
	private Integer id;
	//样式名字
	private String name;
	//样式照片的名字
	private String imagename;
	//是否可见
	private Boolean visible = true;
	//许多的样式都对应着一种产品
	private ProductInfo product;
	//图片的全路径
	@SuppressWarnings("unused")
	private String imageFullPath;
	@SuppressWarnings("unused")
	private String image140FullPath;
	
	public ProductStyle(Integer id) {
		this.id = id;
	}

	public ProductStyle()
	{
	}
	
	/**
	 * 由于这个也是一个属性，那么我们如果不想把这个也映射到数据库中去，那么我们加一个注解@Transactional
	 * 这个是为获取这个文件图片的全路径，但是这个路径不包含真实路径
	 * @return String
	 */
	@Transient
	public String getImageFullPath()
	{
		String imageFullPath = "images/product/" + this.getProduct().getType().getTypeid() +"/" +
					this.getProduct().getId()+ "/phototype/" + this.imagename;
		
		return imageFullPath;
	}
	
	/**
	 * 由于这个也是一个属性，那么我们如果不想把这个也映射到数据库中去，那么我们加一个注解@Transactional
	 * 这个是为获取这个文件图片的140x格式全路径，但是这个路径不包含真实路径
	 * @return String
	 */
	@Transient
	public String getImage140FullPath()
	{
		String imageFullPath = "images/product/" + this.getProduct().getType().getTypeid() +"/" +
					this.getProduct().getId()+ "/140x/" + this.imagename;
		
		return imageFullPath;
	}
	
	public ProductStyle(String name, String imagename)
	{
		this.name = name;
		this.imagename = imagename;
	}
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_productstyle")		//设定为主键，且自增长
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	@Column(length = 30, nullable = false)
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	@Column(length = 40, nullable = false)
	public String getImagename()
	{
		return imagename;
	}
	public void setImagename(String imagename)
	{
		this.imagename = imagename;
	}
	@Column(columnDefinition="char(1)", nullable = false)
	public Boolean getVisible()
	{
		return visible;
	}
	public void setVisible(Boolean visible)
	{
		this.visible = visible;
	}
	//级联更新，并且必须选择
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	//设定外键
	@JoinColumn(name = "productid")
	public ProductInfo getProduct()
	{
		return product;
	}
	public void setProduct(ProductInfo product)
	{
		this.product = product;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ProductStyle other = (ProductStyle) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
