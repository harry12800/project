/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.entity;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://120.78.177.24:3306/scan?useSSL=false&useUnicode=true&characterEncoding=utf-8
 * <dt>scan
 * <dt>Zhouguozhu@123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "int64_sequence")
public class Int64Sequence { // extends DataEntity<Int64Sequence> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 序列名称
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "sequence_name")
 	private String	sequenceName;
	/**
	 * 序列值
	 */
//	@DbField(value="序列值",type=1,sort=1, title ="序列值", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "sequence_value")
 	private Long	sequenceValue;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE int64_sequence("+
		"	sequence_name		VARCHAR(255)  COMMENT '序列名称',"+
		"	sequence_value		INT  COMMENT '序列值',"+
		"	PRIMARY KEY(sequence_name)"+
		");";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE int64_sequence("+
		"	sequence_name		VARCHAR2(255) ,"+
		"	sequence_value		NUMBER ,"+
		"	PRIMARY KEY(sequence_name)"+
		");";
	
	
	/**
	 *获取序列名称
	 */
 	public  String	getSequenceName() {
 		return sequenceName;
 	}
	
	/**
	 * 设值序列名称
	 */
 	public void	setSequenceName(String sequenceName) {
 		this.sequenceName = sequenceName;
 	}
	
	
	
	/**
	 *获取序列值
	 */
 	public  Long	getSequenceValue() {
 		return sequenceValue;
 	}
	
	/**
	 * 设值序列值
	 */
 	public void	setSequenceValue(Long sequenceValue) {
 		this.sequenceValue = sequenceValue;
 	}
}
	