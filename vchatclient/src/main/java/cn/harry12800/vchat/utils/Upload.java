package cn.harry12800.vchat.utils;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Upload {
	public Upload() {
		OkHttpClient client = new OkHttpClient();

		//一种：参数请求体
		FormBody paramsBody = new FormBody.Builder()
				.add("branchCode", "PAAS01")
				.build();

		//二种：文件请求体
		MediaType type = MediaType.parse("application/octet-stream");//"text/xml;charset=utf-8"
		File file = new File("C:\\Users\\admin\\Desktop\\A.txt");
		RequestBody fileBody = RequestBody.create(type, file);

		//三种：混合参数和文件请求
		RequestBody multipartBody = new MultipartBody.Builder()
				.setType(MultipartBody.ALTERNATIVE)
				//一样的效果
				.addPart(Headers.of("Content-Disposition",
						"form-data; name=\"branchCode\""), paramsBody)
				.addPart(Headers.of("Content-Disposition",
						"form-data; name=\"file\"; filename=\"A.txt\""), fileBody)
				//一样的效果
//				.addFormDataPart("path", "/root/FileZilla_3.27.0.1_win64.zip")
				/*.addFormDataPart("name",currentPlan.getName())
				.addFormDataPart("volume",currentPlan.getVolume())
				.addFormDataPart("type",currentPlan.getType()+"")
				.addFormDataPart("mode",currentPlan.getMode()+"")
				.addFormDataPart("params","plans.xml",fileBody)*/
				.build();
		//http://open.teewon.net:9000/fs/media/
		Request request = new Request.Builder().url("http://open.teewon.net:9000/twasp/fs/fs/file/upload")
				.addHeader("User-Agent", "android")
				.header("Content-Type", "text/html; charset=utf-8;")
				.post(multipartBody)//传参数、文件或者混合，改一下就行请求体就行
				.build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				System.out.println("失败！");
			}
			@Override
			public void onResponse(Call call, Response response) throws IOException {

				System.out.println("1、连接的消息" + response.message());
				if (response.isSuccessful()) {
					System.out.println("2、连接成功获取的内容" + response.body().string());
				}
			}
		});
	}
	public static void main(String[] args) {
		new Upload();
	}
}
