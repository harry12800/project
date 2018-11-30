/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.mapper;

import java.util.List;
import java.util.Set;

import cn.harry12800.dao.CrudDao;
import cn.harry12800.db.entity.Remind;

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

public interface RemindMapper extends CrudDao<Remind> {
	static final long serialVersionUID = 1L;

	int save(Remind t);

	List<Remind> findAll();

	Remind findById(String id);

	int deleteByIds(String ids);

	List<Remind> findBySql(String sql);

	List<cn.harry12800.db.entity.Remind> findByIds(Set<?> set);

	int deleteByIds(Set<?> set);

}
