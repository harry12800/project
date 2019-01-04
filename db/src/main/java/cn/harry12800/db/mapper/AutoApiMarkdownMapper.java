/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import cn.harry12800.db.entity.AutoApiMarkdown;

/**
 * 自动拼接接口文档Mapper
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 * <dt>代码自动生成!数据库的资源文件.
 */

public interface AutoApiMarkdownMapper { //extends CrudDao<AutoApiMarkdown> {
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
	AutoApiMarkdown findById(Long id);

	/**
	* 删除多行数据
	**/
	List<AutoApiMarkdown> findAll();

	/**
	* 保存数据
	*/
	int save(AutoApiMarkdown t);

	/**
	* 更新数据，通过id 修改所有字段属性
	*/
	int update(AutoApiMarkdown t);

	List<AutoApiMarkdown> findByIds(@Param("autoApiMarkdownIds") String autoApiMarkdownIds);

	/**
	 * 单独修改前缀
	 * @param prefix
	 * @param id
	 * @return
	 */
	@Update("update auto_api_markdown set prefix_content=#{prefix} where id=#{id}")
	int updatePrefix(@Param("prefix") String prefix, @Param("id") long id);

	/**
	 * 单独修改后缀
	 * @param prefix
	 * @param id
	 * @return
	 */
	@Update("update auto_api_markdown set suffix_content=#{suffix} where id=#{id}")
	int updateSuffix(@Param("suffix") String suffix, @Param("id") long id);

	List<AutoApiMarkdown> search(@Param("content") String content);

}
