package cn.harry12800.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.harry12800.db.entity.FileResource;

/**
 * @author  harry12800
 *
 */
@Mapper
public interface FileResourceMapper { 
	public FileResource getResourceShareById(long id) ;

	public FileResource createResourceShare(FileResource resource) ;
	
	public <T> List<T> getAllResource(Class<?> className) ;

	public List<FileResource> getReadResource(long userid) ;
}
