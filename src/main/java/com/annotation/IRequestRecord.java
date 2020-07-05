package com.annotation;

import java.lang.annotation.*;

/**
 * 
 * 接口请求信息注解
 *
 * @ClassName: IRequestRecord  
 * @author zhou.xy
 * @date 2016年5月19日 上午11:43:55  
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IRequestRecord {
	boolean isRecord() default true;// 是否记录调用请求
	String interfaceUrl();// 调用接口URL
}
