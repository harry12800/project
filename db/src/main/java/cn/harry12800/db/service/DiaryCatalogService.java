/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

import cn.harry12800.db.entity.DiaryCatalog;
import cn.harry12800.db.mapper.DiaryCatalogMapper;

/**
 * Service
 * @author 周国柱
 * @version 1.0
 */
@Component
//@Transactional(readOnly = true)
public class DiaryCatalogService {// extends CrudService<DiaryCatalogMapper, DiaryCatalog> {

	@Autowired
	DiaryCatalogMapper mapper;
	
	 
	public List<DiaryCatalog> findAll() {
		return mapper.findAll();
	}
	public List<DiaryCatalog> findByIds(Set<?> set){
		return mapper.findByIds(set);
	}
	
	public int save(DiaryCatalog t){
		return mapper.save(t);
	}
	public int update(DiaryCatalog t){
		return mapper.update(t);
	}
	 
	public int deleteByIds(Set<?> set){
		return mapper.deleteByIds(set);
	}
	public List<DiaryCatalog> findAllByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	public DiaryCatalog findById(String id) {
		return null;
	}
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}
	public void deleteByIds(String id) {
		
	}
	 
}
	