/**
 * 功能：这个是文件显示的管理控制器,这个文件控制上传下载是基于hadoop的HDFS
 * 时间：2015年7月23日10:49:09
 * 文件：UploadHDFSManagerAction.java
 * 作者：cutter_point
 */
package com.cutter_point.web.action.hadoop.uploadhdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.hadoop.mapred.JobConf;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.service.hadoop.uploadhdfs.UploadHdfsService;
import com.cutter_point.service.hadoop.uploadhdfs.impl.UploadHdfsServiceBean;
import com.cutter_point.utils.SiteUrl;
import com.cutter_point.web.formbean.hadoop.HadoopForm;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class UploadHDFSManagerAction extends ActionSupport implements ModelDriven<HadoopForm>, ServletRequestAware 
{
	private static final long serialVersionUID = 4070981797577362494L;
	private HadoopForm formbean;
	private File uploadfile;
	private String uploadfileFileName;	//上传文件的名字
	private HttpServletRequest request;
	private UploadHdfsService uploadHdfsService;
	
	/**
	 * 上传界面显示
	 * @return
	 */
	public String addUI()
	{
		System.out.println(formbean.getFilePath());
		return "add";
	}
	
	/**
	 * 上传文件
	 */
	public String add()
	{
		if(this.getUploadfile() != null && this.getUploadfile().length() > 0)
		{
			//如果文件确实上传上来了
			//获取完全的真实路径G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ 这个就是realpath
			String realpath = ServletActionContext.getServletContext().getRealPath("/hadoopfile");
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy\\MM\\dd\\HH");	//设定日期的格式
			//设定文件的保存格式,设置文件的保存路径
			//G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\hdfsupload\2015\7\23
			String realpathdir = realpath + "hdfsupload\\" + dateformat.format(new Date());	//得到系统的时间，以及我们要保存的文件所在位置
			File savedir = new File(realpathdir);
			if(!savedir.exists())//如果文件夹不存在的话
			{
				savedir.mkdirs();	//创建相应的文件夹
			}
			//获取上传的文件名
			String filename = this.getUploadfileFileName();
			//我们保存的文件夹和文件名一起
			String savedirAndName = realpathdir + "\\" + filename;
			//使用IO流保存文件
			FileOutputStream fos = null;	//输出流
			FileInputStream fis = null;	//输入流
			
			try 
			{
				fos = new FileOutputStream(savedirAndName);
				fis = new FileInputStream(this.getUploadfile());
				//设置我们的文件上传的缓存
				byte[] buffer = new byte[2048];
				int len = 0;
				//从内存中读取buffer的字节数到文件到输入流
				while((len = fis.read(buffer)) != -1)
				{
					fos.write(buffer, 0, len);
				}
				
				//吧文件上传到hdfs
				//吧信息提取出来，放到一个list中
				//先得设置我们的hadoop配置文件，设置好了之后才可以运行相应的功能，这个其实可以交给spring托管，吧他放到构造函数中
				JobConf conf = UploadHdfsServiceBean.config();
				uploadHdfsService = new UploadHdfsServiceBean(conf);
				//获取当前路径，吧文件保存到当前路径
				uploadHdfsService.copyFile(savedirAndName, HadoopForm.getcurrentPath(formbean.getFilePath()));
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
			
			request.setAttribute("message", "上传文件成功");
			//跳转到文件上传成功的界面
		}
		else
		{
			request.setAttribute("message", "请上传文件");
		}
		
		return "hdfsfileuploadfinish";
	}
	
	//删除hdfs中的文件
	public String delete() throws Exception
	{
		//先得设置我们的hadoop配置文件，设置好了之后才可以运行相应的功能，这个其实可以交给spring托管，吧他放到构造函数中
		JobConf conf = UploadHdfsServiceBean.config();
		uploadHdfsService = new UploadHdfsServiceBean(conf);
		
		//得到我们数据在hdfs中的数据
		String delpath = HadoopForm.getcurrentPath(formbean.getFilePath());
		uploadHdfsService.rmr(delpath);
		
//		request.setAttribute("message", "删除文件成功");
//		request.setAttribute("urladdress", SiteUrl.readUrl("control.hdfsfile.list"));
		request.setAttribute("message", "文件删除成功");
		return "hdfsfileuploadfinish";
	}
	
	//下载hdfs中的文件
	public String download() throws Exception
	{
		//先得设置我们的hadoop配置文件，设置好了之后才可以运行相应的功能，这个其实可以交给spring托管，吧他放到构造函数中
		JobConf conf = UploadHdfsServiceBean.config();
		uploadHdfsService = new UploadHdfsServiceBean(conf);
		//默认下载到F盘
		String local = "F:/hadoop/other";
		String downpath = HadoopForm.getcurrentPath(formbean.getFilePath()); //得到在hdfs中的路径
		uploadHdfsService.download(downpath, local);
		
//		request.setAttribute("message", "下载文件成功");
//		request.setAttribute("urladdress", SiteUrl.readUrl("control.hdfsfile.list"));
		request.setAttribute("message", "文件下载成功");
		return "hdfsfileuploadfinish";
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

	@Override
	public void setServletRequest(HttpServletRequest arg0) 
	{
		this.request = arg0;
	}

	@Override
	public HadoopForm getModel() 
	{
		//从页面获取表单值
		if(formbean == null)
			formbean = new HadoopForm();
		
		return formbean;
	}

	public HadoopForm getFormbean() {
		return formbean;
	}

	public void setFormbean(HadoopForm formbean) {
		this.formbean = formbean;
	}

	public File getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(File uploadfile) {
		this.uploadfile = uploadfile;
	}

	public String getUploadfileFileName() {
		return uploadfileFileName;
	}

	public void setUploadfileFileName(String uploadfileFileName) {
		this.uploadfileFileName = uploadfileFileName;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
