package cn.harry12800.vchat.app;

import java.io.File;

import cn.harry12800.tools.MachineUtils;
import cn.harry12800.vchat.panels.DiaryCatalogPanel;
import cn.harry12800.vchat.scaner.Scaner;

/**
 * Created by harry12800 on 17-5-28.
 */
public class App {
	public static String basePath = getHomePath();

	public static void main(String[] args) throws Exception {
		Scaner.getInvoker();
		Launcher launcher = new Launcher();
		launcher.launch();
	}

	public static String getHomePath() {
		boolean byClass = MachineUtils.getByClass(DiaryCatalogPanel.class);
		String homePath = "";
		if (byClass) {
			String clazz = System.getProperty("sun.java.command");
			System.err.println("sun.java.command: " + clazz);
			File file = new File(clazz);
			// MachineUtils.printSystemProperties();
			if (file.exists()) {
				File file2 = new File(file.getAbsolutePath());
				File parentFile = file2.getParentFile();
				homePath = parentFile.getAbsolutePath();
			} else {
				homePath = System.getProperty("user.dir");
			}
		} else {
			homePath = System.getProperty("user.dir");
		}
		return homePath;
	}

}
