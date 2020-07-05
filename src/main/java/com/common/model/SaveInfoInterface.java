package com.common.model;

import java.util.Date;

/**
 * 内容摘要：新增数据时信息规范
 * @author 王幸蔚
 * @date 2017-07-18
 */
public interface SaveInfoInterface {
	void setAddUserId(String userId);
	void setAddUser(String userName);
	void setAddDttm(Date date);

	void setLastUpdUserId(String userId);
	void setLastUpdUser(String userName);
	void setLastUpdDttm(Date date);
}
