/**
 * 功能：这个是实现产品类和web层的交互
 * 时间：2015年5月27日10:19:42
 * 文件：ProductAction.java
 * 作者：cutter_point
 */
package com.cutter_point.web.action.product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cutter_point.bean.PageView;
import com.cutter_point.bean.QueryResult;
import com.cutter_point.bean.product.ProductInfo;
import com.cutter_point.service.product.ProductInfoService;
import com.cutter_point.web.formbean.product.ProductForm;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class ProductAction extends ActionSupport implements ServletRequestAware, ModelDriven<ProductForm>
{
	private static final long serialVersionUID = -1041016333626692241L;
	@Resource
	private ProductInfoService productInfoService;	//业务注入
	//request
	private HttpServletRequest request;
	private String query;		//判断是从那个页面来的
	private ProductForm pf;	//一个表单bean
	
	@Override
	public String execute() throws Exception
	{
		PageView<ProductInfo> pageview = new PageView<ProductInfo>(12, pf.getPage());
		int firstindex = (pageview.getCurrentpage() - 1) * pageview.getMaxresult();	//得到从哪个开始索引的值
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("visible", "desc");	//倒序，也就是1排前面了，也就是在售的牌前面
		orderby.put("id", "asc");
		QueryResult<ProductInfo> qr = null;
		//判断来的页面是那个
		if("true".equals(this.getQuery()))
		{
			StringBuilder hsql = new StringBuilder("1 = 1");	//免得后面还要确定是不是要加“and”
			List<Object> params = new ArrayList<Object>();	//这个用来存放需要的排序方式
			//是从查询页面来的需求
			//如果是查询的话，那么我们判定一下name是不是为空的
			if(pf.getName() != null && !"".equals(pf.getName().trim())) //后面那个trim是去掉空格的作用
			{
				//模糊查询一下,代表按照名称进行查询
				hsql.append(" and o.name like ?");
				params.add("%" + pf.getName() + "%");		//给问号赋值
			}
			
			//还有产品的类别判断
			if(pf.getTypeid() != null && pf.getTypeid() > 0)
			{
				//组装sql语句
				hsql.append(" and o.typeid = ?");
				params.add(pf.getTypeid());
			}
			
			//低价查询区间
			if(pf.getStartbaseprice() != null && pf.getStartbaseprice() > 0)
			{
				//组装sql语句
				hsql.append(" and o.baseprice >= ?");
				params.add(pf.getStartbaseprice());
			}
			if(pf.getEndbaseprice() != null && pf.getEndbaseprice() > 0)
			{
				//组装sql语句
				hsql.append(" and o.baseprice <= ?");
				params.add(pf.getEndbaseprice());
			}
			
			//销售价(元)区间
			if(pf.getStartsellprice() != null && pf.getStartsellprice() > 0)
			{
				//组装sql语句
				hsql.append(" and o.sellprice >= ?");
				params.add(pf.getStartbaseprice());
			}
			if(pf.getEndsellprice() != null && pf.getEndsellprice() > 0)
			{
				//组装sql语句
				hsql.append(" and o.sellprice <= ?");
				params.add(pf.getEndsellprice());
			}
			
			//货号查询
			if(pf.getCode() != null && !"".equals(pf.getCode()))
			{
				hsql.append(" and o.code = ?");
				params.add(pf.getCode());
			}
			
			//按品牌查询
			if(pf.getBrandid() != null && !"".equals(pf.getBrandid()))
			{
				hsql.append(" and o.brandid = ?");
				params.add(pf.getBrandid());
			}
			
			//这个里面是有params的时候执行的方法
			qr = productInfoService.getScrollData(ProductInfo.class, firstindex, pageview.getMaxresult(), hsql.toString(), 
					params.toArray(), orderby);
		}
		else
		{
			qr = productInfoService.getScrollData(ProductInfo.class, firstindex, pageview.getMaxresult(), orderby);
		}
		
		 
		pageview.setQueryResult(qr);
		request.setAttribute("pageView", pageview);
		return "list";
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;
	}

	public ProductInfoService getProductInfoService()
	{
		return productInfoService;
	}

	public void setProductInfoService(ProductInfoService productInfoService)
	{
		this.productInfoService = productInfoService;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public ProductForm getModel() 
	{
		//从页面获取表单值
		if(pf == null)
			pf = new ProductForm();
		
		return pf;
	}
	
}
