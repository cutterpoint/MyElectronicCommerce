/**
 * 功能：这个是文件显示的控制器
 * 时间：2015年5月26日08:36:01
 * 文件：UploadFileAction.java
 * 作者：cutter_point
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
	//业务处理spring注入
	@Resource
	private UploadFileService uploadFileService;
	//通过实现接口获取相应的request
	private HttpServletRequest request;
	private String filepath;	//文件保存的路径
	private int page;		//当前的页面值

	@Override
	public String execute() throws Exception
	{
		/*
		 * 1、分页的pageView的初始化
		 * 2、初始化每页的第一个索引的初始值
		 * 3、排序的规则（id, desc)
		 * 4、查询的结果集
		 */
		PageView<UploadFile> pv = new PageView<>(12, this.getPage());
		//2、初始化第一个索引值，也就是得到当前页面的值，然后-1*每页长度
		int firstindex = (pv.getCurrentpage() - 1) * pv.getMaxresult();
		//3、初始化排序顺序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc"); 	//按id号的倒序排序
		//得到结果集
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
