/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import cn.harry12800.db.entity.FileServer;
/**
 * Mapper
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://120.78.177.24:3306/scan?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Zhouguozhu@123
 * <dt>代码自动生成!数据库的资源文件.
 */

public interface FileServerMapper { //extends CrudDao<FileServer> {
	static final long serialVersionUID = 1L;
	
	/**
	* 查询所有数据
	**/
	List<FileServer> findAll();
 	/**
	 * 查找一行数据
	 * @param id
	 * @return
	 */
	FileServer findById(String id);
	
	/**
	 * 删除单行数据 主键
	 * @param id
	 * @return
	 */
 	int deleteById(String id);
	
	/**
	* 查询多行数据
	**/
	List<FileServer> findByIds(@Param("ids")Set<?> ids);
	/**
	* 是否存在，存在>0
	*/
	int exist(FileServer t);
	/**
	* 保存数据
	*/
	int save(FileServer t);

	/**
	* 更新数据，通过id 修改所有字段属性
	*/
	int update(FileServer t);
	/**
	* 更新数据，通过id 修改非空字段属性
	*/
	int updateNotNull(FileServer t);
	/**
	* 统计条数
	*/
	long countAll();
	/**
	*分页
	*/
	List<FileServer> pageList(@Param("index") int index,@Param("pageSize") int pageSize);
	/**
	* 通过多个id删除多行数据
	*/
	int deleteByIds(@Param("ids")Set<?> ids);
}
	