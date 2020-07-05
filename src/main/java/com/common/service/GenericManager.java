package com.common.service;


import com.common.utils.Pagination;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 内容摘要：公共基础Service接口
 * @author 王幸蔚
 * @date 2017-07-18
 */
@Service
public interface GenericManager<T, PK> {

	/** 通过主键id获取数据 */
	T getById(PK id);
	/** 根据参数获取数量 */
	int countByParams(Map<String, Object> params);
	/** 根据参数条件获取数据 */
	List<T> getListByParams(Map<String, Object> map);
	/** 获取分页数据 */
	Pagination getList(Pagination page);
	/** 获取导入数据 */
	List<T> getListWithImport(Map<String, Object> map);
	/** 获取导入数据 封装成分页对象*/
	Pagination getListWithImport(Pagination page);
	/** 插入数据 */
	boolean save(T entity);
	/** 批量插入数据 */
	boolean saveBatch(List<T> entity);
	/** 根据id修改数据 */
	boolean updateById(T entity);
	/** 根据id修改某些条件字段 */
	boolean updateBySelective(T entity);
	/** 根据id删除数据 */
	boolean deleteById(PK id);
	/** 批量插入指定表 */
	boolean saveBatchTable(List<T> datas, String tableName);

}
