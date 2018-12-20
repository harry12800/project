package cn.harry12800.vchat.tasks;

import java.io.IOException;

import cn.harry12800.j2se.utils.HttpUtil;

/**
 * Created by harry12800 on 15/06/2017.
 */
public class UploadTask {
	UploadTaskCallback listener;

	public UploadTask(UploadTaskCallback listener) {
		this.listener = listener;
	}

	public void execute(String url, String type, byte[] part) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (HttpUtil.upload(url, type, part)) {
						if (listener != null) {
							listener.onTaskSuccess();
						}
					} else {
						if (listener != null) {
							listener.onTaskError();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
