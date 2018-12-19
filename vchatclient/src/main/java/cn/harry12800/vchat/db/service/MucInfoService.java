package cn.harry12800.vchat.db.service;

import org.apache.ibatis.session.SqlSession;

import cn.harry12800.vchat.db.dao.MucInfoMapper;
import cn.harry12800.vchat.db.model.MucInfo;

/**
 * Created by harry12800 on 08/06/2017.
 */
public class MucInfoService extends BasicService<MucInfoMapper, MucInfo> {
	public MucInfoService(SqlSession session) {
		dao = new MucInfoMapper(session);
		super.setDao(dao);
	}

	public int insertOrUpdate(MucInfo message) {
		if (exist(message.getMucId()+"")) {
			return update(message);
		} else {
			return insert(message);
		}
	}
}
