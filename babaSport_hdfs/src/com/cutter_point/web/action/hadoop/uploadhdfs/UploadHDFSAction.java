/**
 * 功能：这个是文件显示的控制器,这个文件显示是基于hadoop的HDFS
 * 时间：2015年7月22日10:54:50
 * 文件：UploadHDFS.java
 * 作者：cutter_point
 */
package com.cutter_point.web.action.hadoop.uploadhdfs;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.mapred.JobConf;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.bean.hadoop.uploadhdfs.HdfsFile;
import com.cutter_point.service.hadoop.uploadhdfs.UploadHdfsService;
import com.cutter_point.service.hadoop.uploadhdfs.impl.UploadHdfsServiceBean;
import com.cutter_point.web.formbean.hadoop.HadoopForm;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class UploadHDFSAction extends ActionSupport implements ServletRequestAware, ModelDriven<HadoopForm>
{

	private static final long serialVersionUID = -5895766259414269404L;
	
	private HttpServletRequest request;
	private HadoopForm formbean;
	private UploadHdfsService uploadHdfsService;
	
	@Override
	public String execute() throws Exception 
	{
		//显示所有的文件
		//得到结果集
		String path;
		if(formbean.getFilePath() == null)
		{
			path = "/";
			formbean.setFilePath("hdfs://master:9000/");
		}
		else
		{
			//我们调整一下路径hdfs://master:9000/cutter_point，这个样子的
			String s = formbean.getFilePath();
			path = s.substring(s.indexOf("9000") + 4);
		}
//		System.out.println(formbean.getFilePath());
		//吧信息提取出来，放到一个list中
		//先得设置我们的hadoop配置文件，设置好了之后才可以运行相应的功能，这个其实可以交给spring托管，吧他放到构造函数中
		JobConf conf = UploadHdfsServiceBean.config();
		uploadHdfsService = new UploadHdfsServiceBean(conf);
		FileStatus[] filelist = uploadHdfsService.ls(path);
		HdfsFile hfs;
		List<HdfsFile> hfss = new ArrayList();
		for(FileStatus f : filelist)
		{
			hfs = new HdfsFile();
			hfs.setFilename(f.getPath().getName());
			hfs.setLength(f.getLen());  //长度用k来表示
			hfs.setPath(f.getPath());
			if(f.isDir())
			{
				hfs.setDir(true);
			}
			hfss.add(hfs);
		}
		//request.setAttribute("filePath", path);
		request.setAttribute("hfss", hfss);
		return "listhdfs";
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

	public UploadHdfsService getUploadHdfsService() {
		return uploadHdfsService;
	}

	public void setUploadHdfsService(UploadHdfsService uploadHdfsService) {
		this.uploadHdfsService = uploadHdfsService;
	}
}
