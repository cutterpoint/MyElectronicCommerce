/**
 * ����:ʵ�ֶ�hadoop��hdfs�Ĵ洢�ϴ����ع���
 * ʱ�䣺2015��7��22��11:28:51
 * �ļ���HdfsDAO.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.service.hadoop.base;

import org.apache.hadoop.fs.FileStatus;

public interface HdfsDAO 
{
	/**
	 * �ڸ�Ŀ¼�´����ļ�
	 * @param folder �����ļ��е�����
	 * @throws Exception 
	 */
	public void mkdirs(String folder) throws Exception;
	
	/**
	 * ��ȡĳ���ļ��е��ļ��б�
	 * @param folder �ļ��е�����
	 * @return
	 */
	public FileStatus[] ls(String folder) throws Exception;
	
	/**
	 * �ϴ��ļ�����Ӧ���ļ���
	 * @param local
	 * @param remote
	 */
	public void copyFile(String local, String remote) throws Exception;
	
	/**
	 * ɾ���ļ����ļ���
	 * @param folder �ļ������ļ�����
	 */
	public void rmr(String folder) throws Exception;
	
	/**
	 * �����ļ�������
	 * @param remote
	 * @param local
	 */
	public void download(String remote, String local) throws Exception;
	
}
