package com.common.enums;

/**
 * 一般键值型枚举的接口，规范 获取键和值的方式
 * 
 * @author he.fan
 */
public interface ICommonEnum<T>  {
	public T getKey();
	public String getValue();
}

/**
 * 
 * 通过值查找到名称， 通常用在 列表或者详情页面获取值
 * 由于枚举不能继承， 接口不能声明静态方法
 * 
 */
/*
public static String getNameByValue(int ordinal) {
	for(ICommonEnum ce:values()){
		if(ce.getValue()==ordinal){
			return ce.getName();
		}
	}
	return null;
}
*/