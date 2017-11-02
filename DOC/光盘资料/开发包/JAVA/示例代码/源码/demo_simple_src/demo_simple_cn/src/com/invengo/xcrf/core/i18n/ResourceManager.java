package com.invengo.xcrf.core.i18n;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * @author zxl672
 */
public class ResourceManager {

	/**
	 * 监听器列表
	 */
	LinkedList<LanguageChangeListener> listeners = new LinkedList<LanguageChangeListener>();

	/**
	 * 特定语言环境资源包对象
	 */
	ResourceBundle bundle;

	/**
	 * 本土语言环境
	 */
	Locale locale;

	/**
	 * ResourceBundle文件
	 */
	private static final String RESOURCE_NAME = "resswing";

	/**
	 * 使用Singleton使得ResourceManager在程序里只有一个实例
	 */
	private static ResourceManager _instance = new ResourceManager();

	public static ResourceManager getInstance() {
		return _instance;
	}

	/**
	 * 更换语言显示
	 * 
	 * @param lang
	 */
	public void changeLang(String lang) {
		// 1 换bundle
		if (lang.equalsIgnoreCase("zh_CN")) {
			locale = Locale.CHINA;
//			XcrfInstruction.load("cn");
		} else {
			locale = Locale.US;
//			XcrfInstruction.load(lang);
		}
		// 2 通知listeners
		Iterator<LanguageChangeListener> ite = listeners.iterator();
		while (ite.hasNext()) {
			(ite.next()).updateResource();
		}
	}

	/**
	 * 取得显示字符串
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return getBundle().getString(key);
	}

	/**
	 * 取得设置后的语言版本，如果没有设置，则使用默认值Local.US
	 * 
	 * @return
	 * @uml.property name="bundle"
	 */
	public ResourceBundle getBundle() {

		if (locale == null)
			locale = Locale.CHINA;

		bundle = ResourceBundle.getBundle(RESOURCE_NAME, locale);
		return bundle;
	}

	/**
	 * 增加监听器
	 * 
	 * @param lsn
	 */
	public void addListener(LanguageChangeListener lsn) {
		listeners.add(lsn);
	}
	
	
	public static void main(String[] args) {
		
		ResourceBundle bundle = ResourceManager.getInstance().getBundle();
		System.out.println(bundle);
		
	}

}
