package com.enums;

import com.common.enums.ICommonEnum;

/**
 * 
 * 接口调用状态码  
 *
 * @ClassName: ReturnCode  
 * @author zhou.xy
 * @date 2016年5月4日 上午10:39:13  
 *
 */
public enum ReturnCode implements ICommonEnum<Integer> {
	SUCCESS(1, "业务执行成功"),
	UNAUTHORIZED(2, "访问未授权，请得到授权后访问"),
	ORG_PWD_ERROR(3, "机构名或密码错误，请使用正确的机构名或密码重试"), 
	TOKEN_INVALID(4, "应用码或授权码无效"),
	SERVICE_UNAVAILABLE(5, "服务不可用"),
	NETWORK_FAULT(6, "网络故障"),
	FAILED(7, "业务执行失败"),
	PARAMETERS_FORMAT_ERROR(8, "参数格式不正确"),
	TOKEN_HAVE(9,"令牌有效期超过5分钟，不能再次获取令牌"),
	VISIT_TOO_FREQUENTLY(10, "访问过于频繁，请稍后再试"),
	PASS_VISITLIMIT(11,"超过每日最大访问次数"),
	DATE_NULL(12, ""),
	NO_SUBMIT(13, "县级及基层医疗机构采购周期定为每月两次，于每月8-10日，23-25日提交采购单");

	private int key;
	private String value;

	private ReturnCode(int key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public Integer getKey() {
		return this.key;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	public static String getValueByKey(Integer key) {
		for (ICommonEnum<Integer> item : values()) {
			if (item.getKey().equals(key)) {
				return item.getValue();
			}
		}
		return null;
	}

	public static Integer getKeyByValue(String value) {
		for (ICommonEnum<Integer> item : values()) {
			if (item.getValue().equals(value)) {
				return (Integer) item.getKey();
			}
		}
		return null;
	}

	public static Integer getKeyByItemName(String itemName) {
		Integer res = null;
		try {
			res = valueOf(itemName).getKey();
		} catch (IllegalArgumentException | NullPointerException e) {
		}
		return res;
	}

}
