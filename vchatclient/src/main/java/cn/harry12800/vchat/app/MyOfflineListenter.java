package cn.harry12800.vchat.app;

import cn.harry12800.j2se.dialog.MessageDialog;
import cn.harry12800.lnk.client.OfflineListenter;
import cn.harry12800.vchat.frames.LoginFrame;
import cn.harry12800.vchat.frames.MainFrame;

public class MyOfflineListenter extends OfflineListenter {

	@Override
	public void exe() {
		System.out.println("请求重连！");
		if (Launcher.currentUser == null) {
			new MessageDialog(LoginFrame.getContext(), "提示", "网络断开重连！");
		} else {
			MainFrame.getContext().reLogin();
			//new MessageDialog(MainFrame.getContext(), "提示", "请求重连！");
		}
		//		Launcher.client.init(this);
	}

}
