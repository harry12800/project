package cn.harry12800.scan.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import cn.harry12800.api.SpringUtil;
import cn.harry12800.api.doc.cache.MarkDownCacheUtil;
import cn.harry12800.api.doc.cache.UserCacheUtil;
import cn.harry12800.api.doc.swagger2markdown.ProjectApiFactory;
import cn.harry12800.db.entity.Application;
import cn.harry12800.db.entity.FingerChatUser;
import cn.harry12800.db.mapper.FingerChatUserMapper;

public class ApplicationReadyEventListenerImpl implements ApplicationListener<ApplicationReadyEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationReadyEventListenerImpl.class);

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		FingerChatUserMapper userMapper = SpringUtil.getBean(FingerChatUserMapper.class);
//		ApplicationMapper applicationMapper = SpringUtil.getBean(ApplicationMapper.class);
		List<FingerChatUser> users = userMapper.findAll();
		UserCacheUtil.cache(users);
//		List<Application> applications = applicationMapper.findAll();
//		for (Application application : applications) {
//			if (application.getAppType() == 1) {
//				analysisProject(application);
//			}
//		}

	}

	public void analysisProject(Application application) {
		ProjectApiFactory client = new ProjectApiFactory();
		try {
			client.setApp(application).go();
			MarkDownCacheUtil.cacheProjectApi(application, client);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

}