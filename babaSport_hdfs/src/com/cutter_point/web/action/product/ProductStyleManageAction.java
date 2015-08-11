/**
 * ���ܣ�����ǲ�Ʒ��ʽ�Ŀ�����
 * ʱ�䣺2015��6��1��15:45:22
 * �ļ���ProductStyleManageAction.java
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
	 * ���ò�Ʒ�ϼ�
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String visible() throws Exception
	{
		productStyleService.setVisibleStatus(pf.getStylesids(), true);	//�ϼܾ��ǿɼ�
		request.setAttribute("message", "��Ʒ�ϼܳɹ�");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.productstyle.list") + "?productid=" + pf.getProductid());
		return "message";
	}
	
	/**
	 * ���ò�Ʒ�¼�
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String disable() throws Exception
	{
		productStyleService.setVisibleStatus(pf.getStylesids(), false);	//�¼ܾ��ǲ��ɼ�
		request.setAttribute("message", "��Ʒ�¼ܳɹ�");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.productstyle.list") + "?productid=" + pf.getProductid());
		return "message";
	}
	
	/**
	 * ��ʾ��Ʒ�޸Ľ���
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String editUI() throws Exception
	{
		//������ʽid�Ż�ȡ��Ӧ��id
		ProductStyle productStyle = productStyleService.find(ProductStyle.class, pf.getProductstyleid());
		pf.setStylename(productStyle.getName()); 	//��ȡ����
		//��ʾ��·��
		String imageshowpath = "../" + productStyle.getImageFullPath();
		request.setAttribute("imageshowpath", imageshowpath);
		return "edit";
	}
	
	/**
	 * ��Ʒ�޸�
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String edit() throws Exception
	{
		ProductStyle productStyle = productStyleService.find(ProductStyle.class, pf.getProductstyleid());	//��ѯ�������Ʒ
		productStyle.setName(pf.getStylename());  //������ʽ������
		productStyle.setImagename(pf.getImagefileFileName()); //����ͼƬ����
		//���о�����ʽ����û���ϴ��ļ��Ļ���ô�Ͳ��ô����ˣ�ֻ���ļ��ϴ��˲Żᱻ����
		if(pf.getImagefile() != null && pf.getImagefile().length() > 0)
		{
			/***************************************************************************************************************
			 * 					���µ��ļ��ϴ�����Ӧ���ļ���																		****
			 ***************************************************************************************************************/
			//������֤ͼƬ�ĸ�ʽ�ǲ�����ȷ��
			if(!pf.validateFileType(pf.getImagefileFileName()))
			{
				//���ͼƬ��ʽ����ȷ
				request.setAttribute("message", "��Ʒ�޸ĳɹ�");
				request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list") + "?productid=" + productStyle.getProduct().getId());
				return "message";
			}
			//��֤ͼƬ�ļ��Ĵ�С
			if(pf.getImagefile().length() > 409600)
			{
				request.setAttribute("message", "ͼƬ���ܴ���400k");
				request.setAttribute("urladdress", SiteUrl.readUrl("control.productstyle.list") + "?productid=" + productStyle.getProduct().getId());
				return "message";
			}
			//�ϴ������е�·��
			//G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ �������realpath
			String realpath = ServletActionContext.getServletContext().getRealPath("/images");
			ProductForm.saveProductImageFile(pf.getImagefile(), pf.getImagefileFileName(), productStyle.getProduct().getType().getTypeid(), productStyle.getProduct().getId(), realpath);
		}
		
		/***************************************************************************************************************
		 * 					��Ʒ��Ϣ���浽���ݿ�																			****
		 ***************************************************************************************************************/
		productStyleService.update(productStyle);	//���������Ʒ������������֮��hibernate��������Ʒ��id�Ÿ�ֵ��product
				
		
		/***************************************************************************************************************
		 * 								��ת�ɹ�֮��ҳ�洫ֵ														    	  **
		 ***************************************************************************************************************/
		request.setAttribute("message", "��Ʒ�޸ĳɹ�");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.list") + "?productid=" + productStyle.getProduct().getId());
		return "message";
	}
	
	/**
	 * ��ʾ��ʽ��ӽ���
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String addUI() throws Exception
	{
		return "add";
	}
	
	/**
	 * ��ʾ��ʽ��Ӵ���
	 * @return String struts2�ķ�����תresult
	 * @throws Exception
	 */
	public String add() throws Exception
	{
		//��������ͼƬ·��
		//���ϴ�֮ǰ�ж��ϴ�ͼƬ�����ǲ��Ƿ���Ҫ��
		if(!pf.validateFileType(pf.getImagefileFileName()))
		{
			request.setAttribute("message", "ͼƬ��ʽ����");
			request.setAttribute("urladdress", SiteUrl.readUrl("control.productstyle.list"));
			return "message";
		}
		//�õ�ͼƬ��׺
		//String ext = pf.getImagefileFileName().substring(pf.getImagefileFileName().lastIndexOf('.'));
		if(pf.getImagefile().length() > 409600)
		{
			request.setAttribute("message", "ͼƬ���ܴ���400k");
			request.setAttribute("urladdress", SiteUrl.readUrl("control.productstyle.list"));
			return "message";
		}
		ProductInfo productInfo = new ProductInfo();	//���ҵ��Ĳ�Ʒ
		//������Ӧ��id�ŵõ���Ӧ�Ĳ�Ʒ
		productInfo = productInfoService.find(ProductInfo.class, pf.getProductid());
		/***************************************************************************************************************
		 * 					��Ʒ��Ϣ���浽���ݿ�																			****
		 ***************************************************************************************************************/
		//����Ҫ����Ĳ�Ʒ��ʽ
		ProductStyle productStyle = new ProductStyle(pf.getStylename(), pf.getImagefileFileName());	//�ϴ����ļ���Ϣ
		productStyle.setProduct(productInfo);
		productStyleService.save(productStyle);
		
		/***************************************************************************************************************
		 * 								�ļ��ϴ�  													    				  **
		 ***************************************************************************************************************/
		//�����Ʒ�����Ŀ¼
		//G:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\babaSport_1100_brand\images\ �������realpath
		String realpath = ServletActionContext.getServletContext().getRealPath("/images");
		ProductForm.saveProductImageFile(pf.getImagefile(), pf.getImagefileFileName(), productInfo.getType().getTypeid(), productInfo.getId(), realpath);
		
		/***************************************************************************************************************
		 * 								��ת�ɹ�֮��ҳ�洫ֵ														    	  **
		 ***************************************************************************************************************/
		request.setAttribute("message", "��Ʒ��ӳɹ�");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.productstyle.list") + "?productid=" + productInfo.getId());
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
