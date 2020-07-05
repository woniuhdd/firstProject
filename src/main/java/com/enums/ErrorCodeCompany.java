package com.enums;


import com.common.enums.ICommonEnum;

/**
 * 
 * 配送企业接口调用错误码  
 *
 * @ClassName: ErrorCodeCompany  
 * @author zhou.xy
 * @date 2016年5月4日 上午10:47:44  
 *
 */
public enum ErrorCodeCompany implements ICommonEnum<Integer> {
	PARAMETERS_CANNOT_BE_EMPTY(1, "参数不能为空"),
	PARAMETERS_DICT_ERROR(2, "参数字典不正确"),
	PARAMETERS_FORMAT_ERROR(3, "参数格式不正确"), 
	DATA_NOT_FOUND(4, "数据不存在"),
	DATA_STATUS_ERROR(5, "数据状态不符"),
	DATA_RESUBMIT(6, "数据重复提交"),
	DISTRIBUTE_NUMBER_ERROR(7, "配送数量异常"),
	DISTRIBUTE_BATCHCODE_REPEAT(8, "批号、发票号重复"),
	DISTRIBUTE_TIMEOUT(9, "配送时效过期"),
	DISTRIBUTE_BATCH_ERROR(10, "批次入库信息异常");

	private int key;
	private String value;

	private ErrorCodeCompany(int key, String value) {
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
