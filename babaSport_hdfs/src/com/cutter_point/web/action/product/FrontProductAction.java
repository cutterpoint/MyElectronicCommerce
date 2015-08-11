/**
 * 功能：这个是前台产品展示的控制器
 * 时间：2015年6月3日09:46:50
 * 文件：FrontProductAction.java
 * 作者：cutter_point
 */
package com.cutter_point.web.action.product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.bean.PageView;
import com.cutter_point.bean.QueryResult;
import com.cutter_point.bean.product.ProductInfo;
import com.cutter_point.bean.product.ProductStyle;
import com.cutter_point.bean.product.ProductType;
import com.cutter_point.bean.product.Sex;
import com.cutter_point.service.product.ProductInfoService;
import com.cutter_point.service.product.ProductTypeService;
import com.cutter_point.utils.WebUtil;
import com.cutter_point.web.formbean.product.FrontProductForm;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")		//这个表示每次访问的时候都会有一个新的对象创建
public class FrontProductAction extends ActionSupport implements ServletRequestAware, ModelDriven<FrontProductForm>
{
	private static final long serialVersionUID = -568904658537583015L;
	@Resource
	private ProductInfoService productInfoService;	//业务注入
	@Resource
	private ProductTypeService productTypeService;	//业务注入
	private HttpServletRequest request;
	private FrontProductForm pf;	//一个表单bean
	
	@Override
	public String execute() throws Exception 
	{
		PageView<ProductInfo> pageview = new PageView<ProductInfo>(6, pf.getPage());
		pageview.setPagecode(20); 	//这个是为了设置页码数
		int firstindex = (pageview.getCurrentpage() - 1) * pageview.getMaxresult();	//得到从哪个开始索引的值
		//按什么排序的选择
		LinkedHashMap<String, String> orderby = this.buildOrder(pf.getSort());	//获取到相应的排序规则
		//执行的SQL语句
		StringBuilder sql = new StringBuilder(" o.visible=?");	//用来组装的SQL语句
		List<Object> params = new ArrayList<Object>();		//用来给?赋值的参数列表
		params.add(true);	//只有显示为产品上架的才会被显示
		
		//判断是不是有typeid
		if(pf.getTypeid() != null)
		{
			sql.append(" and o.typeid=?");
			params.add(pf.getTypeid());	//查询出相应的类别id号作为条件
		}
		
		//这里先判断一下品牌的id是不是为空
		if(pf.getBrandid() != null && !"".equals(pf.getBrandid().trim()))
		{
			//这里组装一条语句
			sql.append(" and o.brandid=?");
			params.add(pf.getBrandid());	//查询出相应的品牌id号作为条件
		}
		
		//判断选择的性别要求
		if(pf.getSex() != null)
		{
			//获取性别属性
			String sex = pf.getSex().trim();
			if("NONE".equalsIgnoreCase(sex) || "MAN".equalsIgnoreCase(sex) || "WOMEN".equalsIgnoreCase(sex))
			{
				sql.append(" and o.sexrequest=?");
				params.add(Sex.valueOf(sex).toString());	//根据String获取相应的值
			}
		}
		
		//获取所有的子类，包括子类的子类
		List<Integer> typeids = new ArrayList<Integer>();
		//首先把顶级要查询的父类放进去
		//判断是不是有typeid
		if(pf.getTypeid() != null)
		{
			typeids.add(pf.getTypeid());
			this.getTypeids(typeids, new Integer[]{pf.getTypeid()});
			//吧查询出来的所有类型号查询出来
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < typeids.size(); ++i)
			{
				sb.append("?,");
			}
			//去掉逗号
			sb.deleteCharAt(sb.length() - 1);
			sql.append("and o.typeid in (" + sb.toString() + ")");
			params.addAll(typeids);  //传入参数
		}
		
		
		QueryResult<ProductInfo> qr = null;
		//根据相应的条件取得相应的数据
		qr = productInfoService.getScrollData(ProductInfo.class, firstindex, pageview.getMaxresult(), sql.toString(), params.toArray(), orderby);
		pageview.setQueryResult(qr);
		
