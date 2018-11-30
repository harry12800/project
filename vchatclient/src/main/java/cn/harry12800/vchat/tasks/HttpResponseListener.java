package cn.harry12800.vchat.tasks;

/**
 * Created by harry12800 on 08/06/2017.
 */
public interface HttpResponseListener<T extends Object> {
	void onSuccess(T ret);

	void onFailed();
}
