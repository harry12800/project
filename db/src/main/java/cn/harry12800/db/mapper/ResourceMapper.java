/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.mapper;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import cn.harry12800.db.entity.Resource;

/**
 * 资源：自动生成文档，markdown文档，上传的文件Mapper
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 * <dt>代码自动生成!数据库的资源文件.
 */

public interface ResourceMapper { //extends CrudDao<Resource> {
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
	Resource findById(Long id);

	/**
	* 删除多行数据
	**/
	List<Resource> findAll();

	/**
	* 保存数据
	*/
	int save(Resource t);

	/**
	* 更新数据，通过id 修改所有字段属性
	*/
	int update(Resource t);

	/**
	 * user_resource关联插入
	 * @param userId	
	 * @param resourceId
	 * @return
	 */
	int insertUserResource(@Param("userId") String userId, @Param("resourceId") long resourceId);

	/**
	 * 
	 * @param directoryId
	 * @return
	 */
	List<Resource> findByDirectoryId(long directoryId);

	/**
	 * 资源转让
	 * @param resourceId
	 * @param userId
	 * @return
	 */
	@Update("update resource set owner=#{userId} where id=#{resourceId} ")
	int updateOwner(@Param("resourceId") Long resourceId, @Param("userId") String userId);

	/**
	 * 只修改文档的资源字段修改
	 * @param id
	 * @param date
	 * @param userId
	 * @return
	 */
	@Update("update resource set update_time=#{date},update_user=#{userId} where id=#{id}")
	int updateAlone(@Param("id") long id, @Param("date") Date date, @Param("userId") String userId);

	int publicResource(Long id);

	void deleteUserResourceByResouceId(Long resourceId);

	int selfResource(Long id);

	List<Resource> findByUserId(String userId);

}
