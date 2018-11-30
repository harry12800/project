/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package cn.harry12800.scan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.harry12800.db.entity.Diary;
import cn.harry12800.db.entity.User;
import cn.harry12800.db.mapper.DiaryCatalogMapper;
import cn.harry12800.db.mapper.DiaryMapper;
import cn.harry12800.db.mapper.UserMapper;

/**
 * Service
 * 
 * @author 周国柱
 * @version 1.0
 */
@Service
@Transactional()
public class DiaryService {// extends CrudService<DiaryMapper, Diary> {

	@Autowired
	DiaryMapper diaryMapper;
	@Autowired
	DiaryCatalogMapper diaryCatalogMapper;
	@Autowired
	UserMapper userMapper;

	public Diary findById(String id) {
		return diaryMapper.findById(id);
	}

	public List<Diary> findAll() {
		return diaryMapper.findAll();
	}

	public List<Diary> findAllByCatalogId(String id) {
		return diaryMapper.findAllByCatalogId(id);
	}

	//	public int save(Diary t) {
	//		return mapper.save(t);
	//	}

	public int update(Diary t) {
		return diaryMapper.update(t);
	}

	public List<Diary> findBySql(String sql) {
		return diaryMapper.findBySql(sql);
	}

	public int deleteByIds(String ids) {
		return diaryMapper.deleteByIds("id=" + ids);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteById(String id) {
		diaryMapper.deleteUserDiaryByDiaryId(id);
		return diaryMapper.deleteById(id);
	}

	public List<Diary> findAllContainKey(String key) {
		return diaryMapper.findAllContainKey(key);
	}

	public void incHint(String id) {
		diaryMapper.incHint(id);
	}

	public List<Diary> findTop10() {
		return diaryMapper.findTop10();
	}

	public List<Diary> findAllByUserId(String userId) {
		return diaryMapper.findAllByUserId(userId);
	}

	public List<Diary> findAllByUserIdContainKey(String key, String userId) {
		return diaryMapper.findAllByUserIdContainKey(key, userId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Diary diary, String userId) {
		diaryMapper.save(diary);
		diaryMapper.saveUserDiary(diary.getId(), userId);
	}

	public User findUserByDiaryId(String id) {
		return userMapper.findUserByDiaryId(id);
	}

	public int encipher(String id) {
		return diaryMapper.encipher(id);
	}

	public int decipher(String id) {
		return diaryMapper.decipher(id);
	}
}
