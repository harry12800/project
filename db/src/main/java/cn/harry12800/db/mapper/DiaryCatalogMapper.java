/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.mapper;

import java.util.List;
import java.util.Set;

import cn.harry12800.dao.CrudDao;
import cn.harry12800.db.entity.DiaryCatalog;

/**
 * Mapper
 * 
 * @author 周国柱
 * @version 1.0
 *          <dt>jdbc:mysql://119.23.9.164:3306/scan
 *          <dt>root
 *          <dt>zhouguozhu
 *          <dt>代码自动生成!数据库的资源文件.
 *          <dt>代码自动生成!数据库的资源文件.
 */

public interface DiaryCatalogMapper extends CrudDao<DiaryCatalog> {
	static final long serialVersionUID = 1L;

	DiaryCatalog findById(String id);

	List<DiaryCatalog> findAll();

	int save(DiaryCatalog t);

	int deleteByIds(String ids);

	int deleteById(String id);

	List<DiaryCatalog> findBySql(String sql);

	List<DiaryCatalog> findAllByUserId(String userId);

	List<DiaryCatalog> findByIds(Set<?> set);

	int deleteByIds(Set<?> set);

}
