/**
 * 功能：这个是产品样式的控制器
 * 时间：2015年6月1日15:45:22
 * 文件：ProductStyleManageAction.java
 * 作者：cutter_point
 */
package com.cutter_point.web.action.product;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.bean.product.ProductInfo;
import com.cutter_point.bean.product.ProductStyle;
import com.cutter_point.service.product.ProductInfoService;
import com.cutter_point.service.product.ProductStyleService;
import com.cutter_point.utils.SiteUrl;
import com.cutter_point.web.formbean.product.ProductForm;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class ProductStyleManageAction extends ActionSupport implements	ServletRequestAware, ModelDriven<ProductForm>
{
	private static final long serialVersionUID = 1378025018750845909L;
	@Resource
	private ProductStyleService productStyleService;
	@Resource
	private ProductInfoService productInfoService;
	private HttpServletRequest request;
	private ProductForm pf;
	
	/**
	 * 设置产品上架
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String visible() throws Exception
	{
		productStyleService.setVisibleStatus(pf.getStylesids(), true);	//上架就是可见
		request.setAttribute("message", "产品上架成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.productstyle.list") + "?productid=" + pf.getProductid());
		return "message";
	}
	
	/**
	 * 设置产品下架
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String disable() throws Exception
	{
		productStyleService.setVisibleStatus(pf.getStylesids(), false);	//下架就是不可见
		request.setAttribute("message", "产品下架成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.productstyle.list") + "?productid=" + pf.getProductid());
		return "message";
	}
	
	/**
	 * 显示产品修改界面
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String editUI() throws Exception
	{
		//根据样式id号获取相应的id
		ProductStyle productStyle = productStyleService.find(ProductStyle.class, pf.getProductstyleid());
		pf.setStylename(productStyle.getName()); 	//获取名字
		//显示的路径
		String imageshowpath = "../" + productStyle.getImageFullPath();
		request.setAttribute("imageshowpath", imageshowpath);
		return "edit";
	}
	
	/**
	 * 产品修改
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String edit() throws Exception
	{
		ProductStyle productStyle = productStyleService.find(ProductStyle.class, pf.getProductstyleid());	//查询出这个产品
		productStyle.setName(pf.getStylename());  //设置样式的名称
		productStyle.setImagename(pf.getImagefileFileName()); //设置图片名称
		//还有就是样式里面没有上传文件的话那么就不用处理了，只有文件上传了才会被处理
		if(pf.getImagefile() != null && pf.getImagefile().length() > 0)
		{
			/***************************************************************************************************************
			 * 					吧新的文件上传到相应的文件夹																		****
			 ***************************************************************************************************************/
			//首先验证图片的格式是不是正确的
			if(!pf.validateFileType(pf.getImagefileFileName()))
			{
				//如果图片格式不正确
				request.setAttribute("message", "产品修改成功");
				request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list") + "?productid=" + productStyle.getProduct().getId());
				return "message";
			}
			//验证图片文件的大小
			if(pf.getImagefile().length() > 409600)
			{
				request.setAttribute("message", "图片不能大于400k");
				request.setAttribute("urladdress", SiteUrl.readUrl("control.productstyle.list") + "?productid=" + productStyle.getProduct().getId());
				return "message";
			}
			//上传到现有的路径
			//G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ 这个就是realpath
			String realpath = ServletActionContext.getServletContext().getRealPath("/images");
			ProductForm.saveProductImageFile(pf.getImagefile(), pf.getImagefileFileName(), productStyle.getProduct().getType().getTypeid(), productStyle.getProduct().getId(), realpath);
		}
		
		/***************************************************************************************************************
		 * 					产品信息保存到数据库																			****
		 ***************************************************************************************************************/
		productStyleService.update(productStyle);	//保存这个产品，当保存完了之后hibernate会吧这个产品的id号赋值给product
				
		
		/***************************************************************************************************************
		 * 								跳转成功之后页面传值														    	  **
		 ***************************************************************************************************************/
		request.setAttribute("message", "产品修改成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list") + "?productid=" + productStyle.getProduct().getId());
		return "message";
	}
	
	/**
	 * 显示样式添加界面
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String addUI() throws Exception
	{
		return "add";
	}
	
	/**
	 * 显示样式添加处理
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String add() throws Exception
	{
		//用来保存图片路径
		//再上传之前判断上传图片类型是不是符合要求
		if(!pf.validateFileType(pf.getImagefileFileName()))
		{
			request.setAttribute("message", "图片格式不对");
			request.setAttribute("urladdress", SiteUrl.readUrl("control.productstyle.list"));
			return "message";
		}
		//得到图片后缀
		//String ext = pf.getImagefileFileName().substring(pf.getImagefileFileName().lastIndexOf('.'));
		if(pf.getImagefile().length() > 409600)
		{
			request.setAttribute("message", "图片不能大于400k");
			request.setAttribute("urladdress", SiteUrl.readUrl("control.productstyle.list"));
			return "message";
		}
		ProductInfo productInfo = new ProductInfo();	//查找到的产品
		//根据相应的id号得到相应的产品
		productInfo = productInfoService.find(ProductInfo.class, pf.getProductid());
		/***************************************************************************************************************
		 * 					产品信息保存到数据库																			****
		 ***************************************************************************************************************/
		//我们要保存的产品样式
		ProductStyle productStyle = new ProductStyle(pf.getStylename(), pf.getImagefileFileName());	//上传的文件信息
		productStyle.setProduct(productInfo);
		productStyleService.save(productStyle);
		
		/***************************************************************************************************************
		 * 								文件上传  													    				  **
		 ***************************************************************************************************************/
		//构造产品保存的目录
		//G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ 这个就是realpath
		String realpath = ServletActionContext.getServletContext().getRealPath("/images");
		ProductForm.saveProductImageFile(pf.getImagefile(), pf.getImagefileFileName(), productInfo.getType().getTypeid(), productInfo.getId(), realpath);
		
		/***************************************************************************************************************
		 * 								跳转成功之后页面传值														    	  **
		 ***************************************************************************************************************/
		request.setAttribute("message", "产品添加成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.productstyle.list") + "?productid=" + productInfo.getId());
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
	
	@Override
	public void setServletRequest(HttpServletRequest arg0) 
	{
		this.request = arg0;
	}

	@Override
	public ProductForm getModel() 
	{
		if(pf == null)
			pf = new ProductForm();
		return pf;
	}

	public ProductForm getPf() {
		return pf;
	}

	public void setPf(ProductForm pf) {
		this.pf = pf;
	}

}
