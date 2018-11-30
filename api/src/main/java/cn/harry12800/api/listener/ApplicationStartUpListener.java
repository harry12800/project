package cn.harry12800.api.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import cn.harry12800.api.SpringUtil;
import cn.harry12800.db.entity.User;
import cn.harry12800.db.mapper.UserMapper;

public class ApplicationStartUpListener implements ApplicationListener<ApplicationReadyEvent> {

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		UserMapper bean = SpringUtil.getBean(UserMapper.class);
		User userById = bean.getUserById(1L);
		System.out.println(userById);
	}
}