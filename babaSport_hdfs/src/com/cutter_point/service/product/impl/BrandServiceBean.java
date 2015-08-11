/**
 * 功能：这是品牌类别的服务类
 * 文件：BrandServiceBean.java
 * 时间：2015年5月22日17:18:19
 * 作者：cutter_point
 */
package com.cutter_point.service.product.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cutter_point.service.base.DaoSupport;
import com.cutter_point.service.product.BrandService;

@Service	//相当于在spring里面定义一个bean,这是注解的方式<context:component-scan base-package="com.cutter_point" />
@Transactional		//在方法执行的时候开启事务
public class BrandServiceBean extends DaoSupport implements BrandService
{

}
