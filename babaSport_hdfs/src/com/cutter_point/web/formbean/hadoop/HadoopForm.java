package com.cutter_point.web.formbean.hadoop;

import com.cutter_point.web.formbean.BaseForm;

public class HadoopForm extends BaseForm
{
	private String filePath;  //当前文件路径

	public String getFilePath() 
	{
		return filePath;
	}

	public void setFilePath(String filePath) 
	{
		this.filePath = filePath;
	}
	
	public static String getcurrentPath(String filepath)
	{
		String path;
		if(filepath == null)
		{
			path = "/";
		}
		else
		{
			//我们调整一下路径hdfs://master:9000/cutter_point，这个样子的
			String s = filepath;
			path = s.substring(s.indexOf("9000") + 4);
		}
		
		return path;
	}
}
