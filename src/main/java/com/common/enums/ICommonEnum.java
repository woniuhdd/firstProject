package com.common.enums;

/**
 * 一般键值型枚举的接口，规范 获取键和值的方式
 */
public interface ICommonEnum<T>  {
	public T getKey();
	public String getValue();
}
