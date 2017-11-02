package com.invengo.xcrf.core.event;

/**
 * 连接监听器
 * 
 * @author zxl672
 * 
 */
public interface IConnLis {
	/**
	 * 打开连接
	 * 
	 */
	void isConn();

	/**
	 * 关闭连接
	 * 
	 */
	void isCloseConn();
}
