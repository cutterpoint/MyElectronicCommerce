/**
 * ���ܣ�������ļ�ҵ����Ĺ��ܽӿ�
 * ʱ�䣺2015��5��25��09:29:56
 * �ļ���UploadFileService.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.service.uploadfile;

import java.util.List;

import com.cutter_point.service.base.DAO;

public interface UploadFileService extends DAO
{
	//�����涨��ProductTypeServiceר�еķ���
	/**
	 * ����upload�Ļ�ȡ·���ķ���
	 * @param ids[]
	 * @return List ����String���͵��ļ�·������
	 */
	public List<String> getFilepath(Integer[] ids);
}
