/**
 * ���ܣ������Ʒ����ʽ�Ľӿ�
 * �ļ���ProductStyleService.java
 * ʱ�䣺2015��5��31��19:33:19
 * ���ߣ�cutter_point
 */
package com.cutter_point.service.product;

import com.cutter_point.service.base.DAO;

public interface ProductStyleService extends DAO
{
	//�����涨��ProductStyleServiceר�еķ���
	/**
	 * �����Ϊ�����ò�Ʒ�Ƿ��ϼܵĹ���
	 * @param productids ���õ�һ�Ѳ�Ʒ��id��
	 * @param status ����״̬
	 */
	public void setVisibleStatus(Integer[] productstyleids, boolean status);
}
