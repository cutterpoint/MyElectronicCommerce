/**
 * ���ܣ��������������ļ���Ϣ��ʵ��
 * ʱ�䣺2015��5��25��08:57:37
 * �ļ���UploadFile.java
 * ���ߣ�cutter_point
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
@SequenceGenerator(name="seq_2", sequenceName="seq_2", allocationSize=1, initialValue=1)	//oracle��id��������ͨ��һ����������������ʼ��1��ÿ������1
public class UploadFile implements Serializable
{
	private static final long serialVersionUID = 4694353267624420027L;
	//�ļ���������ID��·�����������
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
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_2")	//������
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}
	
	/**
	 * ������ļ���·��
	 * ��ʽ�� /images/brand/��/��/��/ʱ/aaaa.gif  �����趨������80
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
