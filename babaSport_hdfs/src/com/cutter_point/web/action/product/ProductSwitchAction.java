/**
 * 功能：这个是前台显示推荐，浏览==，用ajax的显示
 * 时间：2015年6月4日15:25:50
 * 文件：ProductSwitchAction.java
 * 作者：cutter_point
 */
package com.cutter_point.web.action.product;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.service.product.ProductInfoService;
import com.cutter_point.utils.WebUtil;
import com.cutter_point.web.formbean.product.FrontProductForm;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")		//这个表示每次访问的时候都会有一个新的对象创建
public class ProductSwitchAction extends ActionSupport implements ServletRequestAware, ModelDriven<FrontProductForm>
{

	private static final long serialVersionUID = 4327341396079235931L;
	@Resource
	private ProductInfoService productInfoService;	//业务注入
	private HttpServletRequest request;
	private FrontProductForm formbean;
	
	/**
	 * 显示产品大图
	 * @return
	 * @throws Exception
	 */
	public String showimage() throws Exception
	{
		return "showimage";
	}
	
	/**
	 * 获取10个最畅销的产品
	 * @return
	 * @throws Exception
	 */
	public String topsell() throws Exception
	{
		request.setAttribute("topsellproducts", productInfoService.getTopSell(formbean.getTypeid(), 10));
		return "topsell";
	}
	
	/**
	 * 获取10个用户浏览过的商品
	 * @return
	 * @throws Exception
	 */
	public String viewHistory() throws Exception
	{
		//首先我么要获取cookie的值
		String cookieValue = WebUtil.getCookieByName(request, "productViewHistory");
		//cookie的存放格式12-45-67-89
		if(cookieValue != null && !"".equals(cookieValue))
		{
			String[] ids = cookieValue.split("-"); //吧字符用-分割开
			Integer[] productids = new Integer[ids.length]; //用一个等长的数组存放
			StringBuilder sql = new StringBuilder();	//用来组装相应的sql语句
			for(int i = 0; i < ids.length; ++i)
			{
				//循环吧商品的id存放进去
				productids[i] = new Integer(ids[i].trim()); //去除相应的空格，得到相应值
				sql.append("?,");
			}
			//去掉最后一个逗号
			sql.deleteCharAt(sql.length() - 1);
			//获取相应的产品信息，从0开始获取10个
			request.setAttribute("viewHistory", productInfoService.getViewHistory(productids, 10));
		}
		return "viewHistory";
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) 
	{
		this.request = arg0;
	}

	@Override
	public FrontProductForm getModel() 
	{
		//从页面获取表单值
		if(formbean == null)
			formbean = new FrontProductForm();
		
		return formbean;
	}

}
