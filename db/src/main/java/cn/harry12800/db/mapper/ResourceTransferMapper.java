/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.mapper;

import java.util.List;
import java.util.Set;

import cn.harry12800.db.entity.ResourceTransfer;

/**
 * 资源转让记录表Mapper
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 * <dt>代码自动生成!数据库的资源文件.
 */

public interface ResourceTransferMapper { //extends CrudDao<ResourceTransfer> {
	static final long serialVersionUID = 1L;

	/**
	* 通过多个id删除多行数据
	* 字符串ids的样子 eg:  `id in ('a','b')`
	**/
	int deleteByIds(Set<?> ids);

	/**
	* 删除单行数据
	*/
	int deleteById(String id);

	/**
	 * 查找一行数据
	 * @param id
	 * @return
	 */
	ResourceTransfer findById(String id);

	/**
	* 删除多行数据
	**/
	List<ResourceTransfer> findAll();

	/**
	* 保存数据
	*/
	int save(ResourceTransfer t);

	/**
	* 更新数据，通过id 修改所有字段属性
	*/
	int update(ResourceTransfer t);

	/**
	* 自定义sql语句查询列表
	*/
	List<ResourceTransfer> findBySql(String sql);

	/*
	 * 
	 */
	List<ResourceTransfer> findByResourceId(Long resourceId);

}
