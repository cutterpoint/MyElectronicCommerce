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

//Ϊ������ÿ������������񣬲��Ұ�����ཻ��spring�й�
@Service
@Transactional
public class UploadHdfsServiceBean implements UploadHdfsService
{
	//HDFS�ķ��ʵ�ַ
	private static final String HDFS = "hdfs://master:9000";
	private String hdfsPath;	//hdfs�ĵ�ַ
	private Configuration conf;  //hadoop�������ļ�
	
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
	
	//��������
    public static void main(String[] args) throws IOException {
        JobConf conf = config();
        UploadHdfsServiceBean hdfs = new UploadHdfsServiceBean(conf);
        //hdfs.mkdirs("/xiaoifeng");
        //hdfs.copyFile("C:\\files", "/wgc/");
        hdfs.ls("/cutter_point/input/ncdc");
        //hdfs.rmr("/wgc/files");
        //hdfs.download("/wgc/��3��windows��hadoop+eclipse�����.docx", "c:\\");
        //System.out.println("success!");
    }      
	
	//����hadoop�����ļ�
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
		//�������ǵ��ļ���·��
		Path path = new Path(folder);
		//���ǵ��ļ�ϵͳ��
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		if(!fs.exists(path))		//���·��������
		{
			fs.mkdirs(path);	//������Ӧ���ļ���
		}
		fs.close();  //�ر��ļ�ϵͳ
	}

	@Override
	//ĳ���ļ��е��ļ��б�
	public FileStatus[] ls(String folder) throws IOException 
	{
		//�������ǵ��ļ�·��
		Path path = new Path(folder);
		//���ǵ��ļ�ϵͳ��
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		//��ȡ�ļ�Ŀ¼�µ������ļ�
		FileStatus[] list = fs.listStatus(path);
		if(list != null)
		{
			//�����е��ļ�һ��һ����ȡ����
			for(FileStatus f : list)
			{
				//�����Ӧ����Ϣ
				System.out.printf("%s, ����ǣ�%s, �ļ���С��%db\n", f.getPath().getName(), (f.isDir() ? "Ŀ¼" : "�ļ�"), f.getLen());
			}
			//������֮�����ǹر��ļ�ϵͳ
			fs.close();
		}
		return list;
	}

	@Override
	public void copyFile(String local, String remote) throws IOException 
	{
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		//�ϴ��ļ���hdfs��
		fs.copyFromLocalFile(new Path(local), new Path(remote));
		fs.close();
	}

	@Override
	//ɾ���ļ����ļ���
	public void rmr(String folder) throws IOException 
	{
		//����Ҫɾ����·������
		Path path = new Path(folder);
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		//ɾ�����·���µ������ļ����ļ���
		fs.deleteOnExit(path);
		fs.close();
	}

	@Override
	public void download(String remote, String local) throws IOException 
	{
		//�����������ļ�
		Path path = new Path(remote);
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		//��hdfs�����ļ�����Ӧ��local��
		fs.copyToLocalFile(path, new Path(local));
		System.out.println("download: from" + remote + " to " + local);
		fs.close();
	}
}
