package com.invengo.xcrf.core.event;

/**
 * 读写标签数据监听器
 * 
 * @author zxl672
 * 
 */
public interface IReadOrWriteUserDataEvent {

	/**
	 * 点击按钮事件
	 * 
	 * @param type
	 * @param id
	 */
	void clickButton(int type, String id);
	
}
