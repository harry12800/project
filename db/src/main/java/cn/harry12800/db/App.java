package cn.harry12800.db;

import java.util.List;

import cn.harry12800.dbhelper.entity.DBTable;
import cn.harry12800.developer.DeveloperUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	String url =  "jdbc:mysql://120.78.177.24:3306/scan?useSSL=false&useUnicode=true&characterEncoding=utf-8";
		String userName="scan";
		String pwd="Zhouguozhu@123";
		List<DBTable> dbTable = DeveloperUtils.getDBTable(url, userName, pwd);
		for (DBTable dbTable2 : dbTable) {
			System.out.println(dbTable2.getName());
			DeveloperUtils.generateDbEntityByTableNameUseFreemarker("cn.harry12800", "db", url, userName, pwd, "scan", dbTable2.getName());
		}
		
    }
}
