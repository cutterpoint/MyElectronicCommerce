package com.cutter_point.bean.hadoop.uploadhdfs;

import org.apache.hadoop.fs.Path;

public class HdfsFile 
{
	//�ļ���
	private String filename;
	//�ļ�����,�ж��ǲ����ļ���
	private boolean dir = false;
	//�ļ���С
	private long length;
	private Path path;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public boolean isDir() {
		return dir;
	}
	public void setDir(boolean dir) {
		this.dir = dir;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
}
