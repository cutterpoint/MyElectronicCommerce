package com.cutter_point.web.formbean.hadoop;

import com.cutter_point.web.formbean.BaseForm;

public class HadoopForm extends BaseForm
{
	private String filePath;  //��ǰ�ļ�·��

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
			//���ǵ���һ��·��hdfs://master:9000/cutter_point��������ӵ�
			String s = filepath;
			path = s.substring(s.indexOf("9000") + 4);
		}
		
		return path;
	}
}
