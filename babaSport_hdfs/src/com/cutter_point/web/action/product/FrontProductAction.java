/**
 * ���ܣ������ǰ̨��Ʒչʾ�Ŀ�����
 * ʱ�䣺2015��6��3��09:46:50
 * �ļ���FrontProductAction.java
 * ���ߣ�cutter_point
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
@Scope("prototype")		//�����ʾÿ�η��ʵ�ʱ�򶼻���һ���µĶ��󴴽�
public class FrontProductAction extends ActionSupport implements ServletRequestAware, ModelDriven<FrontProductForm>
{
	private static final long serialVersionUID = -568904658537583015L;
	@Resource
	private ProductInfoService productInfoService;	//ҵ��ע��
	@Resource
	private ProductTypeService productTypeService;	//ҵ��ע��
	private HttpServletRequest request;
	private FrontProductForm pf;	//һ����bean
	
	@Override
	public String execute() throws Exception 
	{
		PageView<ProductInfo> pageview = new PageView<ProductInfo>(6, pf.getPage());
		pageview.setPagecode(20); 	//�����Ϊ������ҳ����
		int firstindex = (pageview.getCurrentpage() - 1) * pageview.getMaxresult();	//�õ����ĸ���ʼ������ֵ
		//��ʲô�����ѡ��
		LinkedHashMap<String, String> orderby = this.buildOrder(pf.getSort());	//��ȡ����Ӧ���������
		//ִ�е�SQL���
		StringBuilder sql = new StringBuilder(" o.visible=?");	//������װ��SQL���
		List<Object> params = new ArrayList<Object>();		//������?��ֵ�Ĳ����б�
		params.add(true);	//ֻ����ʾΪ��Ʒ�ϼܵĲŻᱻ��ʾ
		
		//�ж��ǲ�����typeid
		if(pf.getTypeid() != null)
		{
			sql.append(" and o.typeid=?");
			params.add(pf.getTypeid());	//��ѯ����Ӧ�����id����Ϊ����
		}
		
		//�������ж�һ��Ʒ�Ƶ�id�ǲ���Ϊ��
		if(pf.getBrandid() != null && !"".equals(pf.getBrandid().trim()))
		{
			//������װһ�����
			sql.append(" and o.brandid=?");
			params.add(pf.getBrandid());	//��ѯ����Ӧ��Ʒ��id����Ϊ����
		}
		
		//�ж�ѡ����Ա�Ҫ��
		if(pf.getSex() != null)
		{
			//��ȡ�Ա�����
			String sex = pf.getSex().trim();
			if("NONE".equalsIgnoreCase(sex) || "MAN".equalsIgnoreCase(sex) || "WOMEN".equalsIgnoreCase(sex))
			{
				sql.append(" and o.sexrequest=?");
				params.add(Sex.valueOf(sex).toString());	//����String��ȡ��Ӧ��ֵ
			}
		}
		
		//��ȡ���е����࣬�������������
		List<Integer> typeids = new ArrayList<Integer>();
		//���ȰѶ���Ҫ��ѯ�ĸ���Ž�ȥ
		//�ж��ǲ�����typeid
		if(pf.getTypeid() != null)
		{
			typeids.add(pf.getTypeid());
			this.getTypeids(typeids, new Integer[]{pf.getTypeid()});
			//�ɲ�ѯ�������������ͺŲ�ѯ����
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < typeids.size(); ++i)
			{
				sb.append("?,");
			}
			//ȥ������
			sb.deleteCharAt(sb.length() - 1);
			sql.append("and o.typeid in (" + sb.toString() + ")");
			params.addAll(typeids);  //�������
		}
		
		
		QueryResult<ProductInfo> qr = null;
		//������Ӧ������ȡ����Ӧ������
		qr = productInfoService.getScrollData(ProductInfo.class, firstindex, pageview.getMaxresult(), sql.toString(), params.toArray(), orderby);
		pageview.setQueryResult(qr);
		
		//��ʾ��Ӧ��Ʒ����ʽ��ʱ������Ҫ��ʾһ��
		for(ProductInfo product : pageview.getRecords())
		{
			Set<ProductStyle> styles = new HashSet<ProductStyle>();
			//Ȼ���ȡ�����Ĳ�Ʒ����ʽһ��һ����ȡ����
			for(ProductStyle style : product.getStyles())
			{
				//ȡ��һ�������ϼܵ���ʽ������ѭ��
				if(style.getVisible())
				{
					styles.add(style); //��Ҫ��ʾ����ʽ���һ������
					break;
				}
			}
			product.setStyles(styles); //ÿ����Ʒ����ʽ�����ó���
			//���ﴦ���Ʒ������,ȡ��html��ǩ
			product.setDescription(WebUtil.HtmltoText(product.getDescription()));
		}
		
		
		request.setAttribute("sort", pf.getSort());
		request.setAttribute("pageView", pageview);
		if(pf.getTypeid() != null)
		{
			Integer[] ids = new Integer[typeids.size()];	//������ת��Ϊ����
			for(int i = 0; i < typeids.size(); ++i)
			{
				ids[i] = typeids.get(i);
			}
			request.setAttribute("brands", productInfoService.getBrandsByProductTypeid(ids));	//��ѯ����Ӧ��Ʒ��
			ProductType type = productTypeService.find(ProductType.class, pf.getTypeid());	//���ҵ���ǰ�����
			//ҳ��ѭ����ʾ����·��
			List<ProductType> types = new ArrayList<ProductType>();
			types.add(type);	//����������ӵ����·����
			ProductType parenttype = type.getParent();	//��ȡ��������
			//�����еĸ��࣬����ĸ���ĸ��ࡣ����ȫ���ŵ�list��Ϊ·��
			while(parenttype  != null)
			{
				types.add(parenttype);
				parenttype = parenttype.getParent();
			}
			request.setAttribute("producttype", type);	//��ѯ����Ӧ�ĵ�ǰ���
			request.setAttribute("types", types);	//��ѯ����Ӧ������
		}
		return this.getView(pf.getShowstyle());
	}
	
	/**
	 * �ж���ʾ��������ͼ�İ滹��ͼƬ��
	 * @param showstyle
	 * @return
	 */
	public String getView(String showstyle)
	{
		if("imagetext".equalsIgnoreCase(showstyle))
		{
			//�����ͼ�İ�Ļ�
			return "list_imagetext";
		}
		else
		{
			return "list_image";
		}
	}
	
	/**
	 * ��ѯ�����е�����id(���������ȫ����ȡ)
	 * @param outtypeids	����ǲ�ѯ���������е��й�id��
	 * @param typeids	����id
	 * @return
	 */
	public void getTypeids(List<Integer> outtypeids, Integer[] typeids)
	{
		//���Ȳ������id����������id
		List<Integer> subtypeids = productTypeService.getSubTypeid(typeids);
//		//������Ҳ������ȥ
//		List<Integer> typeidss = Arrays.asList(typeids);
//		outtypeids.addAll(typeidss);	//���Լ�Ҳ�ӽ�ȥ
		//ֻҪ�����������id�Ų�Ϊ��˵��������ܻ������࣬һֱ�����һ��֮���ѯ������Ϊֹ
		if(subtypeids != null && subtypeids.size() > 0)
		{
			//�ɲ�ѯ����������ų��������д��
			outtypeids.addAll(subtypeids);
			//Ȼ��Ѳ�ѯ���������൱��������в�ѯ
			Integer[] ids = new Integer[subtypeids.size()];
			for(int i = 0; i < subtypeids.size(); ++i)
			{
				ids[i] = Integer.valueOf(subtypeids.get(i).toString());
			}
			getTypeids(outtypeids, ids);	//��Listת��Ϊ����
		}
	}
	
	/**
	 * ��װ�������
	 * @param orderfiled
	 * @return
	 */
	private LinkedHashMap<String, String> buildOrder(String orderfiled)
	{
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		//�ж��ַ���ǰ��� �ǲ������ַ���orderfiled��β.
		if("selldesc".equals(orderfiled))
		{
			orderby.put("sellcount", "desc");	//�����������������
		}
		else if("sellpricedesc".equals(orderfiled))	//����������ۼ۸�ĸߵ�������
		{
			orderby.put("sellprice", "desc");
		}
		else if("sellpriceasc".equals(orderfiled)) //������Լ۸�ĵ͸�������
		{
			orderby.put("sellprice", "asc");
		}
		else	//�Բ�Ʒ���ϼ�ʱ�����ж�
		{
			orderby.put("createdate", "desc");
		}
		return orderby;	//������Ӧ�������б�
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
		//��ҳ���ȡ��ֵ
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
