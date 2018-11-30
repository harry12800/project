/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package cn.harry12800.scan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
@Service
@Transactional()
public class DiaryCatalogService {// extends CrudService<DiaryMapper, Diary> {

	@Autowired
	DiaryCatalogMapper mapper;
	@Autowired
	DiaryMapper diaryMapper;

	public DiaryCatalog findById(String id) {
		return mapper.findById(id);
	}

	public List<DiaryCatalog> findAll() {
		return mapper.findAll();
	}

	public int save(DiaryCatalog t) {
		return mapper.save(t);
	}

	public int update(DiaryCatalog t) {
		return mapper.update(t);
	}

	public List<DiaryCatalog> findBySql(String sql) {
		return mapper.findBySql(sql);
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
