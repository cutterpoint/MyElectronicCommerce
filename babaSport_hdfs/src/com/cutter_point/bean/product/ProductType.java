/**
 * ���ܣ����ǲ�Ʒ����
 * �ļ���ProductType.java
 * ʱ�䣺2015��5��12��10:16:21
 * ���ߣ�cutter_point
 */
package com.cutter_point.bean.product;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;


@Entity
@SequenceGenerator(name="seq_1",sequenceName="seq_1",allocationSize=1,initialValue=1)
public class ProductType implements Serializable
{
	private static final long serialVersionUID = -6904074615993718149L;
	
	/** ����id **/
	private Integer typeid;
	private String name;	//�����
	private String note;	//��ע�����ڰٶ�����
	private boolean visible = true;	//�Ƿ�ɼ�
	//��������
	private Set<ProductType> childtypes = new HashSet<ProductType>();
	//��������
	private ProductType parent;
	//һ����Ʒ�����������Ʒ
	private Set<ProductInfo> products = new HashSet<ProductInfo>();
	
	public ProductType(String name, String note)
	{
		this.name = name;
		this.note = note;
	}

	public ProductType(Integer typeid)
	{
		this.typeid = typeid;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeid == null) ? 0 : typeid.hashCode());
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
		ProductType other = (ProductType) obj;
		if (typeid == null)
		{
			if (other.typeid != null)
				return false;
		} else if (!typeid.equals(other.typeid))
			return false;
		return true;
	}
	
	//����ɾ����˫���ϵ���ӵ�productinfo��type����
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "type")
	public Set<ProductInfo> getProducts()
	{
		return products;
	}

	public void setProducts(Set<ProductInfo> products)
	{
		this.products = products;
	}

	@OneToMany(cascade={CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy="parent", fetch=FetchType.EAGER)//����Ǽ������º�ɾ����mappedByָ���Ƕ����ڱ�ӵ�з��ģ���ָ��ӵ�з�
	//parent��ӳ��,һ��������Ӧ,�����fetch���ӳټ����õ�
	public Set<ProductType> getChildtypes()
	{
		return childtypes;
	}

	public void setChildtypes(Set<ProductType> childtypes)
	{
		this.childtypes = childtypes;
	}
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="parentid")
	public ProductType getParent()
	{
		return parent;
	}

	public void setParent(ProductType parent)
	{
		this.parent = parent;
	}

	public ProductType()
	{
	}
	
	@Column(length=36, nullable=false)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column(length=200)
	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
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

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_1")		//������
	public Integer getTypeid()
	{
		return typeid;
	}

	public void setTypeid(Integer typeid)
	{
		this.typeid = typeid;
	}
}
