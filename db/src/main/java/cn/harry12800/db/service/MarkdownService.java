/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package cn.harry12800.db.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

import cn.harry12800.db.entity.Markdown;
import cn.harry12800.db.mapper.MarkdownMapper;

/**
 * 手写的markdownService
 * @author 周国柱
 * @version 1.0
 */
@Component
//@Transactional(readOnly = true)
public class MarkdownService {// extends CrudService<MarkdownMapper, Markdown> {

	@Autowired
	MarkdownMapper mapper;

	public Markdown findById(Long id) {
		return mapper.findById(id);
	}

	public List<Markdown> findAll() {
		return mapper.findAll();
	}

	public int save(Markdown t) {
		return mapper.save(t);
	}

	public int update(Markdown t) {
		return mapper.update(t);
	}

	public int deleteByIds(Set<?> ids) {
		return mapper.deleteByIds(ids);
	}

	public int deleteById(Long id) {
		return mapper.deleteById(id);
	}
}
