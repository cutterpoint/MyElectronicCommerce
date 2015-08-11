/**
 * ���ܣ������ʵ�ֲ�Ʒ���web��Ľ���
 * ʱ�䣺2015��5��27��10:19:42
 * �ļ���ProductAction.java
 * ���ߣ�cutter_point
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
	private ProductInfoService productInfoService;	//ҵ��ע��
	//request
	private HttpServletRequest request;
	private String query;		//�ж��Ǵ��Ǹ�ҳ������
	private ProductForm pf;	//һ����bean
	
	@Override
	public String execute() throws Exception
	{
		PageView<ProductInfo> pageview = new PageView<ProductInfo>(12, pf.getPage());
		int firstindex = (pageview.getCurrentpage() - 1) * pageview.getMaxresult();	//�õ����ĸ���ʼ������ֵ
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("visible", "desc");	//����Ҳ����1��ǰ���ˣ�Ҳ�������۵���ǰ��
		orderby.put("id", "asc");
		QueryResult<ProductInfo> qr = null;
		//�ж�����ҳ�����Ǹ�
		if("true".equals(this.getQuery()))
		{
			StringBuilder hsql = new StringBuilder("1 = 1");	//��ú��滹Ҫȷ���ǲ���Ҫ�ӡ�and��
			List<Object> params = new ArrayList<Object>();	//������������Ҫ������ʽ
			//�ǴӲ�ѯҳ����������
			//����ǲ�ѯ�Ļ�����ô�����ж�һ��name�ǲ���Ϊ�յ�
			if(pf.getName() != null && !"".equals(pf.getName().trim())) //�����Ǹ�trim��ȥ���ո������
			{
				//ģ����ѯһ��,���������ƽ��в�ѯ
				hsql.append(" and o.name like ?");
				params.add("%" + pf.getName() + "%");		//���ʺŸ�ֵ
			}
			
			//���в�Ʒ������ж�
			if(pf.getTypeid() != null && pf.getTypeid() > 0)
			{
				//��װsql���
				hsql.append(" and o.typeid = ?");
				params.add(pf.getTypeid());
			}
			
			//�ͼ۲�ѯ����
			if(pf.getStartbaseprice() != null && pf.getStartbaseprice() > 0)
			{
				//��װsql���
				hsql.append(" and o.baseprice >= ?");
				params.add(pf.getStartbaseprice());
			}
			if(pf.getEndbaseprice() != null && pf.getEndbaseprice() > 0)
			{
				//��װsql���
				hsql.append(" and o.baseprice <= ?");
				params.add(pf.getEndbaseprice());
			}
			
			//���ۼ�(Ԫ)����
			if(pf.getStartsellprice() != null && pf.getStartsellprice() > 0)
			{
				//��װsql���
				hsql.append(" and o.sellprice >= ?");
				params.add(pf.getStartbaseprice());
			}
			if(pf.getEndsellprice() != null && pf.getEndsellprice() > 0)
			{
				//��װsql���
				hsql.append(" and o.sellprice <= ?");
				params.add(pf.getEndsellprice());
			}
			
			//���Ų�ѯ
			if(pf.getCode() != null && !"".equals(pf.getCode()))
			{
				hsql.append(" and o.code = ?");
				params.add(pf.getCode());
			}
			
			//��Ʒ�Ʋ�ѯ
			if(pf.getBrandid() != null && !"".equals(pf.getBrandid()))
			{
				hsql.append(" and o.brandid = ?");
				params.add(pf.getBrandid());
			}
			
			//�����������params��ʱ��ִ�еķ���
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
		//��ҳ���ȡ��ֵ
		if(pf == null)
			pf = new ProductForm();
		
		return pf;
	}
	
}
