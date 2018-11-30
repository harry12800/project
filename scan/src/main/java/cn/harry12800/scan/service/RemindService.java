/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package cn.harry12800.scan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.harry12800.db.entity.Remind;
import cn.harry12800.db.mapper.RemindMapper;

/**
 * Service
 * 
 * @author 周国柱
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class RemindService {// extends CrudService<RemindMapper, Remind> {

	@Autowired
	RemindMapper mapper;

	public Remind findById(String id) {
		return mapper.findById(id);
	}

	public List<Remind> findAll() {
		return mapper.findAll();
	}

	public int save(Remind t) {
		return mapper.save(t);
	}

	public int update(Remind t) {
		return mapper.update(t);
	}

	public List<Remind> findBySql(String sql) {
		return mapper.findBySql(sql);
	}

	public int deleteByIds(String ids) {
		return mapper.deleteByIds(ids);
	}
}
