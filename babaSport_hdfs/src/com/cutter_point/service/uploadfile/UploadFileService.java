/**
 * 功能：这个是文件业务处理的功能接口
 * 时间：2015年5月25日09:29:56
 * 文件：UploadFileService.java
 * 作者：cutter_point
 */
package com.cutter_point.service.uploadfile;

import java.util.List;

import com.cutter_point.service.base.DAO;

public interface UploadFileService extends DAO
{
	//这里面定义ProductTypeService专有的方法
	/**
	 * 定义upload的获取路径的方法
	 * @param ids[]
	 * @return List 返回String类型的文件路径集合
	 */
	public List<String> getFilepath(Integer[] ids);
}
