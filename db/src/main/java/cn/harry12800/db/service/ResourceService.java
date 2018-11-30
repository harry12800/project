/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
 
import cn.harry12800.db.entity.Resource;
import cn.harry12800.db.mapper.ResourceMapper;

/**
 * 资源：自动生成文档，markdown文档，上传的文件Service
 * @author 周国柱
 * @version 1.0
 */
@Component
//@Transactional(readOnly = true)
public class ResourceService {// extends CrudService<ResourceMapper, Resource> {

	@Autowired
	ResourceMapper mapper;
	
	 
	public List<Resource> findAll() {
		return mapper.findAll();
	}
	public List<Resource> findByIds(Set<?> set){
		return mapper.findByIds(set);
	}
	
	public int save(Resource t){
		return mapper.save(t);
	}
	public int update(Resource t){
		return mapper.update(t);
	}
	 
	public int deleteByIds(Set<?> set){
		return mapper.deleteByIds(set);
	}
	 
}
	