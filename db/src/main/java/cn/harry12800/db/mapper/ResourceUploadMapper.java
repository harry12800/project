/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.harry12800.db.entity.ResourcesUpload;

/**
 * 
 * @author LL159127
 *
 */
public interface ResourceUploadMapper { //extends CrudDao<Markdown> {
	static final long serialVersionUID = 1L;

	/**
	* 通过多个id删除多行数据
	* 字符串ids的样子 eg:  `id in ('a','b')`
	**/
	int deleteByIds(Set<?> ids);

	/**
	* 删除单行数据
	*/
	int deleteById(Long id);

	/**
	 * 查找一行数据
	 * @param id
	 * @return
	 */
	ResourcesUpload findById(Long id);

	/**
	* 删除多行数据
	**/
	List<ResourcesUpload> findAll();

	/**
	* 保存数据
	*/
	int save(ResourcesUpload t);

	/**
	* 更新数据，通过id 修改所有字段属性
	*/
	int update(ResourcesUpload t);

	List<ResourcesUpload> findByIds(@Param("uploadIds") String uploadIds);

	//	List<ResourcesUpload> search(@Param("content") String content);

}
