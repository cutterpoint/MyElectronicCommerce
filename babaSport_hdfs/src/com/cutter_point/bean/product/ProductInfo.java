/**
 * 功能：产品信息的实体类
 * 文件：ProductInfo.java
 * 时间：2015年5月22日16:33:49
 * 作者：cutter_point
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
	//产品的id号
	private Integer id;	
	//货号
	private String code;
	//产品名
	private String name;
	//品牌
	private Brand brand;	
	//型号
	private String model;	
	//底价，也就是采购价
	private Float baseprice;	
	//市场价
	private Float marketprice;	
	//销售价
	private Float sellprice;	
	//产品重量
	private Integer weight;		
	//产品简介
	private String description;	
	//购买说明
	private String buyexplain;	
	//是否可见
	private Boolean visible = true;	
	//产品型号
	private ProductType type;	
	//上架日期
	private Date createdate = new Date();		
	//人气指数
	private Integer clickcount = 1;		
	//销售量
	private Integer sellcount = 0;		
	//是否推荐
	private Boolean commend = false;	
	//性别要求
	private Sex sexrequest = Sex.NONE;
	
	//一个不再数据库中使用的字段，来表示节省的钱
	@SuppressWarnings("unused")
	private Float savedPrice;
	
	//一种产品会有多个样式
	private Set<ProductStyle> styles = new HashSet<ProductStyle>();
	
	
	public ProductInfo()
	{
	}
	
	/**
	 * 由于这个也是一个属性，那么我们如果不想把这个也映射到数据库中去，那么我们加一个注解@Transactional
	 * 这个是为算出节省的钱数
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
	//一个品牌有多个产品
	@ManyToOne(cascade = CascadeType.REFRESH)	//级联更新
	//设定一个外键
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
	//市场价
	@Column(nullable = false)
	public Float getMarketprice()
	{
		return marketprice;
	}
	public void setMarketprice(Float marketprice)
	{
		this.marketprice = marketprice;
	}
	//销售价
	@Column(nullable = false)
	public Float getSellprice()
	{
		return sellprice;
	}
	public void setSellprice(Float sellprice)
	{
		this.sellprice = sellprice;
	}
	//重量,可以为空
	public Integer getWeight()
	{
		return weight;
	}
	public void setWeight(Integer weight)
	{
		this.weight = weight;
	}
	//lob表示可以存放大点的数据
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
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)	//级联更新，并且这个产品类型必须存在
	@JoinColumn(name = "typeid")	//设置外键
	public ProductType getType()
	{
		return type;
	}
	public void setType(ProductType type)
	{
		this.type = type;
	}
	@Temporal(TemporalType.DATE)	//时间
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
	//枚举类型,EnumType.ORDINAL表示在数据库中存放相应的数字，EnumType.STRING表示在数据库中存放相应的字符串
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
	//产品和样式之间的关系是一对多,这里定义级联删除,每个样式对应相应的产品
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
	
	//添加一个样式
	public void addProductStyle(ProductStyle style)
	{
		if(!this.styles.contains(style))
		{
			this.styles.add(style);	//添加一个样式
			//同时让这个样式对应这种产品
			style.setProduct(this);
		}
	}
	
	//删除一个样式
	public void removeProductStyle(ProductStyle style)
	{
		if(this.styles.contains(style))	//如果包含了这个样式
		{
			this.styles.remove(style);	//添加一个样式
			//同时让这个样式对应这种产品
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
