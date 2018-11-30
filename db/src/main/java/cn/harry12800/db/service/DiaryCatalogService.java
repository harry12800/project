/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

import cn.harry12800.db.entity.DiaryCatalog;
import cn.harry12800.db.mapper.DiaryCatalogMapper;
import cn.harry12800.db.mapper.DiaryMapper;

/**
 * Service
 * 
 * @author 周国柱
 * @version 1.0
 */
@Component
// @Transactional(readOnly = true)
public class DiaryCatalogService {// extends CrudService<DiaryCatalogMapper,
									// DiaryCatalog> {

	@Autowired
	DiaryCatalogMapper mapper;
	@Autowired
	DiaryMapper diaryMapper;

	public List<DiaryCatalog> findAll() {
		return mapper.findAll();
	}

	public List<DiaryCatalog> findByIds(Set<?> set) {
		return mapper.findByIds(set);
	}

	public int save(DiaryCatalog t) {
		return mapper.save(t);
	}

	public int update(DiaryCatalog t) {
		return mapper.update(t);
	}

	public int deleteByIds(Set<?> set) {
		return mapper.deleteByIds(set);
	}

	public DiaryCatalog findById(String id) {
		return mapper.findById(id);
	}

	public int deleteByIds(String ids) {
		return mapper.deleteByIds("id=" + ids);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteById(String id) {
		diaryMapper.deleteUserDiaryByCatalogId(id);
		diaryMapper.deleteByCatalogId(id);
		return mapper.deleteById(id);
	}

	public List<DiaryCatalog> findAllByUserId(String userId) {
		return mapper.findAllByUserId(userId);
	}

}
