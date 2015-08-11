/**
 * ���ܣ��������ܴ�add_product.jsp���ύ����������
 * �ļ���BaseForm.java
 * ʱ�䣺2015��5��27��19:35:02
 * ���ߣ�cutter_poin
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
	/** ��Ʒid **/
	private Integer productid;
	/** ����id���� **/
	private Integer[] productids;
	/** ���� **/
	private String code;
	/** ��Ʒ���� **/
	private String name;
	/** Ʒ�� **/
	private String brandid;
	/** �ͺ� **/
	private String model;
	/** �׼�(�ɹ������ļ۸�) **/
	private Float baseprice;
	/** �г��� **/
	private Float marketprice;
	/** ���ۼ� **/
	private Float sellprice;
	/** ���� ��λ:�� **/
	private Integer weight;
	/** ��Ʒ��� **/
	private String description;
	/** ����˵�� **/
	private String buyexplain;
	/** ��Ʒ���� **/
	private Integer typeid;
	/** �Ա�Ҫ�� **/
	private String sex;
	/** ��ʽ���� **/
	private String stylename;
	private File imagefile;	//ͼƬ�ļ�
	private String imagefileContentType;	//�����struts2�Զ�������������,�ļ�����������
	private String imagefileFileName;	//�����struts2�Զ�������������,�ϴ��ļ���
	//�ͼ۲�ѯ����
	private Float startbaseprice;
	private Float endbaseprice;
	//���ۼ�(Ԫ)����
	private Float startsellprice;
	private Float endsellprice;
	private Integer productstyleid;
	private Integer[] stylesids;
	
	public ProductForm()
	{
	}
	
	/**
	 * �����ƷͼƬ
	 * @param imageFile �ϴ����ļ�ͼƬ
	 * @param imageFileName �ϴ��ļ�������
	 * @param productTypeid ��Ʒ������id
	 * @param productid	��Ʒ��id
	 * @param realpath	��ʵ·��
	 * @throws Exception
	 */
	public static void saveProductImageFile(File imageFile, String imageFileName, int productTypeid, int productid, String realpath) throws Exception
	{
		/***************************************************************************************************************
		 * 								�ļ��ϴ�  													    				  **
		 ***************************************************************************************************************/
		//�����·������
		String savedir = realpath + "product\\" + productTypeid + "\\" + productid + "\\phototype";
		//ѹ��ͼƬ֮��ı���·��,��ʾ�����140px
		String savedir140 = realpath + "product\\" + productTypeid + "\\" + productid + "\\140x";
		//�ļ���
		//String imagename = pf.getImagefileFileName();
		//����һ��Ŀ¼�ļ������Ŀ¼�ļ������ھʹ�����Ӧ��Ŀ¼
		File f = new File(savedir);
		File f140 = new File(savedir140);
		if(!f.exists())
		{
			f.mkdirs();	//������Ӧ��Ŀ¼
		}
		
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try
		{
			//Ϊ�˰��ļ������ȥ�������ļ�������\\����
			String yasuoshowpath = f140 + "\\" + imageFileName;
			File file = new File(f, imageFileName);
			File file140 = new File(yasuoshowpath);
			//�����ļ������
			fos = new FileOutputStream(file);
			//����ѹ��ͼƬ�� 
			//�����ļ��ϴ���
			fis = new FileInputStream(imageFile);
			//�趨һ���ֽڻ���
			byte[] buffer = new byte[1024];
			int len = 0;		//ÿ���ϴ��ĳ���
			//���ϵش��ļ��ϴ�������������
			while((len = fis.read(buffer)) != -1)
			{
				//���
				fos.write(buffer, 0, len);
			}
			
			if(!f140.exists())
			{
				f140.mkdirs();	//������Ӧ��Ŀ¼
			}
			//��ʼѹ��ͼƬ�Ĵ�С�淶
			ImageSizer.resize(file, file140, 140, "gif");
		} 
		catch (Exception e)
		{
			System.out.println("�ļ��ϴ�ʧ��");
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
					System.out.println("�ر��ļ�������ʧ��");
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
					System.out.println("�ر��ļ������ʧ��");
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * �жϵõ��ļ������ǲ����������������
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public boolean validateFileType(String propertyName) throws Exception
	{
		//�õ���Ӧ����������ԣ��ֶ�
		PropertyDescriptor[] propertydesc = Introspector.getBeanInfo(this.getClass()).getPropertyDescriptors();
		boolean exsit = false;	//�ж������Ƿ���ڵı���
		for(PropertyDescriptor property : propertydesc)		//�൱������������ԣ��ֶ�
		{
			if(property.getName().equals(propertyName))
			{
				//���ֵõ�ƥ��Ļ�,�����Ǵ���
				exsit = true;
				Method method = property.getReadMethod();	//ȡ��������ȡ���Եķ�����Ҳ����ȡ��get����
				if(method != null)
				{
					File file = (File) method.invoke(this);	//ִ���������
					//�ļ��Ǵ��ڵ�
					if(file != null && file.length() > 0)
					{
//						List<String> allowType = Arrays.asList("image/bmp", "image/png", "image/gif", "image/jpeg", "image/pjpe", "image/jpg", 
//								"application/x-shockwave-flash", "application/octet-stream", "application/msword", "application/pdf", "text/plain",
//								"application/vnd.ms-excel", "<extension>ppt</extension>", 
//								"application/vnd.openxmlformats-officedocument.wordprocessingml.document");
						//��֤��׺
//						List<String> allowExtension = Arrays.asList("bmp","png","gif","jpeg","pjpe",
//								"jpg","exe","doc","pdf","txt","xls","ppt","swf","docx");
						List<String> allowType = new ArrayList<String>();
						Properties p = SiteUrl.readallowtyope();
						for(Object key : p.keySet())
						{
							String value = (String) p.get(key);
							String[] values = value.split(",");	//�ָ�õ�������
							for(String v : values)
							{
								//�����е�������ӽ�ȥ
								allowType.add(v.trim());	//ȥ���ո�
							}
						}
						
						//��ȡ�Ľ���ǲ�����.��
						String ext = this.getImagefileFileName().substring(this.getImagefileFileName().lastIndexOf(".") + 1).toLowerCase();
						boolean b = allowType.contains(this.getImagefileContentType().toLowerCase());	//�ж������ǲ���������,��Сд�Ƚ�
						b = b && p.keySet().contains(ext);
						return b;
					}
				}
				else
				{
					//����ļ�û�д�����
					new RuntimeException(propertyName + "���Ե�getter����������");
				}
			}
		}
		
		if(!exsit)
			new RuntimeException(propertyName + "���ԵĲ�����");
		return true;	//���û���ϴ��ļ��Ļ�����������ͨ��
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
