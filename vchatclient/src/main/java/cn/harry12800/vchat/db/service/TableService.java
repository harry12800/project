package cn.harry12800.vchat.db.service;

import org.apache.ibatis.session.SqlSession;

import cn.harry12800.vchat.db.dao.TableDao;

/**
 * Created by harry12800 on 08/06/2017.
 */
public class TableService {
	private TableDao dao;

	public TableService(SqlSession session) {
		dao = new TableDao(session);
	}

	public void createCurrentUserTable() {
		dao.createCurrentUserTable();
	}

	public boolean exist(String name) {
		return dao.exist(name);
	}

	public void createRoomTable() {
		dao.createRoomTable();
	}

	public void createMessageTable() {
		dao.createMessageTable();
	}

	public void createFileAttachmentTable() {
		dao.createFileAttachmentTable();
	}

	public void createImageAttachmentTable() {
		dao.createImageAttachmentTable();
	}

	public void createContactsUserTable() {
		dao.createContactsUserTable();
	}

	public void createMucInfoTable() {
		dao.createMucInfoTable();
	}
}
