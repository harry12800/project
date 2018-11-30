/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
 
import cn.harry12800.db.entity.UserApp;
import cn.harry12800.db.mapper.UserAppMapper;

/**
 * 用户与App的关系Service
 * @author 周国柱
 * @version 1.0
 */
@Component
//@Transactional(readOnly = true)
public class UserAppService {// extends CrudService<UserAppMapper, UserApp> {

	@Autowired
	UserAppMapper mapper;
	
	 
	public List<UserApp> findAll() {
		return mapper.findAll();
	}
	public List<UserApp> findByIds(Set<?> set){
		return mapper.findByIds(set);
	}
	
	public int save(UserApp t){
		return mapper.save(t);
	}
	public int update(UserApp t){
		return mapper.update(t);
	}
	 
	public int deleteByIds(Set<?> set){
		return mapper.deleteByIds(set);
	}
	 
}
	