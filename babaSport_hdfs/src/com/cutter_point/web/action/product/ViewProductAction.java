/**
 * 功能：这个是专门为了显示产品的控制器
 * 时间：2015年6月4日19:20:20
 * 文件：ViewProductAction.java
 * 作者：cutter_point
 */
package com.cutter_point.web.action.product;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.bean.product.ProductInfo;
import com.cutter_point.bean.product.ProductType;
import com.cutter_point.service.product.ProductInfoService;
import com.cutter_point.utils.WebUtil;
import com.cutter_point.web.formbean.product.FrontProductForm;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class ViewProductAction extends ActionSupport implements	ServletRequestAware, ModelDriven<FrontProductForm>
{
	private static final long serialVersionUID = -886191288027224488L;
	@Resource
	private ProductInfoService productInfoService;	//业务注入
	private HttpServletRequest request;
	private FrontProductForm pf;	//一个表单bean

	
	@Override
	public String execute() throws Exception 
	{
		//通过页面的传值得到商品的id
		ProductInfo product = productInfoService.find(ProductInfo.class, pf.getProductid());
		HttpServletResponse response = ServletActionContext.getResponse();
		if(product == null)
		{
			request.setAttribute("message", "获取不到产品");
			request.setAttribute("urladdress", "/");
			return "message";
		}
		//获取到cookie,存放30天，创建一个cookie
		WebUtil.addCookie(response, "productViewHistory", this.buildViewHistory(pf.getProductid()), 30*24*60*60);
		List<ProductType> types = new ArrayList<ProductType>();
		ProductType parent = product.getType();	//获取父类产品类别
		while(parent != null)
		{
			//吧类别
			types.add(parent);	//吧类别添加到一起
			parent = parent.getParent();	//得到父类,然后在吧父类放到类型里面去
		}
		request.setAttribute("types", types);
		request.setAttribute("product", product);
		return "product";
	}
	
	/**
	 * 这个方法是为了获取到相应cookie的值
	 * @param currentProductId
	 * @return
	 */
	public String buildViewHistory(Integer currentProductId)
	{
		//获取到相应的cookie的值
		//1.如果当前浏览的id意见在浏览历史里面了，我们把它要移动到最前面
		//2.如果浏览历史达到了10个产品，我们需要把最先被浏览的产品id号删除掉
		String cookieValue = WebUtil.getCookieByName(request, "productViewHistory");
		LinkedList<Integer> productids = new LinkedList<Integer>();
		//这个里面是为了处理以前浏览过的东西的元素数据
		if(cookieValue != null && !"".equals(cookieValue.trim()))
		{
			String[] ids = cookieValue.split("-");	//分割cookie的值
			//吧分割之后的数据存放到一个list里面
			//吧数据一个一个地加进去
			for(String id : ids)
			{
				//吧数据添加到list里面，这个里面存放所有的浏览历史记录
				productids.offer(new Integer(id.trim()));
			}
			if(productids.contains(currentProductId)) //如果list里面存在当前的产品id号的话
			{
				//我们先把他删除掉
				productids.remove(currentProductId);
			}
			//如果历史记录以及超过了10个，那么就把以前的那个去掉
			if(productids.size() >= 10)
			{
				productids.poll();
			}
		}
		//然后再加进来
		productids.add(currentProductId);
		//吧数组里面的额数据转换为cookie里面的数据类型
		StringBuilder outcookie = new StringBuilder();
		for(Integer id : productids)
		{
			outcookie.append(id).append("-");
		}
		outcookie.deleteCharAt(outcookie.length() - 1);
		return outcookie.toString();
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
		if(pf == null)
			pf = new FrontProductForm();
		
		return pf;
	}

	public FrontProductForm getPf() {
		return pf;
	}

	public void setPf(FrontProductForm pf) {
		this.pf = pf;
	}
}
