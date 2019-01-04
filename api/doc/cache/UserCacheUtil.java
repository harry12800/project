package cn.harry12800.api.doc.cache;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;

import cn.harry12800.api.util.CharacterParser;
import cn.harry12800.db.entity.FingerChatUser;

public class UserCacheUtil {

	static List<FingerChatUser> users = Lists.newCopyOnWriteArrayList();

	public static void cache(List<FingerChatUser> users) {
		for (FingerChatUser user : users) {
			UserCacheUtil.users.add(user);
		}
		Collections.sort(UserCacheUtil.getUsers(), new Comparator<FingerChatUser>() {
			@Override
			public int compare(FingerChatUser o1, FingerChatUser o2) {
				String tc = CharacterParser.getSelling(o1.getNickName()).toUpperCase();
				String oc = CharacterParser.getSelling(o2.getNickName()).toUpperCase();
				return tc.compareTo(oc);
			}

		});
	}

	public static List<FingerChatUser> getUsers() {
		return UserCacheUtil.users;
	}

	public static void addUser(FingerChatUser user) {
		if (UserCacheUtil.users.contains(user))
			return;
		UserCacheUtil.users.add(user);
		Collections.sort(UserCacheUtil.getUsers(), new Comparator<FingerChatUser>() {
			@Override
			public int compare(FingerChatUser o1, FingerChatUser o2) {
				String tc = CharacterParser.getSelling(o1.getNickName()).toUpperCase();
				String oc = CharacterParser.getSelling(o2.getNickName()).toUpperCase();
				return tc.compareTo(oc);
			}

		});
	}

	static class AlphabetUser {
		public String alphabet;
		public List<FingerChatUser> users = Lists.newArrayList();
	}

	public static List<AlphabetUser> getSpellUsers() {
		List<AlphabetUser> lists = Lists.newArrayList();
		String lastChara = "";
		for (FingerChatUser user : UserCacheUtil.getUsers()) {
			String ch = CharacterParser.getSelling(user.getNickName()).substring(0, 1).toUpperCase();
			if (!ch.equals(lastChara)) {
				lastChara = ch;
				AlphabetUser alphabetUser = new AlphabetUser();
				alphabetUser.alphabet = ch;
				alphabetUser.users.add(user);
				lists.add(alphabetUser);
				//positionMap.put(index, ch);
			} else {
				lists.get(lists.size() - 1).users.add(user);
			}
		}
		return lists;
	}
}
