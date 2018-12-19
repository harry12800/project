package cn.harry12800.vchat.app.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.harry12800.vchat.app.config.Contants;
import cn.harry12800.vchat.panels.ChatPanel;
import cn.harry12800.vchat.panels.RoomsPanel;
import cn.harry12800.vchat.utils.HttpUtil;

public class PullWebInfo {
	
	public static void pull() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					String path2 = Contants.getPath(Contants.websocketPath + "?userName=harry12800");
					String string = null;
					try {
						string = HttpUtil.get(path2);
					} catch (IOException e) {
						//e.printStackTrace();
					}
//					System.out.println(string);
					MyResponse string2Json = JsonUtils.string2Json(string, MyResponse.class);
					List<String> content = string2Json.content;
					Map<String,List<Letter>> maps = new LinkedHashMap<>();
					for (String letterStr : content) {
						Letter letter = JsonUtils.string2Json(letterStr, Letter.class);
						if(letter.from.equals("harry12800")) {
							if(maps.get(letter.to)==null) {
								maps.put(letter.to, new ArrayList<Letter>());
							}
							maps.get(letter.to).add(letter);
							RoomsPanel.getContext().addWebSocketRoom(letter.to);
							ChatPanel.getContext().showWebSocketMsg(letter);
						}else {
							if(maps.get(letter.from)==null) {
								maps.put(letter.from, new ArrayList<Letter>());
							}
							maps.get(letter.from).add(letter);
							RoomsPanel.getContext().addWebSocketRoom(letter.from);
							ChatPanel.getContext().showWebSocketMsg(letter);
						}
					}
				}catch (Exception e) {
//					e.printStackTrace();
				}
			}
		}, 5000, 2000);
	}
	public static class MyResponse {
		public int code;
		public String msg;
		public List<String> content;
		@Override
		public String toString() {
			return "MyResponse [code=" + code + ", msg=" + msg + ", content=" + content + "]";
		}
		
	}
	public static class Letter {
		public String from;
		public String to;
		public Date time;
		public String data;

		@Override
		public String toString() {
			return "Letter [from=" + from + ", to=" + to + ", time=" + time + ", data=" + data + "]";
		}

	}

}
