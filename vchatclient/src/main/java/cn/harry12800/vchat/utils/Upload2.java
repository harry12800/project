package cn.harry12800.vchat.utils;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Upload2 {
	private static Logger LOG = LoggerFactory.getLogger(Upload2.class);

	public Upload2() {
		OkHttpClient client = new OkHttpClient();

		// 一种：参数请求体
		// FormBody paramsBody = new FormBody.Builder()
		// .add("path", "/root/FileZilla_3.27.0.1_win64.zip")
		// .build();

		// 二种：文件请求体
		// MediaType type =
		// MediaType.parse("multipart/form-data");//"text/xml;charset=utf-8"
		MediaType type = MediaType.parse("image/png");// "text/xml;charset=utf-8"
		File file = new File("C:/Users/ZR0014.HNLENS/Desktop/a.png");
		RequestBody fileBody = RequestBody.create(type, file);
		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		addFormatData(builder, file);
		// 三种：混合参数和文件请求
		// RequestBody multipartBody = new MultipartBody.Builder()
		// .setType(MultipartBody.ALTERNATIVE)
		// //一样的效果
		// .addPart(Headers.of("Content-Disposition",
		// "form-data; name=\"params\""), paramsBody)
		// .addPart(Headers.of("Content-Disposition",
		// "form-data; name=\"file\";
		// filename=\"FileZilla_3.27.0.1_win64.zip\""), fileBody)
		// //一样的效果
		// .addFormDataPart("path", "/root/FileZilla_3.27.0.1_win64.zip")
		// /*.addFormDataPart("name",currentPlan.getName())
		// .addFormDataPart("volume",currentPlan.getVolume())
		// .addFormDataPart("type",currentPlan.getType()+"")
		// .addFormDataPart("mode",currentPlan.getMode()+"")
		// .addFormDataPart("params","plans.xml",fileBody)*/
		// .build();

		MultipartBody build = builder.build();
		Request request = new Request.Builder().url("http://mobile.fingerchat.cn:8686/DFS/Image")
				.addHeader("User-Agent", "android").header("Content-Type", "multipart/form-data").post(build)// 传参数、文件或者混合，改一下就行请求体就行
				.build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				LOG.info("失败！");
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {

				LOG.info("1、连接的消息" + response.message());
				if (response.isSuccessful()) {
					LOG.info("2、连接成功获取的内容" + response.body().string());
				}
			}
		});
	}

	// private MediaType getMediaType(EUploadFileType type) {
	// switch (type) {
	// case JPG:
	// return MEDIA_TYPE_JPG;
	// case GIF:
	// return MEDIA_TYPE_GIF;
	// case VOICE:
	// return MEDIA_TYPE_MP3;
	// case VIDEO:
	// return MEDIA_TYPE_MP4;
	// default:
	// return MEDIA_TYPE_JPG;
	// }
	// }
	public static void main(String[] args) {
		new Upload2();
	}

	public static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");
	public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
	public static final MediaType MEDIA_TYPE_MP4 = MediaType.parse("video/mp4");
	public static final MediaType MEDIA_TYPE_MP3 = MediaType.parse("audio/mpeg");
	public static final MediaType MEDIA_TYPE_GIF = MediaType.parse("image/gif");

	private void addFormatData(MultipartBody.Builder builder, File file) {
		builder.addFormDataPart("photoContent", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
		// if (type == EUploadFileType.JPG) {
		// builder.addFormDataPart("photoContent", file.getName(),
		// RequestBody.create(getMediaType(type), file));
		// } else if (type == EUploadFileType.GIF) {
		// builder.addFormDataPart("photoContent", file.getName(),
		// RequestBody.create(getMediaType(type), file));
		// } else if (type == EUploadFileType.VOICE) {
		// if (file.getName().contains(".map3")) {
		// builder.addFormDataPart("photoContent", file.getName(),
		// RequestBody.create(getMediaType(type), file));
		// } else {
		// builder.addFormDataPart("photoContent", file.getName() + ".mp3",
		// RequestBody.create(getMediaType(type), file));
		// }
		// } else if (type == EUploadFileType.VIDEO) {
		// builder.addFormDataPart("photoContent", file.getName(),
		// RequestBody.create(getMediaType(type), file));
		// }
	}
}
