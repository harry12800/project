/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.harry12800.dao.CrudDao;
import cn.harry12800.db.entity.Diary;

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

public interface DiaryMapper extends CrudDao<Diary> {
	static final long serialVersionUID = 1L;

	Diary findById(String id);

	List<Diary> findAll();

	int save(Diary t);

	int deleteByIds(String ids);

	int deleteById(String id);

	List<Diary> findBySql(String sql);

	List<Diary> findAllByCatalogId(String catalogId);

	List<Diary> findAllContainKey(String key);

	void incHint(String id);

	List<Diary> findTop10();

	List<Diary> findAllByUserId(String userId);

	List<Diary> findAllByUserIdContainKey(@Param("key") String key, @Param("userId") String userId);

	void saveUserDiary(@Param("diaryId") String diaryId, @Param("userId") String userId);

	void deleteByCatalogId(String id);

	void deleteUserDiaryByCatalogId(String id);

	void deleteUserDiaryByDiaryId(String id);

	int encipher(String id);

	int decipher(String id);

	int deleteByIds(Set<?> set);

	List<Diary> findByIds(Set<?> set);

}
