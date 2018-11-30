package cn.harry12800.vchat.tasks;

import cn.harry12800.vchat.utils.HttpUtil;

/**
 * Created by harry12800 on 16/06/2017.
 */
public class DownloadTask extends HttpTask {
	HttpUtil.ProgressListener progressListener;

	public DownloadTask(HttpUtil.ProgressListener progressListener) {
		this.progressListener = progressListener;
	}

	public void execute(String url) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					byte[] data = HttpUtil.download(url, null, null, progressListener);
					if (listener != null) {
						listener.onSuccess(data);
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onFailed();
					}
				}
			}
		}).start();
	}
}
