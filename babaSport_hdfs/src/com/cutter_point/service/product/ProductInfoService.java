/**
 * ���ܣ�����ǲ�Ʒҵ��Ľӿ�
 * �ļ���ProductInfoService.java
 * ʱ�䣺2015��5��22��16:48:25
 * ���ߣ�cutter_point
 */
package com.cutter_point.service.product;

import java.util.List;

import com.cutter_point.bean.product.Brand;
import com.cutter_point.bean.product.ProductInfo;
import com.cutter_point.service.base.DAO;

public interface ProductInfoService extends DAO
{
	//�����涨��ProductInfoServiceר�еķ���
	
	/**
	 * �����Ϊ�����ò�Ʒ�Ƿ��ϼܵĹ���
	 * @param productids ���õ�һ�Ѳ�Ʒ��id��
	 * @param status ����״̬
	 */
	public void setVisibleStatus(Integer[] productids, boolean status);
	
	/**
	 * ���ò�Ʒ�Ƿ�Ҫ�Ƽ�
	 * @param productids ���õ�һ�Ѳ�Ʒ��id��
	 * @param status ����״̬
	 */
	public void setCommendStatus(Integer[] productids, boolean status);
	
	/**
	 * ����Ǹ�������ids�Ż�ȡ��Ӧ��Ʒ��
	 * @param typeid
	 * @return
	 */
	public List<Brand> getBrandsByProductTypeid(Integer[] typeids);
	
	/**
	 * ��ȡ������ಢ�ұ��Ƽ��Ĳ�Ʒ
	 * @param typeid ���id
	 * @param maxResult ��ȡ�Ĳ�Ʒ����
	 * @return
	 */
	public List<ProductInfo> getTopSell(Integer typeid, int maxResult);
	
	/**
	 * ��ȡָ����id�Ĳ�Ʒ��Ϣ
	 * @param productids ��Ʒ��id����
	 * @param maxResult ���Ļ�ȡ�ļ�¼��
	 * @return
	 */
	public List<ProductInfo> getViewHistory(Integer[] productids, int maxResult);

}