		//显示相应产品的样式的时候，我们要显示一个
		for(ProductInfo product : pageview.getRecords())
		{
			Set<ProductStyle> styles = new HashSet<ProductStyle>();
			//然后把取出来的产品的样式一个一个地取出来
			for(ProductStyle style : product.getStyles())
			{
				//取出一个可以上架的样式就跳出循环
				if(style.getVisible())
				{
					styles.add(style); //给要显示的样式添加一个对象
					break;
				}
			}
			product.setStyles(styles); //每个产品的样式给设置出来
			//这里处理产品的描述,取出html标签
			product.setDescription(WebUtil.HtmltoText(product.getDescription()));
		}
		
		
		request.setAttribute("sort", pf.getSort());
		request.setAttribute("pageView", pageview);
		if(pf.getTypeid() != null)
		{
			Integer[] ids = new Integer[typeids.size()];	//吧类型转换为数组
			for(int i = 0; i < typeids.size(); ++i)
			{
				ids[i] = typeids.get(i);
			}
			request.setAttribute("brands", productInfoService.getBrandsByProductTypeid(ids));	//查询出相应的品牌
			ProductType type = productTypeService.find(ProductType.class, pf.getTypeid());	//查找到当前的类别
			//页面循环显示导航路径
			List<ProductType> types = new ArrayList<ProductType>();
			types.add(type);	//吧这个类别添加到这个路径中
			ProductType parenttype = type.getParent();	//获取父类类型
			//吧所有的父类，父类的父类的父类。。。全部放到list作为路径
			while(parenttype  != null)
			{
				types.add(parenttype);
				parenttype = parenttype.getParent();
			}
			request.setAttribute("producttype", type);	//查询出相应的当前类别
			request.setAttribute("types", types);	//查询出相应的序列
		}
		return this.getView(pf.getShowstyle());
	}
	
	/**
	 * 判定显示的类型是图文版还是图片版
	 * @param showstyle
	 * @return
	 */
	public String getView(String showstyle)
	{
		if("imagetext".equalsIgnoreCase(showstyle))
		{
			//如果是图文版的话
			return "list_imagetext";
		}
		else
		{
			return "list_image";
		}
	}
	
	/**
	 * 查询出所有的子类id(子类的子类全部获取)
	 * @param outtypeids	这个是查询出来的所有的有关id号
	 * @param typeids	父类id
	 * @return
	 */
	public void getTypeids(List<Integer> outtypeids, Integer[] typeids)
	{
		//首先查出父类id的所有子类id
		List<Integer> subtypeids = productTypeService.getSubTypeid(typeids);
//		//吧自身也包含进去
//		List<Integer> typeidss = Arrays.asList(typeids);
//		outtypeids.addAll(typeidss);	//吧自己也加进去
		//只要查出来的子类id号不为空说明子类可能还有子类，一直把最后一层之类查询不出来为止
		if(subtypeids != null && subtypeids.size() > 0)
		{
			//吧查询出来的子类放出到参数中存放
			outtypeids.addAll(subtypeids);
			//然后把查询出来的子类当做父类进行查询
			Integer[] ids = new Integer[subtypeids.size()];
			for(int i = 0; i < subtypeids.size(); ++i)
			{
				ids[i] = Integer.valueOf(subtypeids.get(i).toString());
			}
			getTypeids(outtypeids, ids);	//吧List转化为数组
		}
	}
	
	/**
	 * 组装排序规则
	 * @param orderfiled
	 * @return
	 */
	private LinkedHashMap<String, String> buildOrder(String orderfiled)
	{
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		//判断字符串前面的 是不是以字符串orderfiled结尾.
		if("selldesc".equals(orderfiled))
		{
			orderby.put("sellcount", "desc");	//这个是以销量来排序
		}
		else if("sellpricedesc".equals(orderfiled))	//这个是以销售价格的高低来排序
		{
			orderby.put("sellprice", "desc");
		}
		else if("sellpriceasc".equals(orderfiled)) //这个是以价格的低高来排序
		{
			orderby.put("sellprice", "asc");
		}
		else	//以产品的上架时间来判断
		{
			orderby.put("createdate", "desc");
		}
		return orderby;	//返回相应的排序列表
	}

	
	public ProductInfoService getProductInfoService() {
		return productInfoService;
	}


	public void setProductInfoService(ProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}


	public FrontProductForm getPf() {
		return pf;
	}


	public void setPf(FrontProductForm pf) {
		this.pf = pf;
	}


	@Override
	public FrontProductForm getModel() 
	{
		//从页面获取表单值
		if(pf == null)
			pf = new FrontProductForm();
		
		return pf;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) 
	{
		this.request = arg0;
	}
	
}
