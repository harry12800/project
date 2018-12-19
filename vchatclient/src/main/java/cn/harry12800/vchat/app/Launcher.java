package cn.harry12800.vchat.app;

import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.ibatis.session.SqlSession;

import cn.harry12800.common.module.user.dto.UserResponse;
import cn.harry12800.j2se.component.utils.ImageUtils;
import cn.harry12800.tools.Maps;
import cn.harry12800.vchat.client.Client;
import cn.harry12800.vchat.client.OfflineListenter;
import cn.harry12800.vchat.db.model.CurrentUser;
import cn.harry12800.vchat.db.service.ContactsUserService;
import cn.harry12800.vchat.db.service.CurrentUserService;
import cn.harry12800.vchat.db.service.FileAttachmentService;
import cn.harry12800.vchat.db.service.ImageAttachmentService;
import cn.harry12800.vchat.db.service.MessageService;
import cn.harry12800.vchat.db.service.RoomService;
import cn.harry12800.vchat.frames.LoginFrame;
import cn.harry12800.vchat.frames.MainFrame;
import cn.harry12800.vchat.utils.DbUtils;

/**
 * Created by harry12800 on 09/06/2017.
 */
public class Launcher {
	private static Launcher context;

	public static SqlSession sqlSession;
	public static RoomService roomService;
	public static CurrentUserService currentUserService;
	public static MessageService messageService;
	public static ContactsUserService contactsUserService;
	public static ImageAttachmentService imageAttachmentService;
	public static FileAttachmentService fileAttachmentService;

	public static final String HOSTNAME = "http://www.baidu.com";

	public static final String APP_VERSION = "1.0.0";

	public static CurrentUser currentUser;
	static {
		sqlSession = DbUtils.getSqlSession();
		roomService = new RoomService(sqlSession);
		currentUserService = new CurrentUserService(sqlSession);
		messageService = new MessageService(sqlSession);
		contactsUserService = new ContactsUserService(sqlSession);
		imageAttachmentService = new ImageAttachmentService(sqlSession);
		fileAttachmentService = new FileAttachmentService(sqlSession);
	}

	public static Client client;
	static OfflineListenter listener = new MyOfflineListenter();
	static {
		client = new Client();
		client.init(listener);
	}
	private JFrame currentFrame;

	public Launcher() {
		context = this;
		ImageUtils.addImage(Launcher.class);
	}

	public void launch() {
		openFrame();
		// if (!isApplicationRunning()) {
		// openFrame();
		// } else {
		// System.exit(-1);
		// }
	}

	private void openFrame() {
		// 原来登录过
		if (checkLoginInfo()) {
			currentFrame = MainFrame.getContext();
		}
		// 从未登录过
		else {
			currentFrame = LoginFrame.getContext();
			currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		currentFrame.setVisible(true);
	}

	/**
	 * 检查是否有登录信息
	 * 
	 * @return
	 */
	private boolean checkLoginInfo() {
		// TODO 判断是否已登录的逻辑
		return false;
	}

	/**
	 * 通过文件锁来判断程序是否正在运行
	 *
	 * @return 如果正在运行返回true，否则返回false
	 */
	// public static boolean isApplicationRunning() {
	// boolean rv = false;
	// try {
	// String path = appFilesBasePath + System.getProperty("file.separator") +
	// "appLock";
	// File dir = new File(path);
	// if (!dir.exists()) {
	// dir.mkdirs();
	// }
	//
	// File lockFile = new File(path + System.getProperty("file.separator") +
	// "appLaunch.lock");
	// if (!lockFile.exists()) {
	// lockFile.createNewFile();
	// }
	//
	// // 程序名称
	// @SuppressWarnings("resource")
	// RandomAccessFile fis = new RandomAccessFile(lockFile.getAbsolutePath(),
	// "rw");
	// FileChannel fileChannel = fis.getChannel();
	// FileLock fileLock = fileChannel.tryLock();
	// if (fileLock == null) {
	// System.out.println("程序已在运行.");
	// rv = true;
	// }
	// } catch (FileNotFoundException e1) {
	// e1.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return rv;
	// }

	public void reLogin(String username) {
		MainFrame.getContext().setVisible(false);
		MainFrame.getContext().dispose();

		currentFrame = new LoginFrame(username);
		currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		currentFrame.setVisible(true);
	}

	public static Launcher getContext() {
		return context;
	}

	public static Map<String, UserResponse> userMaps = Maps.newHashMap();
	public static Map<Long, String> iduserIdMaps = Maps.newHashMap();
	public static void loadUsers(List<UserResponse> users) {
		for (UserResponse user : users) {
			userMaps.put(user.getUserId(), user);
			iduserIdMaps.put(user.getId(), user.getUserId());
		}
	}

	public static void loadUser(UserResponse user) {
		userMaps.put(user.getUserId(), user);
	}

	public static String getUserNameByUserId(String id) {
		return userMaps.get(id) == null ? "" : userMaps.get(id).getNickName();
	}
	public static long getIdByUserId(String id) {
		return userMaps.get(id) == null ? -1L : userMaps.get(id).getId();
	}
	public static String getUserIdById(long id) {
		return iduserIdMaps.get(id) == null ? "" : iduserIdMaps.get(id);
	}
}
