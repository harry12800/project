package cn.harry12800.vchat.model.diary;

import java.util.List;

import cn.harry12800.j2se.utils.Speak;
import cn.harry12800.tools.StringUtils;

public class BaiduReader extends Thread {

	public boolean isread = true;
	private String content;

	public BaiduReader(String content) {
		this.content = content;
	}

	@Override
	public void run() {
		List<String> split = StringUtils.split(content);
		for (int i = 0; i < split.size(); i++) {
			try {
				if (isread)
					Speak.speak1(split.get(i));
				else {
					break;
				}
			} catch (Exception e) {
				i--;
			}
		}
		isread = false;
	}

	public void setReadStatus(boolean b) {
		isread = b;
	}

}
