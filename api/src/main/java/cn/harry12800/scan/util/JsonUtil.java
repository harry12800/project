package cn.harry12800.scan.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import cn.harry12800.tools.FileUtils;
import cn.harry12800.tools.StringUtils;

public class JsonUtil {
	static Gson gson = new GsonBuilder().registerTypeAdapterFactory(new DateNullAdapterFactory<Date>()).create();

	public static <T> T string2Json(String jsonData, Class<T> type) {
		T result = gson.fromJson(jsonData, type);
		return result;
	}

	// 将Json数据解析成相应的映射对象
	public static <T> T string2Json(File file, Class<T> type) throws Exception {
		T result = gson.fromJson(FileUtils.getSrcByFilePath(file.getAbsolutePath(), "utf-8"), type);
		return result;
	}

	// 将Json数据解析成相应的映射对象
	public static String object2String(Object obj) {
		String result = gson.toJson(obj);
		return result;
	}

	public static void save(String json, String configPath) {
		FileUtils.writeContent(configPath, FormatUtil.formatJson(json), "utf-8");
	}

	public static void saveObj(Object obj, String configPath) {
		save(object2String(obj), configPath);
	}

	public static String readJsonConfig(File file) {
		StringBuffer sb = new StringBuffer();
		String line;
		try (FileInputStream in = new FileInputStream(file);
				InputStreamReader read = new InputStreamReader(in, "utf-8");
				BufferedReader reader = new BufferedReader(read);) {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	// 将Json数据解析成相应的映射对象
	public static class DateNullAdapterFactory<T> implements TypeAdapterFactory {

		@SuppressWarnings({ "unchecked", "hiding" })
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
			Class<T> rawType = (Class<T>) type.getRawType();
			if (rawType != Date.class) {
				return null;
			}
			return (TypeAdapter<T>) new DateNullAdapter();
		}
	}

	public static class DateNullAdapter extends TypeAdapter<Date> {

		public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
			@SuppressWarnings("unchecked") // we use a runtime check to make sure the 'T's equal
			@Override
			public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
				return typeToken.getRawType() == Date.class ? (TypeAdapter<T>) new DateTypeAdapter() : null;
			}
		};

		private final DateFormat enUsFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT,
				Locale.US);
		private final DateFormat localFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT);
		private final DateFormat standFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

		@Override
		public Date read(JsonReader in) throws IOException {

			if (in.peek() == JsonToken.NULL) {
				in.nextNull();
				return null;
			}
			String jsonStr = in.nextString();
			if (StringUtils.isBlank(jsonStr)) {
				return null;
			} else {
				return deserializeToDate(jsonStr);
			}
		}

		private synchronized Date deserializeToDate(String json) {
			try {
				Long valueOf = Long.valueOf(json);
				return new Date(valueOf);
			} catch (Exception e) {
			}
			try {
				return standFormat.parse(json);
			} catch (ParseException ignored) {
			}
			try {
				return localFormat.parse(json);
			} catch (ParseException ignored) {
			}
			try {
				return enUsFormat.parse(json);
			} catch (ParseException ignored) {
			}
			try {
				return ISO8601Utils.parse(json, new ParsePosition(0));
			} catch (ParseException e) {
				throw new JsonSyntaxException(json, e);
			}
		}

		@Override
		public synchronized void write(JsonWriter out, Date value) throws IOException {
			if (value == null) {
				out.nullValue();
				return;
			}
			String dateFormatAsString = standFormat.format(value);
			out.value(dateFormatAsString);
		}

	}
}