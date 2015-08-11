/**
 * 功能：这个是品牌产品的管理动作
 * 时间：2015年5月20日15:40:17
 * 文件：ProductTypeManageAction.java
 * 作者：cutter_point
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
	private BrandService brandService;	//通过接口注入，aop默认的方式
	private File logofile;		//上传的文件
	private String logofileContentType;	//这个是struts2自动传进来的属性,文件的内容类型
	private String logofileFileName;	//这个是struts2自动传进来的属性,上传文件名
	private String name;		//上传的品牌的名字
	private String code;	//品牌代码
	private String logoimagepath;	//logo图片路径
	private HttpServletRequest request;
	
	/**
	 * 显示品牌添加界面
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String addUI() throws Exception
	{
		return "add";
	}
	
	/**
	 * 品牌添加操作,上传文件方式2
	 * @return
	 * @throws Exception
	 */
	public String add2() throws Exception
	{
		//我们保存文件的格式是images/brand/2015/5/23/ssss.gif
		//G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ 这个就是realpath
		String realpath = ServletActionContext.getServletContext().getRealPath("/images");
		//这里我们规定一下格式
		SimpleDateFormat dateformat = new SimpleDateFormat("yyy\\MM\\dd\\HH");
		String logopath = realpath + "brand\\" + dateformat.format(new Date());	//构建图片保存的目录
		Brand brand = new Brand();
		brand.setName(this.getName());
		//判断文件是否获取,文件获取到，且长度大于0
		if(this.getLogofile() != null && this.getLogofile().length() > 0)
		{
			File logosavedir = new File(logopath);	//文件的保存路径
			if(!logosavedir.exists())
			{
				//如果文件保存路径不存在，我们就创建这个路径
				logosavedir.mkdirs();
			}
			//文件的名字
//			String imagename = UUID.randomUUID().toString();	//构建文件名称
			//文件输出到相应的目录,根据 parent 抽象路径名和 child 路径名字符串创建一个新 File 实例。
			File savefile = new File(logosavedir, this.getLogofileFileName());
			FileUtils.copyFile(logofile, savefile);
		}
		brandService.save(brand);
		request.setAttribute("message", "添加品牌成功");
		return "message";
	}
	
	/**
	 * 品牌添加操作,上传文件方式1
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception
	{
		//再上传之前判断上传图片类型是不是符合要求
		if(!this.validateFileType("logofile"))
		{
			request.setAttribute("message", "图片格式不对");
			request.setAttribute("urladdress", SiteUrl.readUrl("control.brand.list"));
			return "message";
		}
		//我们保存文件的格式是images/brand/2015/5/23/ssss.gif
		//G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ 这个就是realpath
		String realpath = ServletActionContext.getServletContext().getRealPath("/images");
		//这里我们规定一下格式
		SimpleDateFormat dateformat = new SimpleDateFormat("yyy\\MM\\dd\\HH");
		String logopathdir = realpath + "brand\\" + dateformat.format(new Date());	//构建图片保存的目录
		Brand brand = new Brand();
		brand.setName(this.getName());
		//判断文件是否获取,文件获取到，且长度大于0
		if(this.getLogofile() != null && this.getLogofile().length() > 0)
		{
			File logosavedir = new File(logopathdir);	//文件的保存路径
			if(!logosavedir.exists())
			{
				//如果文件保存路径不存在，我们就创建这个路径
				logosavedir.mkdirs();
			}
			//得到图片后缀
			String ext = this.getLogofileFileName().substring(this.getLogofileFileName().lastIndexOf('.'));
			//文件的显示地址
			String showpath = ".." + logosavedir.toString().substring(logosavedir.toString().lastIndexOf("\\images"));
			String imagename = UUID.randomUUID().toString() + ext;	//构建文件名称
			//这里用文件流来传进来
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try
			{
				String logopath = logosavedir + "\\" + imagename;
				showpath += "\\" + imagename;
				//建立文件输出流
				fos = new FileOutputStream(logopath);
				//建立文件上传流
				fis = new FileInputStream(this.getLogofile());
				//设定一个字节缓存
				byte[] buffer = new byte[2048];
				int len = 0;		//每次上传的长度
				//不断地从文件上传流输出到输出流
				while((len = fis.read(buffer)) != -1)
				{
					//输出
					fos.write(buffer, 0, len);
				}
				showpath = showpath.replace("\\", "/");  //替换字符串，这里用相对路径
				brand.setLogopath(showpath);
			} 
			catch (Exception e)
			{
				System.out.println("文件上传失败");
				e.printStackTrace();
			}
			finally
			{
				this.close(fos, fis);
			}
		}
		brandService.save(brand);
		request.setAttribute("message", "添加品牌成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.brand.list"));
		return "message";
	}
	
	
	/**
	 * 显示类别修改界面
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String editUI() throws Exception
	{
		//取得相应的值传到修改的地方去
		Brand brand = brandService.find(Brand.class, this.getCode());	//获取相应的对象实体
		name = brand.getName();	//获取要修改的对象的名字
		logoimagepath = brand.getLogopath();	//得到图片路径
		request.setAttribute("name", name);
		request.setAttribute("logoimagepath", logoimagepath);
		return "edit";
	}
	
	/**
	 * 类别修改操作
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception
	{
		//HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get("request");
		//再上传之前判断上传图片类型是不是符合要求
    	if(!this.validateFileType("logofile"))
    	{
    		request.setAttribute("message", "图片格式不对");
    		request.setAttribute("urladdress", SiteUrl.readUrl("control.brand.list"));
    		return "message";
    	}
		Brand brand = brandService.find(Brand.class, this.getCode());	//找到相应的对象
		brand.setName(this.getName());	//新的名字
		//判断文件是否获取,文件获取到，且长度大于0
		if(this.getLogofile() != null && this.getLogofile().length() > 0)	//如果重新填了新的图片，那么就修改为新的图片
		{
			//我们保存文件的格式是images/brand/2015/5/23/ssss.gif
			//G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ 这个就是realpath
			String realpath = ServletActionContext.getServletContext().getRealPath("/images");
			//这里我们规定一下格式
			SimpleDateFormat dateformat = new SimpleDateFormat("yyy\\MM\\dd\\HH");
			String logopathdir = realpath + "brand\\" + dateformat.format(new Date());	//构建图片保存的目录
			File logosavedir = new File(logopathdir);	//文件的保存路径
			if(!logosavedir.exists())
			{
				//如果文件保存路径不存在，我们就创建这个路径
				logosavedir.mkdirs();
			}
			//得到图片后缀
			String ext = this.getLogofileFileName().substring(this.getLogofileFileName().lastIndexOf('.'));
			//文件的名字
			String showpath = ".." + logosavedir.toString().substring(logosavedir.toString().lastIndexOf("\\images"));
			String imagename = UUID.randomUUID().toString() + ext;	//构建文件名称
			//这里用文件流来传进来
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try
			{
				String logopath = logosavedir + "\\" + imagename;
				showpath += "\\" + imagename;
				//建立文件输出流
				fos = new FileOutputStream(logopath);
				//建立文件上传流
				fis = new FileInputStream(this.getLogofile());
				//设定一个字节缓存
				byte[] buffer = new byte[2048];
				int len = 0;		//每次上传的长度
				//不断地从文件上传流输出到输出流
				while((len = fis.read(buffer)) != -1)
				{
					//输出
					fos.write(buffer, 0, len);
				}
				showpath = showpath.replace("\\", "/");  //替换字符串，这里用相对路径
				brand.setLogopath(showpath);
			} 
			catch (Exception e)
			{
				System.out.println("文件上传失败");
				e.printStackTrace();
			}
			finally
			{
				this.close(fos, fis);
			}
		}
		brandService.update(brand);
		request.setAttribute("message", "修改品牌成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.brand.list"));
		return "message";
	}
	
	/**
	 * 显示类别查询界面
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String queryUI() throws Exception
	{
		return "query";
	}
	
	/**
	 * 这儿函数用来验证上传的文件类型是不是符合要求的
	 * @return
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
						List<String> allowType = Arrays.asList("image/bmp", "image/png", "image/gif", "image/jpeg", "image/pjpe", "image/jpg");
						boolean b = allowType.contains(this.getLogofileContentType().toLowerCase());	//判断类型是不是在里面,用小写比较
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
	
	//管理文件流
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
