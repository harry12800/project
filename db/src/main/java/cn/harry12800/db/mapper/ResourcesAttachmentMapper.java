/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import cn.harry12800.db.entity.ResourcesAttachment;
/**
 * 上传其他类型文件Mapper
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://120.78.177.24:3306/docs?useSSL=false&useUnicode=true&characterEncoding=utf-8
 * <dt>scan
 * <dt>Zhouguozhu@123
 * <dt>代码自动生成!数据库的资源文件.
 */

public interface ResourcesAttachmentMapper { //extends CrudDao<ResourcesAttachment> {
	static final long serialVersionUID = 1L;
	
	/**
	* 通过多个id删除多行数据
	* 字符串ids的样子 eg:  `id in ('a','b')`  数字型的是  `id in (a,b)`
	*/
	int deleteByIds(Set<?> ids);
	
	/**
	 * 删除单行数据 
	 * @param id
	 * @return
	 */
 	int deleteById(Long id);
 	/**
	 * 查找一行数据
	 * @param id
	 * @return
	 */
	ResourcesAttachment findById(Long id);
	
	/**
	* 查询根据字段名称和数据
	**/
	List<ResourcesAttachment> findByParam(@Param("propName") String propName,@Param("value") Object value);
	/**
	* 删除多行数据
	**/
	List<ResourcesAttachment> findAll();
	/**
	* 查询多行数据
	**/
	List<ResourcesAttachment> findByIds(Set<?> set);
	/**
	* 保存数据
	*/
	int save(ResourcesAttachment t);

	/**
	* 更新数据，通过id 修改所有字段属性
	*/
	int update(ResourcesAttachment t);
	
}
	