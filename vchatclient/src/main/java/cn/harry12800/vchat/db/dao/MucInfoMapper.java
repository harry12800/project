/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.vchat.db.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import cn.harry12800.vchat.db.model.MucInfo;
/**
 * Mapper
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://120.78.177.24:3306/im?useSSL=false&useUnicode=true&characterEncoding=utf-8
 * <dt>scan
 * <dt>Zhouguozhu@123
 * <dt>代码自动生成!数据库的资源文件.
 */

public class MucInfoMapper extends BasicDao {
	public MucInfoMapper(SqlSession session) {
		super(session, MucInfoMapper.class);
	}

	static final long serialVersionUID = 1L;
	
 	/**
	 * 查找一行数据
	 * @param id
	 * @return
	 */
	MucInfo findByMucId(Long mucId){
		return null;};
	
	/**
	 * 删除单行数据 MUC群ID
	 * @param id
	 * @return
	 */
 	int deleteByMucId(Long mucId){
		return (Integer) null;};
		
	/**
	* 查询多行数据
	**/
	List<MucInfo> findByIds(@Param("ids")Set<?> ids){
		return null;};
		
	/**
	* 保存数据
	*/
	int save(MucInfo t){
		return (Integer) null;};
		

	/**
	* 更新数据，通过id 修改所有字段属性
	*/
	int update(MucInfo t){
		return (Integer) null;};
		
	/**
	* 更新数据，通过id 修改非空字段属性
	*/
	int updateNotNull(MucInfo t){
		return (Integer) null;};
		
	
	/**
	* 通过多个id删除多行数据
	* 字符串ids的样子 eg:  `id in ('a','b')`  数字型的是  `id in (a,b)`
	*/
	int deleteByIds(@Param("ids")Set<?> ids){
		return (Integer) null;};
		
}
	