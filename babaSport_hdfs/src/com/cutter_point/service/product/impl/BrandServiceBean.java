/**
 * ���ܣ�����Ʒ�����ķ�����
 * �ļ���BrandServiceBean.java
 * ʱ�䣺2015��5��22��17:18:19
 * ���ߣ�cutter_point
 */
package com.cutter_point.service.product.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cutter_point.service.base.DaoSupport;
import com.cutter_point.service.product.BrandService;

@Service	//�൱����spring���涨��һ��bean,����ע��ķ�ʽ<context:component-scan base-package="com.cutter_point" />
@Transactional		//�ڷ���ִ�е�ʱ��������
public class BrandServiceBean extends DaoSupport implements BrandService
{

}
