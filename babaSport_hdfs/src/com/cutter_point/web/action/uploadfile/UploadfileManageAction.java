/**
 * ���ܣ�������ļ��Ĺ�����
 * ʱ�䣺2015��5��25��09:45:53
 * �ļ���UploadfileManageAction.java
 * ���ߣ�cutter_point
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
	//ҵ����springע��
	@Resource
	private UploadFileService uploadFileService;
	//ͨ��ʵ�ֽӿڻ�ȡ��Ӧ��request
	private HttpServletRequest request;
	//�ϴ��ļ���Ҫ������
	private File uploadfile;
	private String uploadfileFileName;	//�ϴ��ļ�������
	private String uploadfileContentType;	//�������Ӧ���ļ�������G:\Program Files\Apache Software Foundation\Tomcat 8.0\conf\web.xml�е��ļ����Ͷ�Ӧ�Ķ���
	//�ӽ�����ܱ�ѡ��Ҫɾ����id��
	private Integer[] fileids;
	
	/**
	 * �ϴ�������ʾ
	 * @return
	 */
	public String uploadUI()
	{
		return "uploadUI";
	}
	
	/**
	 * �����ϴ��ļ�
	 * @return
	 */
	public String upload() throws Exception
	{
		if(!this.validateFileType("uploadfile"))
		{
			request.setAttribute("message", "�ϴ��ļ���ʽ����");
			return "error";
		}
		
		if(this.getUploadfile() != null && this.getUploadfile().length() > 0)
		{
			//����ļ�ȷʵ�ϴ�������
			//��ȡ��ȫ����ʵ·��G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ �������realpath
			String realpath = ServletActionContext.getServletContext().getRealPath("/images");
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy\\MM\\dd\\HH");	//�趨���ڵĸ�ʽ
			//�趨�ļ��ı����ʽ
			String realpathdir = realpath + "upload\\" + dateformat.format(new Date());	//�õ�ϵͳ��ʱ�䣬�Լ�����Ҫ������ļ�����λ��
			File savedir = new File(realpathdir);
			if(!savedir.exists())//����ļ��в����ڵĻ�
			{
				savedir.mkdirs();	//������Ӧ���ļ���
			}
			//��ȡ��Ӧ���ļ���׺
			//String ext = "." + this.getUploadfileContentType();	//�õ���Ӧ���ļ���׺����.��
			//��ʾͼƬ��ʱ��������һ����Ӧ��·��,���������../images/upload/2015/5/25/19
			String showpath = ".." + savedir.toString().substring(savedir.toString().lastIndexOf("\\images"));
			//���ﱣ���ļ������Ƶ�ʱ�����Ǿ���ԭ��������
			String uploadname = this.getUploadfileFileName();
			//��ʼ�������,��������
			String savefilepath = savedir + "\\" + uploadname;
			//�趨ʵ��ĸ�������,������ʾ��·��������web��ʾ
			UploadFile uploadFile = new UploadFile(showpath + "\\" + uploadname);
			//ʹ��IO�������ļ�
			FileOutputStream fos = null;	//�����
			FileInputStream fis = null;	//������
			try
			{
				fos = new FileOutputStream(savefilepath);
				fis = new FileInputStream(this.getUploadfile());
				//�趨�ֽڻ���غͳ���
				byte[] buffer = new byte[2048];
				int len = 0;	//ÿ���ϴ��ļ��ĳ���
				while((len = fis.read(buffer)) != -1)
				{
					//�������������Ӧ�ĵط�
					fos.write(buffer, 0, len); //Ҫ������ֽڣ���ʼ��λ�ã�������λ��
				}
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
			//���浽���ݿ�
			uploadFileService.save(uploadFile);
			request.setAttribute("message", "�ϴ��ļ��ɹ�");
			//��ת���ļ��ϴ��ɹ��Ľ���
			return "fileuploadfinish";
		}
		else
		{
			request.setAttribute("message", "���ϴ��ļ�");
		}
		return "message";
	}
	
	public String delete()
	{
		//��ȡҪɾ����·������
		List<String> files = uploadFileService.getFilepath(this.getFileids());
		//���ǻ�ȡ��id�Ų�Ϊ�գ��Ż�ɾ��
		if(files != null)
		{
			//ѭ��ɾ��file:..\images\\upload\2015\05\26\10\2.5��, ��0-����.docx
			for(String file : files)
			{
				//��ȡ��ʵ·����ͨ����ʵ·��ɾ��
				//��ȡ��ȫ����ʵ·��G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\2015\05\26\15\.. �������realpath
				String hpath = file.substring(9);
				System.out.println("help��ѯ��ʵ·����" + hpath);
				String realpath = ServletActionContext.getServletContext().getRealPath("/images");
				System.out.println("��ʵ·���ǣ�" + realpath);
				//����ͨ��Fileָ������ļ�
				File deletefile = new File(realpath + hpath);
				System.out.println("Ҫɾ����·���ǣ�" + deletefile);
				//�ж�����ļ��ǲ��Ǵ���
				if(deletefile.exists())
				{
					//������ڵĻ�ɾ����
					deletefile.delete();
				}
			}
			//�����ݿ���ɾ����Ӧ���ļ�
			uploadFileService.delete(UploadFile.class, this.getFileids());
		}
		
		request.setAttribute("urladdress", SiteUrl.readUrl("control.uploadfile.list"));
		request.setAttribute("message", "ɾ���ɹ�");
		return "message";
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
	
	/**
	 * �жϵõ��ļ������ǲ����������������
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	protected boolean validateFileType(String propertyName) throws Exception
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
						String ext = this.getUploadfileFileName().substring(this.getUploadfileFileName().lastIndexOf(".") + 1).toLowerCase();
						boolean b = allowType.contains(this.getUploadfileContentType().toLowerCase());	//�ж������ǲ���������,��Сд�Ƚ�
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

	@Override	//��ȡ����Ӧ��request
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
