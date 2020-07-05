package com.common.model;

import java.util.Date;

/**
 * 内容摘要：修改数据时信息规范
 * @author 王幸蔚
 * @date 2017-07-18
 */
public interface UpdateInfoInterface {
	void setLastUpdUserId(String userId);
	void setLastUpdUser(String userName);
	void setLastUpdDttm(Date date);
}
