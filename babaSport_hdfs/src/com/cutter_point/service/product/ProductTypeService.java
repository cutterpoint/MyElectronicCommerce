/**
 * ���ܣ�����ǲ�Ʒ���ܵĽӿ�
 * ʱ�䣺2015��5��25��09:29:07
 * �ļ���ProductTypeService.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.service.product;

import java.util.List;

import com.cutter_point.service.base.DAO;

public interface ProductTypeService extends DAO
{
	//�����涨��ProductTypeServiceר�еķ���
	/**
	 * ��ȡ���ids�µ������id
	 * @param parentids ����id����
	 * @return
	 */
	public List<Integer> getSubTypeid(Integer[] parentids);
}