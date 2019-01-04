package cn.harry12800.vchat.utils;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.harry12800.vchat.db.service.TableService;

public class DbUtils {
	private static Logger LOG = LoggerFactory.getLogger(DbUtils.class);
	private static SqlSession sqlSession = null;
	static {
		try {
			Reader reader = Resources.getResourceAsReader("mybatis.xml");
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			SqlSessionManager newInstance = SqlSessionManager.newInstance(sqlSessionFactory);
//			sqlSession = sqlSessionFactory.openSession(true);
			sqlSession = newInstance;
			reader.close();
			checkTable();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private DbUtils() {

	}

	public static SqlSession getSqlSession() {
		return sqlSession;
	}

	private static void checkTable() {
		SqlSession session = DbUtils.getSqlSession();

		TableService tableService = new TableService(session);
		if (!tableService.exist("current_user")) {
			LOG.info("创建表 current_user");
			tableService.createCurrentUserTable();

			if (!tableService.exist("room")) {
				LOG.info("创建表 room");
				tableService.createRoomTable();
			}
			if (!tableService.exist("message")) {
				LOG.info("创建表 message");
				tableService.createMessageTable();
			}
			if (!tableService.exist("file_attachment")) {
				LOG.info("创建表 file_attachment");
				tableService.createFileAttachmentTable();
			}
			if (!tableService.exist("image_attachment")) {
				LOG.info("创建表 image_attachment");
				tableService.createImageAttachmentTable();
			}
			if (!tableService.exist("contacts_user")) {
				LOG.info("创建表 contacts_user");
				tableService.createContactsUserTable();
			}
			if (!tableService.exist("muc_info")) {
				LOG.info("创建表 muc_info");
				tableService.createMucInfoTable();
			}
		}
	}
}
