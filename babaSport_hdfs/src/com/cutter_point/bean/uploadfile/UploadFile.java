/**
 * 功能：这个是用来存放文件信息的实体
 * 时间：2015年5月25日08:57:37
 * 文件：UploadFile.java
 * 作者：cutter_point
 */
package com.cutter_point.bean.uploadfile;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(name="seq_2", sequenceName="seq_2", allocationSize=1, initialValue=1)	//oracle的id自增长，通过一个序列自增长，起始是1，每次增长1
public class UploadFile implements Serializable
{
	private static final long serialVersionUID = 4694353267624420027L;
	//文件的属性有ID，路径，添加日期
	private Integer id;
	private String filepath;
	private Date uploadtime = new Date();
	
	public UploadFile()
	{
	}

	public UploadFile(String filepath)
	{
		this.filepath = filepath;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_2")	//自增长
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}
	
	/**
	 * 这个是文件的路径
	 * 格式： /images/brand/年/月/日/时/aaaa.gif  长度设定可以是80
	 * @return
	 */
	@Column(length = 200, nullable=false)
	public String getFilepath()
	{
		return filepath;
	}

	public void setFilepath(String filepath)
	{
		this.filepath = filepath;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUploadtime()
	{
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime)
	{
		this.uploadtime = uploadtime;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((filepath == null) ? 0 : filepath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UploadFile other = (UploadFile) obj;
		if (filepath == null)
		{
			if (other.filepath != null)
				return false;
		} else if (!filepath.equals(other.filepath))
			return false;
		return true;
	}
}
