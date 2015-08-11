/**
 * 功能：这个是产品的管理动作
 * 时间：2015年5月27日17:17:45
 * 文件：ProductManageAction.java
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

import com.cutter_point.bean.QueryResult;
import com.cutter_point.bean.product.Brand;
import com.cutter_point.bean.product.ProductInfo;
import com.cutter_point.bean.product.ProductStyle;
import com.cutter_point.bean.product.ProductType;
import com.cutter_point.bean.product.Sex;
import com.cutter_point.service.product.BrandService;
import com.cutter_point.service.product.ProductInfoService;
import com.cutter_point.service.product.ProductTypeService;
import com.cutter_point.utils.SiteUrl;
import com.cutter_point.web.formbean.product.ProductForm;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class ProductManageAction extends ActionSupport implements ServletRequestAware, ModelDriven<ProductForm>
{
	private static final long serialVersionUID = 6647552432813142686L;
	@Resource
	private ProductInfoService pis;	//注入产品服务类
	@Resource
	private BrandService bs;	//注入品牌服务类
	@Resource
	private ProductTypeService pts;	//注入产品类型服务类
	private HttpServletRequest request;
	private ProductForm pf;
	
	/**
	 * 设置产品推荐
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String commend() throws Exception
	{
		pis.setCommendStatus(pf.getProductids(), true);	//上架就是可见
		request.setAttribute("message", "产品推荐成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list"));
		return "message";
	}
	
	/**
	 * 设置产品不推荐
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String uncommend() throws Exception
	{
		pis.setCommendStatus(pf.getProductids(), false);	//下架就是不可见
		request.setAttribute("message", "产品不推荐成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list"));
		return "message";
	}
	
	/**
	 * 设置产品上架
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String visible() throws Exception
	{
		pis.setVisibleStatus(pf.getProductids(), true);	//上架就是可见
		request.setAttribute("message", "产品上架成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list"));
		return "message";
	}
	
	/**
	 * 设置产品下架
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String disable() throws Exception
	{
		pis.setVisibleStatus(pf.getProductids(), false);	//下架就是不可见
		request.setAttribute("message", "产品下架成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list"));
		return "message";
	}
	
	/**
	 * 显示产品查询界面
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String queryUI() throws Exception
	{
		//类别名称显示出来
		request.setAttribute("brands", bs.getScrollData(Brand.class).getResultList());	//吧品牌显示出来
		return "query";
	}
	
	/**
	 * 显示产品修改界面
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String editUI() throws Exception
	{
		//从数据库中获取相应的产品信息,根据id号获取相应的信息
		ProductInfo product = pis.find(ProductInfo.class, pf.getProductid());
		//获取产品名称
		pf.setName(product.getName());
		//设置类别id
		pf.setTypeid(product.getType().getTypeid());
		//底(采购)价
		pf.setBaseprice(product.getBaseprice());
		//市场价
		pf.setMarketprice(product.getMarketprice());
		//销售价 
		pf.setSellprice(product.getSellprice());
		//货号 
		pf.setCode(product.getCode());
		//品牌 
		if(product.getBrand() != null)
		{
			//如果对应的品牌不为空
			pf.setBrandid(product.getBrand().getCode());
		}
		//适用性别 
		pf.setSex(product.getSexrequest().toString());
		//型号 
		pf.setModel(product.getModel());
		//重量 ：
		pf.setWeight(product.getWeight());
		//购买说明
		pf.setBuyexplain(product.getBuyexplain());
		//产品简介
		pf.setDescription(product.getDescription());
		//性别合适的人群
		request.setAttribute("sexname", product.getSexrequest().getName());
		//吧当前选择的品牌传出去
		request.setAttribute("brand", product.getBrand());
		//类别名称显示出来
		request.setAttribute("typename", product.getType().getName());	//吧类型显示出来
		request.setAttribute("brands", bs.getScrollData(Brand.class).getResultList());	//吧品牌显示出来
		return "edit";
	}
	
	/**
	 * 产品修改
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String edit() throws Exception
	{
		ProductInfo product = pis.find(ProductInfo.class, pf.getProductid());	//查询出这个产品
		product.setName(pf.getName());	//获取产品名称
		product.setBaseprice(pf.getBaseprice());		//设置基础价格
		product.setSellprice(pf.getSellprice());		//设置销售价格
		product.setMarketprice(pf.getMarketprice());	//市场价
		if(pf.getBrandid() != null && !"".equals(pf.getBrandid().trim()))
		{
			product.setBrand(new Brand(pf.getBrandid()));		//设置品牌的id
		}
		product.setBuyexplain(pf.getBuyexplain()); 	//购买说明
		product.setCode(pf.getCode()); 		//货号
		product.setDescription(pf.getDescription()); 		//产品描述
		product.setModel(pf.getModel()); //设置型号
		product.setWeight(pf.getWeight()); //重量
		product.setSexrequest(Sex.valueOf(pf.getSex())); //性别要求
		product.setType(new ProductType(pf.getTypeid()));	//设置产品类型
		
		/***************************************************************************************************************
		 * 					产品信息保存到数据库																			****
		 ***************************************************************************************************************/
		pis.update(product);	//保存这个产品，当保存完了之后hibernate会吧这个产品的id号赋值给product
				
		
		/***************************************************************************************************************
		 * 								跳转成功之后页面传值														    	  **
		 ***************************************************************************************************************/
		request.setAttribute("message", "产品修改成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list"));
		return "message";
	}
	
	/**
	 * 显示类别选择界面
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String selectUI() throws Exception
	{
		String sql = "o.parentid is null and o.visible = 1";	//如果是顶级目录的话
		Object[] parems = new Object[0];	//设定相应的id号
		if(pf.getTypeid() != null && pf.getTypeid() > 0)
		{
			sql = " o.parentid = ? ";
			parems = new Object[]{pf.getTypeid()};
		}
		QueryResult<ProductType> qr = pts.getScrollData(ProductType.class, -1, -1, sql, parems);
		request.setAttribute("types", qr.getResultList());
		return "typeselect";
	}
	
	/**
	 * 显示产品添加界面
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String addUI() throws Exception
	{
		request.setAttribute("brands", bs.getScrollData(Brand.class).getResultList());
		return "add";
	}
	
	/**
	 * 产品添加
	 * @return String struts2的返回跳转result
	 * @throws Exception
	 */
	public String add() throws Exception
	{
		ProductInfo product = new ProductInfo();	//新建一个产品
		product.setName(pf.getName());	//获取产品名称
		product.setBaseprice(pf.getBaseprice());		//设置基础价格
		product.setSellprice(pf.getSellprice());		//设置销售价格
		product.setMarketprice(pf.getMarketprice());	//市场价
		if(pf.getBrandid() != null && !"".equals(pf.getBrandid().trim()))
		{
			product.setBrand(new Brand(pf.getBrandid()));		//设置品牌的id
		}
		product.setBuyexplain(pf.getBuyexplain()); 	//购买说明
		product.setCode(pf.getCode()); 		//货号
		product.setDescription(pf.getDescription()); 		//产品描述
		product.setModel(pf.getModel()); //设置型号
		product.setWeight(pf.getWeight()); //重量
		product.setSexrequest(Sex.valueOf(pf.getSex())); //性别要求
		product.setType(new ProductType(pf.getTypeid()));	//设置产品类型
		
		//用来保存图片路径
		//再上传之前判断上传图片类型是不是符合要求
		if(!pf.validateFileType("logofile"))
		{
			request.setAttribute("message", "图片格式不对");
			request.setAttribute("urladdress", SiteUrl.readUrl("control.brand.list"));
			return "message";
		}
		//得到图片后缀
		//String ext = pf.getImagefileFileName().substring(pf.getImagefileFileName().lastIndexOf('.'));
		if(pf.getImagefile().length() > 409600)
		{
			request.setAttribute("message", "图片不能大于400k");
			request.setAttribute("urladdress", SiteUrl.readUrl("control.brand.list"));
			return "message";
		}
		
		/***************************************************************************************************************
		 * 					产品信息保存到数据库																			****
		 ***************************************************************************************************************/
		product.addProductStyle(new ProductStyle(pf.getStylename(), pf.getImagefileFileName()));	//这个里面放文件存放的名字和路径
		pis.save(product);	//保存这个产品，当保存完了之后hibernate会吧这个产品的id号赋值给product
				
		/***************************************************************************************************************
		 * 								文件上传  													    				  **
		 ***************************************************************************************************************/
		//G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ 这个就是realpath
		String realpath = ServletActionContext.getServletContext().getRealPath("/images");
		ProductForm.saveProductImageFile(pf.getImagefile(), pf.getImagefileFileName(), pf.getTypeid(), product.getId(), realpath);
		
		/***************************************************************************************************************
		 * 								跳转成功之后页面传值														    	  **
		 ***************************************************************************************************************/
		request.setAttribute("message", "产品添加成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list"));
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

	public ProductInfoService getPis()
	{
		return pis;
	}

	public void setPis(ProductInfoService pis)
	{
		this.pis = pis;
	}

	public BrandService getBs()
	{
		return bs;
	}

	public void setBs(BrandService bs)
	{
		this.bs = bs;
	}

	public ProductForm getPf()
	{
		return pf;
	}

	public void setPf(ProductForm pf)
	{
		this.pf = pf;
	}

	@Override
	public ProductForm getModel() 
	{
		if(pf == null)
			pf = new ProductForm();
		
		return pf;
	}
}
