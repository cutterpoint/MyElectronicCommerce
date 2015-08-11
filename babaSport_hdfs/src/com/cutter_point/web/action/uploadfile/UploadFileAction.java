/**
 * ���ܣ�������ļ���ʾ�Ŀ�����
 * ʱ�䣺2015��5��26��08:36:01
 * �ļ���UploadFileAction.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.web.action.uploadfile;

import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.bean.PageView;
import com.cutter_point.bean.uploadfile.UploadFile;
import com.cutter_point.service.uploadfile.UploadFileService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class UploadFileAction extends ActionSupport implements ServletRequestAware
{
	private static final long serialVersionUID = 7754191209036170135L;
	//ҵ����springע��
	@Resource
	private UploadFileService uploadFileService;
	//ͨ��ʵ�ֽӿڻ�ȡ��Ӧ��request
	private HttpServletRequest request;
	private String filepath;	//�ļ������·��
	private int page;		//��ǰ��ҳ��ֵ

	@Override
	public String execute() throws Exception
	{
		/*
		 * 1����ҳ��pageView�ĳ�ʼ��
		 * 2����ʼ��ÿҳ�ĵ�һ�������ĳ�ʼֵ
		 * 3������Ĺ���id, desc)
		 * 4����ѯ�Ľ����
		 */
		PageView<UploadFile> pv = new PageView<>(12, this.getPage());
		//2����ʼ����һ������ֵ��Ҳ���ǵõ���ǰҳ���ֵ��Ȼ��-1*ÿҳ����
		int firstindex = (pv.getCurrentpage() - 1) * pv.getMaxresult();
		//3����ʼ������˳��
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc"); 	//��id�ŵĵ�������
		//�õ������
		pv.setQueryResult(uploadFileService.getScrollData(UploadFile.class, firstindex, pv.getMaxresult(), orderby));
		request.setAttribute("pageView", pv);
		return "list";
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;
	}

	public String getFilepath()
	{
		return filepath;
	}

	public void setFilepath(String filepath)
	{
		this.filepath = filepath;
	}

	public int getPage()
	{
		return page < 1 ? 1 : page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public UploadFileService getUploadFileService()
	{
		return uploadFileService;
	}

	public void setUploadFileService(UploadFileService uploadFileService)
	{
		this.uploadFileService = uploadFileService;
	}
}
