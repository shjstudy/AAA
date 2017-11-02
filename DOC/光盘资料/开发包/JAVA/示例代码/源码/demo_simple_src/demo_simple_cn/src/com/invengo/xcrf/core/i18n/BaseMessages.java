package com.invengo.xcrf.core.i18n;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * BaseMessage is called by all Message classes to enable the delegation of
 * message delivery, by key to be delegated to the appropriately authoritative
 * supplier as registered in the LAFFactory enabling both i18n as well as
 * pluggable look and feel (LAF)
 * 
 * @author
 * 
 */
public class BaseMessages {

	/**
	 * 监听器列表
	 */
	private static LinkedList<LanguageChangeListener> listeners = new LinkedList<LanguageChangeListener>();

	static BaseMessages instance = null;
	protected MessageHandler handler = null;
	Class<MessageHandler> clazz = MessageHandler.class;

	static {
		getInstance();
	}

	private BaseMessages() {
		init();
	}

	private void init() {

		handler = GlobalMessages.getInstance();

	}

	public static BaseMessages getInstance() {
		if (instance == null) {
			instance = new BaseMessages();
		}
		return instance;
	}

	protected MessageHandler getHandler() {
		return handler;
	}

	protected static MessageHandler getInstanceHandler() {
		return getInstance().getHandler();
	}

	public static String getString(String key) {
		return getInstanceHandler().getString(key);
	}

	public static String getString(String packageName, String key) {
		return getInstanceHandler()
				.getString(packageName, key, new String[] {});
	}

	public static String getString(String packageName, String key,
			String... parameters) {
		return getInstanceHandler().getString(packageName, key, parameters);
	}

	public static String getString(String packageName, String key,
			Class<?> resourceClass, String... parameters) {
		return getInstanceHandler().getString(packageName, key, resourceClass,
				parameters);
	}

	public static String getString(Class<?> packageClass, String key,
			String... parameters) {
		return getInstanceHandler().getString(
				packageClass.getPackage().getName(), key, packageClass,
				parameters);
	}

	public static String getString(Class<?> packageClass, String key,
			Class<?> resourceClass, String... parameters) {
		return getInstanceHandler().getString(
				packageClass.getPackage().getName(), key, packageClass,
				parameters);
	}

	public static String getString(Class<?> packageClass, String key,
			Object... parameters) {
		String[] strings = new String[parameters.length];
		for (int i = 0; i < strings.length; i++) {
			strings[i] = parameters[i] != null ? parameters[i].toString() : "";
		}
		return getString(packageClass, key, strings);
	}

	public static String[] getStrings(String key) {
		String data = getString(key);
		if (data != null && data.contains("|")) {
			return data.split("\\|");
		}
		return null;
	}

	/**
	 * 增加监听器
	 * 
	 * @param langLsn
	 */
	public static void addListener(LanguageChangeListener langLsn) {
		listeners.add(langLsn);
	}

	public void changeLang() {
		Iterator<LanguageChangeListener> ite = listeners.iterator();
		while (ite.hasNext()) {
			(ite.next()).updateResource();
		}
	}

}
