package cn.harry12800.vchat.db.dao;

import org.apache.ibatis.session.SqlSession;

/**
 * Created by harry12800 on 09/06/2017.
 */
public class ImageAttachmentDao extends BasicDao {
	public ImageAttachmentDao(SqlSession session) {
		super(session, ImageAttachmentDao.class);
	}
}
