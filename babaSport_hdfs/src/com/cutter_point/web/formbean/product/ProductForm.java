/**
 * 功能：用来接受从add_product.jsp表单提交上来的数据
 * 文件：BaseForm.java
 * 时间：2015年5月27日19:35:02
 * 作者：cutter_poin
 */
package com.cutter_point.web.formbean.product;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.cutter_point.utils.ImageSizer;
import com.cutter_point.utils.SiteUrl;
import com.cutter_point.web.formbean.BaseForm;

public class ProductForm extends BaseForm
{
	/** 产品id **/
	private Integer productid;
	/** 接受id数组 **/
	private Integer[] productids;
	/** 货号 **/
	private String code;
	/** 产品名称 **/
	private String name;
	/** 品牌 **/
	private String brandid;
	/** 型号 **/
	private String model;
	/** 底价(采购进来的价格) **/
	private Float baseprice;
	/** 市场价 **/
	private Float marketprice;
	/** 销售价 **/
	private Float sellprice;
	/** 重量 单位:克 **/
	private Integer weight;
	/** 产品简介 **/
	private String description;
	/** 购买说明 **/
	private String buyexplain;
	/** 产品类型 **/
	private Integer typeid;
	/** 性别要求 **/
	private String sex;
	/** 样式名称 **/
	private String stylename;
	private File imagefile;	//图片文件
	private String imagefileContentType;	//这个是struts2自动传进来的属性,文件的内容类型
	private String imagefileFileName;	//这个是struts2自动传进来的属性,上传文件名
	//低价查询区间
	private Float startbaseprice;
	private Float endbaseprice;
	//销售价(元)区间
	private Float startsellprice;
	private Float endsellprice;
	private Integer productstyleid;
	private Integer[] stylesids;
	
	public ProductForm()
	{
	}
	
