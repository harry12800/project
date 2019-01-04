/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package cn.harry12800.db.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

import cn.harry12800.db.entity.Api;
import cn.harry12800.db.mapper.ApiMapper;

/**
 * 项目的接口ApiService
 * @author 周国柱
 * @version 1.0
 */
@Component
//@Transactional(readOnly = true)
public class ApiService {// extends CrudService<ApiMapper, Api> {

	@Autowired
	ApiMapper mapper;

	public Api findById(Long id) {
		return mapper.findById(id);
	}

	public List<Api> findAll() {
		return mapper.findAll();
	}

	public int save(Api t) {
		return mapper.save(t);
	}

	public int update(Api t) {
		return mapper.update(t);
	}

	public int deleteByIds(Set<?> ids) {
		return mapper.deleteByIds(ids);
	}

	public int deleteById(String id) {
		return mapper.deleteById(id);
	}
}
