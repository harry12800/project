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

import cn.harry12800.db.entity.Directory;
import cn.harry12800.db.entity.Resource;
import cn.harry12800.db.mapper.ApiMapper;
import cn.harry12800.db.mapper.AutoApiMarkdownMapper;
import cn.harry12800.db.mapper.DirectoryMapper;
import cn.harry12800.db.mapper.MarkdownMapper;
import cn.harry12800.db.mapper.ResourceMapper;
import cn.harry12800.db.mapper.ResourceUploadMapper;

/**
 * 项目的层次目录Service
 * @author 周国柱
 * @version 1.0
 */
@Component
//@Transactional(readOnly = true)
public class DirectoryService {// extends CrudService<DirectoryMapper, Directory> {

	@Autowired
	DirectoryMapper mapper;
	@Autowired
	ResourceMapper resourceMapper;
	@Autowired
	ApiMapper apiMapper;
	@Autowired
	MarkdownMapper markdownMapper;
	@Autowired
	AutoApiMarkdownMapper autoApiMarkdownMapper;
	@Autowired
	ResourceUploadMapper resourceUploadMapper;
	@Autowired
	ResourceService resourceService;

	public Directory findById(Long id) {
		return mapper.findById(id);
	}

	public List<Directory> findAll() {
		return mapper.findAll();
	}

	public int save(Directory t) {
		return mapper.save(t);
	}

	public int update(Directory t) {
		return mapper.update(t);
	}

	public int deleteByIds(Set<?> ids) {
		return mapper.deleteByIds(ids);
	}

	public int deleteById(Long id) {
		List<Resource> resources = resourceMapper.findByDirectoryId(id);
		if (Objects.nonNull(resources))
			for (Resource resource : resources) {
				apiMapper.deleteByResourceId(resource.getId());
				markdownMapper.deleteById(resource.getId());
				autoApiMarkdownMapper.deleteById(resource.getId());
				resourceUploadMapper.deleteById(resource.getId());
				resourceService.deleteById(resource.getId());
			}
		return mapper.deleteById(id);
	}
}