	/**
	 * 保存产品图片
	 * @param imageFile 上传的文件图片
	 * @param imageFileName 上传文件的名字
	 * @param productTypeid 产品的类型id
	 * @param productid	产品的id
	 * @param realpath	真实路径
	 * @throws Exception
	 */
	public static void saveProductImageFile(File imageFile, String imageFileName, int productTypeid, int productid, String realpath) throws Exception
	{
		/***************************************************************************************************************
		 * 								文件上传  													    				  **
		 ***************************************************************************************************************/
		//保存的路径就是
		String savedir = realpath + "product\\" + productTypeid + "\\" + productid + "\\phototype";
		//压缩图片之后的保存路径,表示宽度是140px
		String savedir140 = realpath + "product\\" + productTypeid + "\\" + productid + "\\140x";
		//文件名
		//String imagename = pf.getImagefileFileName();
		//创建一个目录文件，如果目录文件不存在就创建相应的目录
		File f = new File(savedir);
		File f140 = new File(savedir140);
		if(!f.exists())
		{
			f.mkdirs();	//创建相应的目录
		}
		
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try
		{
			//为了把文件输入进去，最后的文件名得用\\隔开
			String yasuoshowpath = f140 + "\\" + imageFileName;
			File file = new File(f, imageFileName);
			File file140 = new File(yasuoshowpath);
			//建立文件输出流
			fos = new FileOutputStream(file);
			//这里压缩图片的 
			//建立文件上传流
			fis = new FileInputStream(imageFile);
			//设定一个字节缓存
			byte[] buffer = new byte[1024];
			int len = 0;		//每次上传的长度
			//不断地从文件上传流输出到输出流
			while((len = fis.read(buffer)) != -1)
			{
				//输出
				fos.write(buffer, 0, len);
			}
			
			if(!f140.exists())
			{
				f140.mkdirs();	//创建相应的目录
			}
			//开始压缩图片的大小规范
			ImageSizer.resize(file, file140, 140, "gif");
		} 
		catch (Exception e)
		{
			System.out.println("文件上传失败");
			e.printStackTrace();
		}
		finally
		{
			if(fis != null)
			{
				try
				{
					fis.close();
				} 
				catch (Exception e)
				{
					System.out.println("关闭文件输入流失败");
					e.printStackTrace();
				}
			}
			
			if(fos != null)
			{
				try
				{
					fos.close();
				} 
				catch (Exception e)
				{
					System.out.println("关闭文件输出流失败");
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 判断得到文件类型是不是我们允许的类型
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public boolean validateFileType(String propertyName) throws Exception
	{
		//得到相应类的所有属性，字段
		PropertyDescriptor[] propertydesc = Introspector.getBeanInfo(this.getClass()).getPropertyDescriptors();
		boolean exsit = false;	//判断属性是否存在的变量
		for(PropertyDescriptor property : propertydesc)		//相当于类里面的属性，字段
		{
			if(property.getName().equals(propertyName))
			{
				//名字得到匹配的话,属性是存在
				exsit = true;
				Method method = property.getReadMethod();	//取得用来读取属性的方法，也就是取得get方法
				if(method != null)
				{
					File file = (File) method.invoke(this);	//执行这个方法
					//文件是存在的
					if(file != null && file.length() > 0)
					{
//						List<String> allowType = Arrays.asList("image/bmp", "image/png", "image/gif", "image/jpeg", "image/pjpe", "image/jpg", 
//								"application/x-shockwave-flash", "application/octet-stream", "application/msword", "application/pdf", "text/plain",
//								"application/vnd.ms-excel", "<extension>ppt</extension>", 
//								"application/vnd.openxmlformats-officedocument.wordprocessingml.document");
						//验证后缀
//						List<String> allowExtension = Arrays.asList("bmp","png","gif","jpeg","pjpe",
//								"jpg","exe","doc","pdf","txt","xls","ppt","swf","docx");
						List<String> allowType = new ArrayList<String>();
						Properties p = SiteUrl.readallowtyope();
						for(Object key : p.keySet())
						{
							String value = (String) p.get(key);
							String[] values = value.split(",");	//分割得到的属性
							for(String v : values)
							{
								//吧所有的类型添加进去
								allowType.add(v.trim());	//去掉空格
							}
						}
						
						//获取的结果是不包含.的
						String ext = this.getImagefileFileName().substring(this.getImagefileFileName().lastIndexOf(".") + 1).toLowerCase();
						boolean b = allowType.contains(this.getImagefileContentType().toLowerCase());	//判断类型是不是在里面,用小写比较
						b = b && p.keySet().contains(ext);
						return b;
					}
				}
				else
				{
					//如果文件没有穿进来
					new RuntimeException(propertyName + "属性的getter方法不存在");
				}
			}
		}
		
		if(!exsit)
			new RuntimeException(propertyName + "属性的不存在");
		return true;	//如果没有上传文件的话，还是让他通过
	}
	
	public Integer[] getStylesids() {
		return stylesids;
	}

	public void setStylesids(Integer[] stylesids) {
		this.stylesids = stylesids;
	}

	public Integer getProductstyleid() {
		return productstyleid;
	}

	public void setProductstyleid(Integer productstyleid) {
		this.productstyleid = productstyleid;
	}

	public Integer getProductid()
	{
		return productid;
	}
	public void setProductid(Integer productid)
	{
		this.productid = productid;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getBrandid()
	{
		return brandid;
	}
	public void setBrandid(String brandid)
	{
		this.brandid = brandid;
	}
	public String getModel()
	{
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	public Float getBaseprice()
	{
		return baseprice;
	}
	public void setBaseprice(Float baseprice)
	{
		this.baseprice = baseprice;
	}
	public Float getMarketprice()
	{
		return marketprice;
	}
	public void setMarketprice(Float marketprice)
	{
		this.marketprice = marketprice;
	}
	public Float getSellprice()
	{
		return sellprice;
	}
	public void setSellprice(Float sellprice)
	{
		this.sellprice = sellprice;
	}
	public Integer getWeight()
	{
		return weight;
	}
	public void setWeight(Integer weight)
	{
		this.weight = weight;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getBuyexplain()
	{
		return buyexplain;
	}
	public void setBuyexplain(String buyexplain)
	{
		this.buyexplain = buyexplain;
	}
	public Integer getTypeid()
	{
		return typeid;
	}
	public void setTypeid(Integer typeid)
	{
		this.typeid = typeid;
	}
	public String getSex()
	{
		return sex;
	}
	public void setSex(String sex)
	{
		this.sex = sex;
	}
	public String getStylename() {
		return stylename;
	}
	public void setStylename(String stylename) {
		this.stylename = stylename;
	}
	public File getImagefile() {
		return imagefile;
	}
	public void setImagefile(File imagefile) {
		this.imagefile = imagefile;
	}
	public String getImagefileContentType() {
		return imagefileContentType;
	}
	public void setImagefileContentType(String imagefileContentType) {
		this.imagefileContentType = imagefileContentType;
	}
	public String getImagefileFileName() {
		return imagefileFileName;
	}
	public void setImagefileFileName(String imagefileFileName) {
		this.imagefileFileName = imagefileFileName;
	}

	public Float getStartbaseprice() {
		return startbaseprice;
	}

	public void setStartbaseprice(Float startbaseprice) {
		this.startbaseprice = startbaseprice;
	}

	public Float getEndbaseprice() {
		return endbaseprice;
	}

	public void setEndbaseprice(Float endbaseprice) {
		this.endbaseprice = endbaseprice;
	}

	public Float getStartsellprice() {
		return startsellprice;
	}

	public void setStartsellprice(Float startsellprice) {
		this.startsellprice = startsellprice;
	}

	public Float getEndsellprice() {
		return endsellprice;
	}

	public void setEndsellprice(Float endsellprice) {
		this.endsellprice = endsellprice;
	}

	public Integer[] getProductids() {
		return productids;
	}

	public void setProductids(Integer[] productids) {
		this.productids = productids;
	}
	
}
