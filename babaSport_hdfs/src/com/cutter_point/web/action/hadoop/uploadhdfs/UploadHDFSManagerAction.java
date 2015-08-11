/**
 * ���ܣ�������ļ���ʾ�Ĺ��������,����ļ������ϴ������ǻ���hadoop��HDFS
 * ʱ�䣺2015��7��23��10:49:09
 * �ļ���UploadHDFSManagerAction.java
 * ���ߣ�cutter_point
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
	private String uploadfileFileName;	//�ϴ��ļ�������
	private HttpServletRequest request;
	private UploadHdfsService uploadHdfsService;
	
	/**
	 * �ϴ�������ʾ
	 * @return
	 */
	public String addUI()
	{
		System.out.println(formbean.getFilePath());
		return "add";
	}
	
	/**
	 * �ϴ��ļ�
	 */
	public String add()
	{
		if(this.getUploadfile() != null && this.getUploadfile().length() > 0)
		{
			//����ļ�ȷʵ�ϴ�������
			//��ȡ��ȫ����ʵ·��G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ �������realpath
			String realpath = ServletActionContext.getServletContext().getRealPath("/hadoopfile");
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy\\MM\\dd\\HH");	//�趨���ڵĸ�ʽ
			//�趨�ļ��ı����ʽ,�����ļ��ı���·��
			//G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\hdfsupload\2015\7\23
			String realpathdir = realpath + "hdfsupload\\" + dateformat.format(new Date());	//�õ�ϵͳ��ʱ�䣬�Լ�����Ҫ������ļ�����λ��
			File savedir = new File(realpathdir);
			if(!savedir.exists())//����ļ��в����ڵĻ�
			{
				savedir.mkdirs();	//������Ӧ���ļ���
			}
			//��ȡ�ϴ����ļ���
			String filename = this.getUploadfileFileName();
			//���Ǳ�����ļ��к��ļ���һ��
			String savedirAndName = realpathdir + "\\" + filename;
			//ʹ��IO�������ļ�
			FileOutputStream fos = null;	//�����
			FileInputStream fis = null;	//������
			
			try 
			{
				fos = new FileOutputStream(savedirAndName);
				fis = new FileInputStream(this.getUploadfile());
				//�������ǵ��ļ��ϴ��Ļ���
				byte[] buffer = new byte[2048];
				int len = 0;
				//���ڴ��ж�ȡbuffer���ֽ������ļ���������
				while((len = fis.read(buffer)) != -1)
				{
					fos.write(buffer, 0, len);
				}
				
				//���ļ��ϴ���hdfs
				//����Ϣ��ȡ�������ŵ�һ��list��
				//�ȵ��������ǵ�hadoop�����ļ������ú���֮��ſ���������Ӧ�Ĺ��ܣ������ʵ���Խ���spring�йܣ������ŵ����캯����
				JobConf conf = UploadHdfsServiceBean.config();
				uploadHdfsService = new UploadHdfsServiceBean(conf);
				//��ȡ��ǰ·�������ļ����浽��ǰ·��
				uploadHdfsService.copyFile(savedirAndName, HadoopForm.getcurrentPath(formbean.getFilePath()));
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
			
			request.setAttribute("message", "�ϴ��ļ��ɹ�");
			//��ת���ļ��ϴ��ɹ��Ľ���
		}
		else
		{
			request.setAttribute("message", "���ϴ��ļ�");
		}
		
		return "hdfsfileuploadfinish";
	}
	
	//ɾ��hdfs�е��ļ�
	public String delete() throws Exception
	{
		//�ȵ��������ǵ�hadoop�����ļ������ú���֮��ſ���������Ӧ�Ĺ��ܣ������ʵ���Խ���spring�йܣ������ŵ����캯����
		JobConf conf = UploadHdfsServiceBean.config();
		uploadHdfsService = new UploadHdfsServiceBean(conf);
		
		//�õ�����������hdfs�е�����
		String delpath = HadoopForm.getcurrentPath(formbean.getFilePath());
		uploadHdfsService.rmr(delpath);
		
//		request.setAttribute("message", "ɾ���ļ��ɹ�");
//		request.setAttribute("urladdress", SiteUrl.readUrl("control.hdfsfile.list"));
		request.setAttribute("message", "�ļ�ɾ���ɹ�");
		return "hdfsfileuploadfinish";
	}
	
	//����hdfs�е��ļ�
	public String download() throws Exception
	{
		//�ȵ��������ǵ�hadoop�����ļ������ú���֮��ſ���������Ӧ�Ĺ��ܣ������ʵ���Խ���spring�йܣ������ŵ����캯����
		JobConf conf = UploadHdfsServiceBean.config();
		uploadHdfsService = new UploadHdfsServiceBean(conf);
		//Ĭ�����ص�F��
		String local = "F:/hadoop/other";
		String downpath = HadoopForm.getcurrentPath(formbean.getFilePath()); //�õ���hdfs�е�·��
		uploadHdfsService.download(downpath, local);
		
//		request.setAttribute("message", "�����ļ��ɹ�");
//		request.setAttribute("urladdress", SiteUrl.readUrl("control.hdfsfile.list"));
		request.setAttribute("message", "�ļ����سɹ�");
		return "hdfsfileuploadfinish";
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
