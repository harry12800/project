package cn.harry12800.vchat.model.diary;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import cn.harry12800.tools.DateUtils;
import cn.harry12800.tools.FileUtils;
import cn.harry12800.tools.Maps;

public class Snippet {
	public static String getHolidayJson(String date) {
		String httpUrl = "https://api.goseek.cn/Tools/holiday?date=" + date;
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		try {
			// URL url = new URL(httpUrl);
			HttpsURLConnection connection = HttpsUtil.getHttpsURLConnection(httpUrl);
			// HttpURLConnection connection = (HttpURLConnection) url
			// .openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		Calendar sCalendar = Calendar.getInstance();
		sCalendar.set(Calendar.YEAR, 2018);
		sCalendar.set(Calendar.DAY_OF_YEAR, 1);
		Map<String, String> map = Maps.newLinkedHashMap();
		int i = 0;
		System.out.println(sCalendar);
		while (i < 400) {
			String timeByFormat = DateUtils.getTimeByFormat(sCalendar.getTime(), "yyyyMMdd");
			try {
				String holidayJson = getHolidayJson(timeByFormat);
				map.put(timeByFormat, holidayJson.substring(21, 22));

			} catch (Exception e) {

			}
			sCalendar.add(Calendar.DAY_OF_MONTH, 1);
			i++;
		}
		// DeveloperUtils.generateMapCode(map, Snippet.class, "holidayMap", 59);
	}

	public static void main2(String[] args) {
		List<String> rowByFile = FileUtils.getRowByFile(new File("D:/desktop/a.txt"), "utf-8");
		Collections.sort(rowByFile);
		for (String string : rowByFile) {
			System.out.println(string);
		}
	}
}
