/**
 * ���ܣ�������ļ���ʾ�Ŀ�����,����ļ���ʾ�ǻ���hadoop��HDFS
 * ʱ�䣺2015��7��22��10:54:50
 * �ļ���UploadHDFS.java
 * ���ߣ�cutter_point
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
		//��ʾ���е��ļ�
		//�õ������
		String path;
		if(formbean.getFilePath() == null)
		{
			path = "/";
			formbean.setFilePath("hdfs://master:9000/");
		}
		else
		{
			//���ǵ���һ��·��hdfs://master:9000/cutter_point��������ӵ�
			String s = formbean.getFilePath();
			path = s.substring(s.indexOf("9000") + 4);
		}
//		System.out.println(formbean.getFilePath());
		//����Ϣ��ȡ�������ŵ�һ��list��
		//�ȵ��������ǵ�hadoop�����ļ������ú���֮��ſ���������Ӧ�Ĺ��ܣ������ʵ���Խ���spring�йܣ������ŵ����캯����
		JobConf conf = UploadHdfsServiceBean.config();
		uploadHdfsService = new UploadHdfsServiceBean(conf);
		FileStatus[] filelist = uploadHdfsService.ls(path);
		HdfsFile hfs;
		List<HdfsFile> hfss = new ArrayList();
		for(FileStatus f : filelist)
		{
			hfs = new HdfsFile();
			hfs.setFilename(f.getPath().getName());
			hfs.setLength(f.getLen());  //������k����ʾ
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
		//��ҳ���ȡ��ֵ
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
