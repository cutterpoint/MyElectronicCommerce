/**
 * ���ܣ�����ǲ�Ʒ�Ĺ�����
 * ʱ�䣺2015��5��27��17:17:45
 * �ļ���ProductManageAction.java
 * ���ߣ�cutter_point
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
	private ProductInfoService pis;	//ע���Ʒ������
	@Resource
	private BrandService bs;	//ע��Ʒ�Ʒ�����
	@Resource
	private ProductTypeService pts;	//ע���Ʒ���ͷ�����
	private HttpServletRequest request;
	private ProductForm pf;
	
	/**
	 * ���ò�Ʒ�Ƽ�
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String commend() throws Exception
	{
		pis.setCommendStatus(pf.getProductids(), true);	//�ϼܾ��ǿɼ�
		request.setAttribute("message", "��Ʒ�Ƽ��ɹ�");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list"));
		return "message";
	}
	
	/**
	 * ���ò�Ʒ���Ƽ�
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String uncommend() throws Exception
	{
		pis.setCommendStatus(pf.getProductids(), false);	//�¼ܾ��ǲ��ɼ�
		request.setAttribute("message", "��Ʒ���Ƽ��ɹ�");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list"));
		return "message";
	}
	
	/**
	 * ���ò�Ʒ�ϼ�
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String visible() throws Exception
	{
		pis.setVisibleStatus(pf.getProductids(), true);	//�ϼܾ��ǿɼ�
		request.setAttribute("message", "��Ʒ�ϼܳɹ�");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list"));
		return "message";
	}
	
	/**
	 * ���ò�Ʒ�¼�
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String disable() throws Exception
	{
		pis.setVisibleStatus(pf.getProductids(), false);	//�¼ܾ��ǲ��ɼ�
		request.setAttribute("message", "��Ʒ�¼ܳɹ�");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list"));
		return "message";
	}
	
	/**
	 * ��ʾ��Ʒ��ѯ����
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String queryUI() throws Exception
	{
		//���������ʾ����
		request.setAttribute("brands", bs.getScrollData(Brand.class).getResultList());	//��Ʒ����ʾ����
		return "query";
	}
	
	/**
	 * ��ʾ��Ʒ�޸Ľ���
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String editUI() throws Exception
	{
		//�����ݿ��л�ȡ��Ӧ�Ĳ�Ʒ��Ϣ,����id�Ż�ȡ��Ӧ����Ϣ
		ProductInfo product = pis.find(ProductInfo.class, pf.getProductid());
		//��ȡ��Ʒ����
		pf.setName(product.getName());
		//�������id
		pf.setTypeid(product.getType().getTypeid());
		//��(�ɹ�)��
		pf.setBaseprice(product.getBaseprice());
		//�г���
		pf.setMarketprice(product.getMarketprice());
		//���ۼ� 
		pf.setSellprice(product.getSellprice());
		//���� 
		pf.setCode(product.getCode());
		//Ʒ�� 
		if(product.getBrand() != null)
		{
			//�����Ӧ��Ʒ�Ʋ�Ϊ��
			pf.setBrandid(product.getBrand().getCode());
		}
		//�����Ա� 
		pf.setSex(product.getSexrequest().toString());
		//�ͺ� 
		pf.setModel(product.getModel());
		//���� ��
		pf.setWeight(product.getWeight());
		//����˵��
		pf.setBuyexplain(product.getBuyexplain());
		//��Ʒ���
		pf.setDescription(product.getDescription());
		//�Ա���ʵ���Ⱥ
		request.setAttribute("sexname", product.getSexrequest().getName());
		//�ɵ�ǰѡ���Ʒ�ƴ���ȥ
		request.setAttribute("brand", product.getBrand());
		//���������ʾ����
		request.setAttribute("typename", product.getType().getName());	//��������ʾ����
		request.setAttribute("brands", bs.getScrollData(Brand.class).getResultList());	//��Ʒ����ʾ����
		return "edit";
	}
	
	/**
	 * ��Ʒ�޸�
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String edit() throws Exception
	{
		ProductInfo product = pis.find(ProductInfo.class, pf.getProductid());	//��ѯ�������Ʒ
		product.setName(pf.getName());	//��ȡ��Ʒ����
		product.setBaseprice(pf.getBaseprice());		//���û����۸�
		product.setSellprice(pf.getSellprice());		//�������ۼ۸�
		product.setMarketprice(pf.getMarketprice());	//�г���
		if(pf.getBrandid() != null && !"".equals(pf.getBrandid().trim()))
		{
			product.setBrand(new Brand(pf.getBrandid()));		//����Ʒ�Ƶ�id
		}
		product.setBuyexplain(pf.getBuyexplain()); 	//����˵��
		product.setCode(pf.getCode()); 		//����
		product.setDescription(pf.getDescription()); 		//��Ʒ����
		product.setModel(pf.getModel()); //�����ͺ�
		product.setWeight(pf.getWeight()); //����
		product.setSexrequest(Sex.valueOf(pf.getSex())); //�Ա�Ҫ��
		product.setType(new ProductType(pf.getTypeid()));	//���ò�Ʒ����
		
		/***************************************************************************************************************
		 * 					��Ʒ��Ϣ���浽���ݿ�																			****
		 ***************************************************************************************************************/
		pis.update(product);	//���������Ʒ������������֮��hibernate��������Ʒ��id�Ÿ�ֵ��product
				
		
		/***************************************************************************************************************
		 * 								��ת�ɹ�֮��ҳ�洫ֵ														    	  **
		 ***************************************************************************************************************/
		request.setAttribute("message", "��Ʒ�޸ĳɹ�");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list"));
		return "message";
	}
	
	/**
	 * ��ʾ���ѡ�����
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String selectUI() throws Exception
	{
		String sql = "o.parentid is null and o.visible = 1";	//����Ƕ���Ŀ¼�Ļ�
		Object[] parems = new Object[0];	//�趨��Ӧ��id��
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
	 * ��ʾ��Ʒ��ӽ���
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String addUI() throws Exception
	{
		request.setAttribute("brands", bs.getScrollData(Brand.class).getResultList());
		return "add";
	}
	
	/**
	 * ��Ʒ���
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String add() throws Exception
	{
		ProductInfo product = new ProductInfo();	//�½�һ����Ʒ
		product.setName(pf.getName());	//��ȡ��Ʒ����
		product.setBaseprice(pf.getBaseprice());		//���û����۸�
		product.setSellprice(pf.getSellprice());		//�������ۼ۸�
		product.setMarketprice(pf.getMarketprice());	//�г���
		if(pf.getBrandid() != null && !"".equals(pf.getBrandid().trim()))
		{
			product.setBrand(new Brand(pf.getBrandid()));		//����Ʒ�Ƶ�id
		}
		product.setBuyexplain(pf.getBuyexplain()); 	//����˵��
		product.setCode(pf.getCode()); 		//����
		product.setDescription(pf.getDescription()); 		//��Ʒ����
		product.setModel(pf.getModel()); //�����ͺ�
		product.setWeight(pf.getWeight()); //����
		product.setSexrequest(Sex.valueOf(pf.getSex())); //�Ա�Ҫ��
		product.setType(new ProductType(pf.getTypeid()));	//���ò�Ʒ����
		
		//��������ͼƬ·��
		//���ϴ�֮ǰ�ж��ϴ�ͼƬ�����ǲ��Ƿ���Ҫ��
		if(!pf.validateFileType("logofile"))
		{
			request.setAttribute("message", "ͼƬ��ʽ����");
			request.setAttribute("urladdress", SiteUrl.readUrl("control.brand.list"));
			return "message";
		}
		//�õ�ͼƬ��׺
		//String ext = pf.getImagefileFileName().substring(pf.getImagefileFileName().lastIndexOf('.'));
		if(pf.getImagefile().length() > 409600)
		{
			request.setAttribute("message", "ͼƬ���ܴ���400k");
			request.setAttribute("urladdress", SiteUrl.readUrl("control.brand.list"));
			return "message";
		}
		
		/***************************************************************************************************************
		 * 					��Ʒ��Ϣ���浽���ݿ�																			****
		 ***************************************************************************************************************/
		product.addProductStyle(new ProductStyle(pf.getStylename(), pf.getImagefileFileName()));	//���������ļ���ŵ����ֺ�·��
		pis.save(product);	//���������Ʒ������������֮��hibernate��������Ʒ��id�Ÿ�ֵ��product
				
		/***************************************************************************************************************
		 * 								�ļ��ϴ�  													    				  **
		 ***************************************************************************************************************/
		//G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ �������realpath
		String realpath = ServletActionContext.getServletContext().getRealPath("/images");
		ProductForm.saveProductImageFile(pf.getImagefile(), pf.getImagefileFileName(), pf.getTypeid(), product.getId(), realpath);
		
		/***************************************************************************************************************
		 * 								��ת�ɹ�֮��ҳ�洫ֵ														    	  **
		 ***************************************************************************************************************/
		request.setAttribute("message", "��Ʒ��ӳɹ�");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list"));
		return "message";
	}
	
	//�����ļ���
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
				System.out.println("�ر��ļ�������ʧ��");
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
				System.out.println("�ر��ļ������ʧ��");
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
