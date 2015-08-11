/**
 * 功能：这个是文件的管理动作
 * 时间：2015年5月25日09:45:53
 * 文件：UploadfileManageAction.java
 * 作者：cutter_point
 */
package com.cutter_point.web.action.uploadfile;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.bean.uploadfile.UploadFile;
import com.cutter_point.service.uploadfile.UploadFileService;
import com.cutter_point.utils.SiteUrl;
import com.opensymphony.xwork2.ActionSupport;


@Controller
@Scope("prototype")
public class UploadfileManageAction extends ActionSupport implements ServletRequestAware
{
	private static final long serialVersionUID = -815949461566116034L;
	//业务处理spring注入
	@Resource
	private UploadFileService uploadFileService;
	//通过实现接口获取相应的request
	private HttpServletRequest request;
	//上传文件需要的属性
	private File uploadfile;
	private String uploadfileFileName;	//上传文件的名字
	private String uploadfileContentType;	//这个是相应的文件类型在G:\Program Files\Apache Software Foundation\Tomcat 8.0\conf\web.xml中的文件类型对应的东西
	//从界面接受被选中要删除的id号
	private Integer[] fileids;
	
	/**
	 * 上传界面显示
	 * @return
	 */
	public String uploadUI()
	{
		return "uploadUI";
	}
	
	/**
	 * 保存上传文件
	 * @return
	 */
	public String upload() throws Exception
	{
		if(!this.validateFileType("uploadfile"))
		{
			request.setAttribute("message", "上传文件格式不对");
			return "error";
		}
		
		if(this.getUploadfile() != null && this.getUploadfile().length() > 0)
		{
			//如果文件确实上传上来了
			//获取完全的真实路径G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ 这个就是realpath
			String realpath = ServletActionContext.getServletContext().getRealPath("/images");
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy\\MM\\dd\\HH");	//设定日期的格式
			//设定文件的保存格式
			String realpathdir = realpath + "upload\\" + dateformat.format(new Date());	//得到系统的时间，以及我们要保存的文件所在位置
			File savedir = new File(realpathdir);
			if(!savedir.exists())//如果文件夹不存在的话
			{
				savedir.mkdirs();	//创建相应的文件夹
			}
			//获取相应的文件后缀
			//String ext = "." + this.getUploadfileContentType();	//得到相应的文件后缀，带.的
			//显示图片的时候我们有一个相应的路径,类似这个样../images/upload/2015/5/25/19
			String showpath = ".." + savedir.toString().substring(savedir.toString().lastIndexOf("\\images"));
			//这里保存文件的名称的时候，我们就用原来的名称
			String uploadname = this.getUploadfileFileName();
			//初始化输出流,和输入流
			String savefilepath = savedir + "\\" + uploadname;
			//设定实体的各项属性,保存显示的路径，用来web显示
			UploadFile uploadFile = new UploadFile(showpath + "\\" + uploadname);
			//使用IO流保存文件
			FileOutputStream fos = null;	//输出流
			FileInputStream fis = null;	//输入流
			try
			{
				fos = new FileOutputStream(savefilepath);
				fis = new FileInputStream(this.getUploadfile());
				//设定字节缓存池和长度
				byte[] buffer = new byte[2048];
				int len = 0;	//每次上传文件的长度
				while((len = fis.read(buffer)) != -1)
				{
					//调用输出流到相应的地方
					fos.write(buffer, 0, len); //要输出的字节，开始的位置，结束的位置
				}
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
			//保存到数据库
			uploadFileService.save(uploadFile);
			request.setAttribute("message", "上传文件成功");
			//跳转到文件上传成功的界面
			return "fileuploadfinish";
		}
		else
		{
			request.setAttribute("message", "请上传文件");
		}
		return "message";
	}
	
	public String delete()
	{
		//获取要删除的路径对象
		List<String> files = uploadFileService.getFilepath(this.getFileids());
		//我们获取的id号不为空，才会删除
		if(files != null)
		{
			//循环删除file:..\images\\upload\2015\05\26\10\2.5年, 从0-阿里.docx
			for(String file : files)
			{
				//获取真实路径，通过真实路径删除
				//获取完全的真实路径G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\2015\05\26\15\.. 这个就是realpath
				String hpath = file.substring(9);
				System.out.println("help查询真实路径：" + hpath);
				String realpath = ServletActionContext.getServletContext().getRealPath("/images");
				System.out.println("真实路径是：" + realpath);
				//我们通过File指向这个文件
				File deletefile = new File(realpath + hpath);
				System.out.println("要删除的路径是：" + deletefile);
				//判断这个文件是不是存在
				if(deletefile.exists())
				{
					//如果存在的话删除掉
					deletefile.delete();
				}
			}
			//从数据库中删除相应的文件
			uploadFileService.delete(UploadFile.class, this.getFileids());
		}
		
		request.setAttribute("urladdress", SiteUrl.readUrl("control.uploadfile.list"));
		request.setAttribute("message", "删除成功");
		return "message";
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
	
	/**
	 * 判断得到文件类型是不是我们允许的类型
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	protected boolean validateFileType(String propertyName) throws Exception
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
						String ext = this.getUploadfileFileName().substring(this.getUploadfileFileName().lastIndexOf(".") + 1).toLowerCase();
						boolean b = allowType.contains(this.getUploadfileContentType().toLowerCase());	//判断类型是不是在里面,用小写比较
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
	
	public File getUploadfile()
	{
		return uploadfile;
	}

	public void setUploadfile(File uploadfile)
	{
		this.uploadfile = uploadfile;
	}

	public UploadFileService getUploadFileService()
	{
		return uploadFileService;
	}

	public void setUploadFileService(UploadFileService uploadFileService)
	{
		this.uploadFileService = uploadFileService;
	}

	public String getUploadfileFileName()
	{
		return uploadfileFileName;
	}

	public void setUploadfileFileName(String uploadfileFileName)
	{
		this.uploadfileFileName = uploadfileFileName;
	}

	@Override	//获取到相应的request
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;
	}

	public String getUploadfileContentType()
	{
		return uploadfileContentType;
	}

	public void setUploadfileContentType(String uploadfileContentType)
	{
		this.uploadfileContentType = uploadfileContentType;
	}

	public Integer[] getFileids()
	{
		return fileids;
	}

	public void setFileids(Integer[] fileids)
	{
		this.fileids = fileids;
	}
}
