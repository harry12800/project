package cn.harry12800.vchat.tasks;

import java.io.IOException;

import org.json.JSONObject;

import cn.harry12800.vchat.utils.HttpUtil;

/**
 * Created by harry12800 on 08/06/2017.
 */
public class HttpPostTask extends HttpTask {

	@Override
	public void execute(String url) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String ret = HttpUtil.post(url, headers, requestParams);
					JSONObject retJson = new JSONObject(ret);
					if (listener != null) {
						listener.onSuccess(retJson);
					}
				} catch (IOException e) {
					if (listener != null) {
						listener.onFailed();
					}
				}

			}
		}).start();

	}
}
