/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package cn.harry12800.db.service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

import cn.harry12800.db.entity.Application;
import cn.harry12800.db.entity.Directory;
import cn.harry12800.db.mapper.ApplicationMapper;
import cn.harry12800.db.mapper.DirectoryMapper;

/**
 * 开发组开发的应用Service
 * @author 周国柱
 * @version 1.0
 */
@Component
//@Transactional(readOnly = true)
public class ApplicationService {// extends CrudService<ApplicationMapper, Application> {

	@Autowired
	ApplicationMapper mapper;
	@Autowired
	DirectoryMapper directoryMapper;
	@Autowired
	DirectoryService directoryService;

	public Application findById(Long id) {
		return mapper.findById(id);
	}

	public List<Application> findAll() {
		return mapper.findAll();
	}

	public int save(Application t) {
		return mapper.save(t);
	}

	public int update(Application t) {
		return mapper.update(t);
	}

	public int deleteByIds(Set<?> ids) {
		return mapper.deleteByIds(ids);
	}

	public int deleteById(Long id) {
		Application app = mapper.findById(id);
		if (app == null)
			return 0;
		List<Directory> dirs = directoryMapper.findByAppId(id);
		System.out.println("开始删除目录");
		if (Objects.nonNull(dirs)) {
			for (Directory directory : dirs) {
				directoryService.deleteById(directory.getId());
				System.out.println("删除目录:" + directory.getId());
			}
		}
		System.out.println("删除目录结束");
		return mapper.deleteById(id);
	}
}
