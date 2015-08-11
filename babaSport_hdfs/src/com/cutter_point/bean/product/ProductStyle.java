/**
 * ���ܣ����ǲ�Ʒ��ʽ��ʵ��
 * �ļ���ProductStyle.java
 * ʱ�䣺2015��5��27��09:18:23
 * ���ߣ�cutter_point
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
	//id��
	private Integer id;
	//��ʽ����
	private String name;
	//��ʽ��Ƭ������
	private String imagename;
	//�Ƿ�ɼ�
	private Boolean visible = true;
	//������ʽ����Ӧ��һ�ֲ�Ʒ
	private ProductInfo product;
	//ͼƬ��ȫ·��
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
	 * �������Ҳ��һ�����ԣ���ô���������������Ҳӳ�䵽���ݿ���ȥ����ô���Ǽ�һ��ע��@Transactional
	 * �����Ϊ��ȡ����ļ�ͼƬ��ȫ·�����������·����������ʵ·��
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
	 * �������Ҳ��һ�����ԣ���ô���������������Ҳӳ�䵽���ݿ���ȥ����ô���Ǽ�һ��ע��@Transactional
	 * �����Ϊ��ȡ����ļ�ͼƬ��140x��ʽȫ·�����������·����������ʵ·��
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
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_productstyle")		//�趨Ϊ��������������
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
	//�������£����ұ���ѡ��
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	//�趨���
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
