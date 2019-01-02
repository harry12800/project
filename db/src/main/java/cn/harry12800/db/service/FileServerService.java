/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
 
import cn.harry12800.db.entity.FileServer;
import cn.harry12800.db.mapper.FileServerMapper;

/**
 * Service
 * @author 周国柱
 * @version 1.0
 */
@Component
//@Transactional(readOnly = true)
public class FileServerService {// extends CrudService<FileServerMapper, FileServer> {

	@Autowired
	FileServerMapper mapper;
	
	public List<FileServer> findAll() {
		return mapper.findAll();
	}
	/**
	* 新增或者更新
	*/
	public int saveOrUpdate(FileServer t) {
		if(mapper.exist(t)>0){
			return mapper.updateNotNull(t);
		} else {
			return mapper.save(t);
		}
	}
	
	public List<FileServer> findByIds(Set<?> set){
		return mapper.findByIds(set);
	}
	
	public FileServer findById(String id){
		return mapper.findById(id);
	}
	public int save(FileServer t){
		return mapper.save(t);
	}
	public int update(FileServer t){
		return mapper.update(t);
	}
	 
	public int deleteByIds(Set<?> set){
		return mapper.deleteByIds(set);
	}
	public long countAll() {
		return mapper.countAll();
	}

	public List<FileServer> pageList(int pageIndex, int pageSize) {
		int index = (pageIndex -1)*pageSize;
		return mapper.pageList(index,pageSize);
	}
}
	