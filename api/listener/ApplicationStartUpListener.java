package cn.harry12800.api.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import cn.harry12800.api.SpringUtil;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.db.mapper.UserInfoMapper;

public class ApplicationStartUpListener implements ApplicationListener<ApplicationReadyEvent> {

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		UserInfoMapper bean = SpringUtil.getBean(UserInfoMapper.class);
		UserInfo findById = bean.findById(1L);
		System.out.println(findById);
	}
}