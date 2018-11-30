/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

import cn.harry12800.db.entity.Diary;
import cn.harry12800.db.entity.User;
import cn.harry12800.db.mapper.DiaryMapper;

/**
 * Service
 * @author 周国柱
 * @version 1.0
 */
@Component
//@Transactional(readOnly = true)
public class DiaryService {// extends CrudService<DiaryMapper, Diary> {

	@Autowired
	DiaryMapper mapper;
	
	 
	public List<Diary> findAll() {
		return mapper.findAll();
	}
	public List<Diary> findByIds(Set<?> set){
		return mapper.findByIds(set);
	}
	
	public int save(Diary t){
		return mapper.save(t);
	}
	public int update(Diary t){
		return mapper.update(t);
	}
	 
	public int deleteByIds(Set<?> set){
		return mapper.deleteByIds(set);
	}
	public void save(Diary diary, String userId) {
		
	}
	public Diary findById(String id) {
		return null;
	}
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}
	public List<Diary> findAllByUserIdContainKey(String key, String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Diary> findAllByUserId(String userId) {
		return null;
	}
	public List<Diary> findTop10() {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Diary> findAllByCatalogId(String diaryCatalogId) {
		// TODO Auto-generated method stub
		return null;
	}
	public void incHint(String id) {
		
	}
	public User findUserByDiaryId(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	public Object encipher(String id) {
		
		return null;
	}
	public Object decipher(String id) {
		return null;
	}
	 
}
	