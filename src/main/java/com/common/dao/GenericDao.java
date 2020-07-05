package com.common.dao;

import java.util.List;
import java.util.Map;

/**
 * 内容摘要：公共基础dao类
 * @author 王幸蔚
 * @date 2017-07-17
 */
@Mapper
public interface GenericDao<T, PK> {

	/* 通过主键id获取数据 */
	T getById(PK id);
	/* 根据参数获取数量 */
	int countByParams(Map<String, Object> params);
	/* 根据参数条件获取数据 */
	List<T> getListByParams(Map<String, Object> map);
	/* 获取导入数据 */
	List<T> getListWithImport(Map<String, Object> map);
	/* 插入数据 */
	int save(T entity);
	/* 批量插入数据 */
	int saveBatch(List<T> entity);
	/* 根据id修改数据 */
	int updateById(T entity);
	/* 根据id修改某些条件字段 */
	int updateBySelective(T entity);
	/* 根据id删除数据 */
	int deleteById(PK id);
	/* 批量插入指定表 */
	int saveBatchTable(List<T> datas, String tableName);

}
