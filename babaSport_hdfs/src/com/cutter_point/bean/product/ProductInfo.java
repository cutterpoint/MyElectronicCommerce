/**
 * ���ܣ���Ʒ��Ϣ��ʵ����
 * �ļ���ProductInfo.java
 * ʱ�䣺2015��5��22��16:33:49
 * ���ߣ�cutter_point
 */
package com.cutter_point.bean.product;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@SequenceGenerator(name="seq_productinfo",sequenceName="seq_productinfo",allocationSize=1,initialValue=1)
public class ProductInfo implements Serializable
{
	private static final long serialVersionUID = -9060015497593350640L;
	//��Ʒ��id��
	private Integer id;	
	//����
	private String code;
	//��Ʒ��
	private String name;
	//Ʒ��
	private Brand brand;	
	//�ͺ�
	private String model;	
	//�׼ۣ�Ҳ���ǲɹ���
	private Float baseprice;	
	//�г���
	private Float marketprice;	
	//���ۼ�
	private Float sellprice;	
	//��Ʒ����
	private Integer weight;		
	//��Ʒ���
	private String description;	
	//����˵��
	private String buyexplain;	
	//�Ƿ�ɼ�
	private Boolean visible = true;	
	//��Ʒ�ͺ�
	private ProductType type;	
	//�ϼ�����
	private Date createdate = new Date();		
	//����ָ��
	private Integer clickcount = 1;		
	//������
	private Integer sellcount = 0;		
	//�Ƿ��Ƽ�
	private Boolean commend = false;	
	//�Ա�Ҫ��
	private Sex sexrequest = Sex.NONE;
	
	//һ���������ݿ���ʹ�õ��ֶΣ�����ʾ��ʡ��Ǯ
	@SuppressWarnings("unused")
	private Float savedPrice;
	
	//һ�ֲ�Ʒ���ж����ʽ
	private Set<ProductStyle> styles = new HashSet<ProductStyle>();
	
	
	public ProductInfo()
	{
	}
	
	/**
	 * �������Ҳ��һ�����ԣ���ô���������������Ҳӳ�䵽���ݿ���ȥ����ô���Ǽ�һ��ע��@Transactional
	 * �����Ϊ�����ʡ��Ǯ��
	 * @return String
	 */
	@Transient
	public Float getSavedPrice()
	{
		return marketprice - sellprice;
	}
	
	public ProductInfo(Integer id)
	{
		this.id = id;
	}
	@Column(length = 30)
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	@Column(length = 50, nullable = false)
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	//һ��Ʒ���ж����Ʒ
	@ManyToOne(cascade = CascadeType.REFRESH)	//��������
	//�趨һ�����
	@JoinColumn(name = "brandid")
	public Brand getBrand()
	{
		return brand;
	}
	public void setBrand(Brand brand)
	{
		this.brand = brand;
	}
	@Column(length = 20)
	public String getModel()
	{
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	@Column(nullable = false)
	public Float getBaseprice()
	{
		return baseprice;
	}
	public void setBaseprice(Float baseprice)
	{
		this.baseprice = baseprice;
	}
	//�г���
	@Column(nullable = false)
	public Float getMarketprice()
	{
		return marketprice;
	}
	public void setMarketprice(Float marketprice)
	{
		this.marketprice = marketprice;
	}
	//���ۼ�
	@Column(nullable = false)
	public Float getSellprice()
	{
		return sellprice;
	}
	public void setSellprice(Float sellprice)
	{
		this.sellprice = sellprice;
	}
	//����,����Ϊ��
	public Integer getWeight()
	{
		return weight;
	}
	public void setWeight(Integer weight)
	{
		this.weight = weight;
	}
	//lob��ʾ���Դ�Ŵ�������
	@Lob @Column(nullable = false)
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	@Column(length = 30)
	public String getBuyexplain()
	{
		return buyexplain;
	}
	public void setBuyexplain(String buyexplain)
	{
		this.buyexplain = buyexplain;
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
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)	//�������£����������Ʒ���ͱ������
	@JoinColumn(name = "typeid")	//�������
	public ProductType getType()
	{
		return type;
	}
	public void setType(ProductType type)
	{
		this.type = type;
	}
	@Temporal(TemporalType.DATE)	//ʱ��
	public Date getCreatedate()
	{
		return createdate;
	}
	public void setCreatedate(Date createdate)
	{
		this.createdate = createdate;
	}
	@Column(nullable = false)
	public Integer getClickcount()
	{
		return clickcount;
	}
	public void setClickcount(Integer clickcount)
	{
		this.clickcount = clickcount;
	}
	@Column(nullable = false)
	public Integer getSellcount()
	{
		return sellcount;
	}
	public void setSellcount(Integer sellcount)
	{
		this.sellcount = sellcount;
	}
	@Column(columnDefinition="char(1)", nullable = false)
	public Boolean getCommend()
	{
		return commend;
	}
	public void setCommend(Boolean commend)
	{
		this.commend = commend;
	}
	//ö������,EnumType.ORDINAL��ʾ�����ݿ��д����Ӧ�����֣�EnumType.STRING��ʾ�����ݿ��д����Ӧ���ַ���
	@Enumerated(EnumType.STRING) @Column(length = 5, nullable = false)
	public Sex getSexrequest()
	{
		return sexrequest;
	}
	public void setSexrequest(Sex sexrequest)
	{
		this.sexrequest = sexrequest;
	}
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_productinfo")
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	//��Ʒ����ʽ֮��Ĺ�ϵ��һ�Զ�,���ﶨ�弶��ɾ��,ÿ����ʽ��Ӧ��Ӧ�Ĳ�Ʒ
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.EAGER)
	@OrderBy("visible desc, id asc")
	public Set<ProductStyle> getStyles()
	{
		return styles;
	}
	public void setStyles(Set<ProductStyle> styles)
	{
		this.styles = styles;
	}
	
	//���һ����ʽ
	public void addProductStyle(ProductStyle style)
	{
		if(!this.styles.contains(style))
		{
			this.styles.add(style);	//���һ����ʽ
			//ͬʱ�������ʽ��Ӧ���ֲ�Ʒ
			style.setProduct(this);
		}
	}
	
	//ɾ��һ����ʽ
	public void removeProductStyle(ProductStyle style)
	{
		if(this.styles.contains(style))	//��������������ʽ
		{
			this.styles.remove(style);	//���һ����ʽ
			//ͬʱ�������ʽ��Ӧ���ֲ�Ʒ
			style.setProduct(null);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductInfo other = (ProductInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
