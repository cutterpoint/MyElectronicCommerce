package com.cutter_point.service.hadoop.uploadhdfs.impl;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cutter_point.service.hadoop.uploadhdfs.UploadHdfsService;

//为这个类的每个方法添加事务，并且把这个类交给spring托管
@Service
@Transactional
public class UploadHdfsServiceBean implements UploadHdfsService
{
	//HDFS的访问地址
	private static final String HDFS = "hdfs://master:9000";
	private String hdfsPath;	//hdfs的地址
	private Configuration conf;  //hadoop的配置文件
	
	public UploadHdfsServiceBean()
	{}
	
	public UploadHdfsServiceBean(Configuration conf) 
	{
		this(HDFS, conf);
	}
	
	public UploadHdfsServiceBean(String hdfs, Configuration conf) 
	{
		this.hdfsPath = hdfs;
		this.conf = conf;
	}
	
	//启动函数
    public static void main(String[] args) throws IOException {
        JobConf conf = config();
        UploadHdfsServiceBean hdfs = new UploadHdfsServiceBean(conf);
        //hdfs.mkdirs("/xiaoifeng");
        //hdfs.copyFile("C:\\files", "/wgc/");
        hdfs.ls("/cutter_point/input/ncdc");
        //hdfs.rmr("/wgc/files");
        //hdfs.download("/wgc/（3）windows下hadoop+eclipse环境搭建.docx", "c:\\");
        //System.out.println("success!");
    }      
	
	//加载hadoop配置文件
	public static JobConf config()
	{
		JobConf conf = new JobConf(UploadHdfsServiceBean.class);
		conf.set("fs.default.name", HDFS);
		conf.setJobName("UploadHdfsServiceBean");
		return conf;
	}

	@Override
	public void mkdirs(String folder) throws IOException 
	{
		//创建我们的文件夹路径
		Path path = new Path(folder);
		//我们的文件系统类
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		if(!fs.exists(path))		//如果路径不存在
		{
			fs.mkdirs(path);	//创建相应的文件夹
		}
		fs.close();  //关闭文件系统
	}

	@Override
	//某个文件夹的文件列表
	public FileStatus[] ls(String folder) throws IOException 
	{
		//创建我们的文件路径
		Path path = new Path(folder);
		//我们的文件系统类
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		//获取文件目录下的所有文件
		FileStatus[] list = fs.listStatus(path);
		if(list != null)
		{
			//吧所有的文件一个一个地取出来
			for(FileStatus f : list)
			{
				//输出相应的信息
				System.out.printf("%s, 这个是：%s, 文件大小：%db\n", f.getPath().getName(), (f.isDir() ? "目录" : "文件"), f.getLen());
			}
			//输出完毕之后我们关闭文件系统
			fs.close();
		}
		return list;
	}

	@Override
	public void copyFile(String local, String remote) throws IOException 
	{
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		//上传文件到hdfs中
		fs.copyFromLocalFile(new Path(local), new Path(remote));
		fs.close();
	}

	@Override
	//删除文件或文件夹
	public void rmr(String folder) throws IOException 
	{
		//我们要删除的路径在哪
		Path path = new Path(folder);
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		//删除这个路径下的所有文件和文件夹
		fs.deleteOnExit(path);
		fs.close();
	}

	@Override
	public void download(String remote, String local) throws IOException 
	{
		//从哪里下载文件
		Path path = new Path(remote);
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		//从hdfs拷贝文件到相应的local中
		fs.copyToLocalFile(path, new Path(local));
		System.out.println("download: from" + remote + " to " + local);
		fs.close();
	}
}
