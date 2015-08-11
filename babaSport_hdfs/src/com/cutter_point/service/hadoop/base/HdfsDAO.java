/**
 * 功能:实现对hadoop的hdfs的存储上传下载功能
 * 时间：2015年7月22日11:28:51
 * 文件：HdfsDAO.java
 * 作者：cutter_point
 */
package com.cutter_point.service.hadoop.base;

import org.apache.hadoop.fs.FileStatus;

public interface HdfsDAO 
{
	/**
	 * 在根目录下创建文件
	 * @param folder 我们文件夹的名字
	 * @throws Exception 
	 */
	public void mkdirs(String folder) throws Exception;
	
	/**
	 * 获取某个文件夹的文件列表
	 * @param folder 文件夹的名字
	 * @return
	 */
	public FileStatus[] ls(String folder) throws Exception;
	
	/**
	 * 上传文件到相应的文件夹
	 * @param local
	 * @param remote
	 */
	public void copyFile(String local, String remote) throws Exception;
	
	/**
	 * 删除文件或文件夹
	 * @param folder 文件名或文件夹名
	 */
	public void rmr(String folder) throws Exception;
	
	/**
	 * 下载文件到本地
	 * @param remote
	 * @param local
	 */
	public void download(String remote, String local) throws Exception;
	
}
