/**
 * ���ܣ������Ʒ�Ʋ�Ʒ�Ĺ�����
 * ʱ�䣺2015��5��20��15:40:17
 * �ļ���ProductTypeManageAction.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.web.action.product;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.bean.product.Brand;
import com.cutter_point.service.product.BrandService;
import com.cutter_point.utils.SiteUrl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class BrandManageAction extends ActionSupport implements ServletRequestAware
{
	private static final long serialVersionUID = -1810478269301025945L;
	@Resource
	private BrandService brandService;	//ͨ���ӿ�ע�룬aopĬ�ϵķ�ʽ
	private File logofile;		//�ϴ����ļ�
	private String logofileContentType;	//�����struts2�Զ�������������,�ļ�����������
	private String logofileFileName;	//�����struts2�Զ�������������,�ϴ��ļ���
	private String name;		//�ϴ���Ʒ�Ƶ�����
	private String code;	//Ʒ�ƴ���
	private String logoimagepath;	//logoͼƬ·��
	private HttpServletRequest request;
	
	/**
	 * ��ʾƷ����ӽ���
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String addUI() throws Exception
	{
		return "add";
	}
	
	/**
	 * Ʒ����Ӳ���,�ϴ��ļ���ʽ2
	 * @return
	 * @throws Exception
	 */
	public String add2() throws Exception
	{
		//���Ǳ����ļ��ĸ�ʽ��images/brand/2015/5/23/ssss.gif
		//G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ �������realpath
		String realpath = ServletActionContext.getServletContext().getRealPath("/images");
		//�������ǹ涨һ�¸�ʽ
		SimpleDateFormat dateformat = new SimpleDateFormat("yyy\\MM\\dd\\HH");
		String logopath = realpath + "brand\\" + dateformat.format(new Date());	//����ͼƬ�����Ŀ¼
		Brand brand = new Brand();
		brand.setName(this.getName());
		//�ж��ļ��Ƿ��ȡ,�ļ���ȡ�����ҳ��ȴ���0
		if(this.getLogofile() != null && this.getLogofile().length() > 0)
		{
			File logosavedir = new File(logopath);	//�ļ��ı���·��
			if(!logosavedir.exists())
			{
				//����ļ�����·�������ڣ����Ǿʹ������·��
				logosavedir.mkdirs();
			}
			//�ļ�������
//			String imagename = UUID.randomUUID().toString();	//�����ļ�����
			//�ļ��������Ӧ��Ŀ¼,���� parent ����·������ child ·�����ַ�������һ���� File ʵ����
			File savefile = new File(logosavedir, this.getLogofileFileName());
			FileUtils.copyFile(logofile, savefile);
		}
		brandService.save(brand);
		request.setAttribute("message", "���Ʒ�Ƴɹ�");
		return "message";
	}
	
	/**
	 * Ʒ����Ӳ���,�ϴ��ļ���ʽ1
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception
	{
		//���ϴ�֮ǰ�ж��ϴ�ͼƬ�����ǲ��Ƿ���Ҫ��
		if(!this.validateFileType("logofile"))
		{
			request.setAttribute("message", "ͼƬ��ʽ����");
			request.setAttribute("urladdress", SiteUrl.readUrl("control.brand.list"));
			return "message";
		}
		//���Ǳ����ļ��ĸ�ʽ��images/brand/2015/5/23/ssss.gif
		//G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ �������realpath
		String realpath = ServletActionContext.getServletContext().getRealPath("/images");
		//�������ǹ涨һ�¸�ʽ
		SimpleDateFormat dateformat = new SimpleDateFormat("yyy\\MM\\dd\\HH");
		String logopathdir = realpath + "brand\\" + dateformat.format(new Date());	//����ͼƬ�����Ŀ¼
		Brand brand = new Brand();
		brand.setName(this.getName());
		//�ж��ļ��Ƿ��ȡ,�ļ���ȡ�����ҳ��ȴ���0
		if(this.getLogofile() != null && this.getLogofile().length() > 0)
		{
			File logosavedir = new File(logopathdir);	//�ļ��ı���·��
			if(!logosavedir.exists())
			{
				//����ļ�����·�������ڣ����Ǿʹ������·��
				logosavedir.mkdirs();
			}
			//�õ�ͼƬ��׺
			String ext = this.getLogofileFileName().substring(this.getLogofileFileName().lastIndexOf('.'));
			//�ļ�����ʾ��ַ
			String showpath = ".." + logosavedir.toString().substring(logosavedir.toString().lastIndexOf("\\images"));
			String imagename = UUID.randomUUID().toString() + ext;	//�����ļ�����
			//�������ļ�����������
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try
			{
				String logopath = logosavedir + "\\" + imagename;
				showpath += "\\" + imagename;
				//�����ļ������
				fos = new FileOutputStream(logopath);
				//�����ļ��ϴ���
				fis = new FileInputStream(this.getLogofile());
				//�趨һ���ֽڻ���
				byte[] buffer = new byte[2048];
				int len = 0;		//ÿ���ϴ��ĳ���
				//���ϵش��ļ��ϴ�������������
				while((len = fis.read(buffer)) != -1)
				{
					//���
					fos.write(buffer, 0, len);
				}
				showpath = showpath.replace("\\", "/");  //�滻�ַ��������������·��
				brand.setLogopath(showpath);
			} 
			catch (Exception e)
			{
				System.out.println("�ļ��ϴ�ʧ��");
				e.printStackTrace();
			}
			finally
			{
				this.close(fos, fis);
			}
		}
		brandService.save(brand);
		request.setAttribute("message", "���Ʒ�Ƴɹ�");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.brand.list"));
		return "message";
	}
	
	
	/**
	 * ��ʾ����޸Ľ���
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String editUI() throws Exception
	{
		//ȡ����Ӧ��ֵ�����޸ĵĵط�ȥ
		Brand brand = brandService.find(Brand.class, this.getCode());	//��ȡ��Ӧ�Ķ���ʵ��
		name = brand.getName();	//��ȡҪ�޸ĵĶ��������
		logoimagepath = brand.getLogopath();	//�õ�ͼƬ·��
		request.setAttribute("name", name);
		request.setAttribute("logoimagepath", logoimagepath);
		return "edit";
	}
	
	/**
	 * ����޸Ĳ���
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception
	{
		//HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get("request");
		//���ϴ�֮ǰ�ж��ϴ�ͼƬ�����ǲ��Ƿ���Ҫ��
    	if(!this.validateFileType("logofile"))
    	{
    		request.setAttribute("message", "ͼƬ��ʽ����");
    		request.setAttribute("urladdress", SiteUrl.readUrl("control.brand.list"));
    		return "message";
    	}
		Brand brand = brandService.find(Brand.class, this.getCode());	//�ҵ���Ӧ�Ķ���
		brand.setName(this.getName());	//�µ�����
		//�ж��ļ��Ƿ��ȡ,�ļ���ȡ�����ҳ��ȴ���0
		if(this.getLogofile() != null && this.getLogofile().length() > 0)	//������������µ�ͼƬ����ô���޸�Ϊ�µ�ͼƬ
		{
			//���Ǳ����ļ��ĸ�ʽ��images/brand/2015/5/23/ssss.gif
			//G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ �������realpath
			String realpath = ServletActionContext.getServletContext().getRealPath("/images");
			//�������ǹ涨һ�¸�ʽ
			SimpleDateFormat dateformat = new SimpleDateFormat("yyy\\MM\\dd\\HH");
			String logopathdir = realpath + "brand\\" + dateformat.format(new Date());	//����ͼƬ�����Ŀ¼
			File logosavedir = new File(logopathdir);	//�ļ��ı���·��
			if(!logosavedir.exists())
			{
				//����ļ�����·�������ڣ����Ǿʹ������·��
				logosavedir.mkdirs();
			}
			//�õ�ͼƬ��׺
			String ext = this.getLogofileFileName().substring(this.getLogofileFileName().lastIndexOf('.'));
			//�ļ�������
			String showpath = ".." + logosavedir.toString().substring(logosavedir.toString().lastIndexOf("\\images"));
			String imagename = UUID.randomUUID().toString() + ext;	//�����ļ�����
			//�������ļ�����������
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try
			{
				String logopath = logosavedir + "\\" + imagename;
				showpath += "\\" + imagename;
				//�����ļ������
				fos = new FileOutputStream(logopath);
				//�����ļ��ϴ���
				fis = new FileInputStream(this.getLogofile());
				//�趨һ���ֽڻ���
				byte[] buffer = new byte[2048];
				int len = 0;		//ÿ���ϴ��ĳ���
				//���ϵش��ļ��ϴ�������������
				while((len = fis.read(buffer)) != -1)
				{
					//���
					fos.write(buffer, 0, len);
				}
				showpath = showpath.replace("\\", "/");  //�滻�ַ��������������·��
				brand.setLogopath(showpath);
			} 
			catch (Exception e)
			{
				System.out.println("�ļ��ϴ�ʧ��");
				e.printStackTrace();
			}
			finally
			{
				this.close(fos, fis);
			}
		}
		brandService.update(brand);
		request.setAttribute("message", "�޸�Ʒ�Ƴɹ�");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.brand.list"));
		return "message";
	}
	
	/**
	 * ��ʾ����ѯ����
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String queryUI() throws Exception
	{
		return "query";
	}
	
	/**
	 * �������������֤�ϴ����ļ������ǲ��Ƿ���Ҫ���
	 * @return
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
						List<String> allowType = Arrays.asList("image/bmp", "image/png", "image/gif", "image/jpeg", "image/pjpe", "image/jpg");
						boolean b = allowType.contains(this.getLogofileContentType().toLowerCase());	//�ж������ǲ���������,��Сд�Ƚ�
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
	
	//�����ļ���
	protected void close(FileOutputStream fos, FileInputStream fis)
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
	

	public File getLogofile()
	{
		return logofile;
	}

	public void setLogofile(File logofile)
	{
		this.logofile = logofile;
	}

	public BrandService getBrandService()
	{
		return brandService;
	}

	public void setBrandService(BrandService brandService)
	{
		this.brandService = brandService;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getLogofileContentType()
	{
		return logofileContentType;
	}

	public void setLogofileContentType(String logofileContentType)
	{
		this.logofileContentType = logofileContentType;
	}

	public String getLogofileFileName()
	{
		return logofileFileName;
	}

	public void setLogofileFileName(String logofileFileName)
	{
		this.logofileFileName = logofileFileName;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getLogoimagepath()
	{
		return logoimagepath;
	}

	public void setLogoimagepath(String logoimagepath)
	{
		this.logoimagepath = logoimagepath;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;
	}
}
