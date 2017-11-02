package com.invengo.xcrf.core.event;

/**
 * ¶Á±êÇ©¼àÌýÆ÷
 * @author zxl672
 *
 */
public interface IReadEvent {
	/**
	 * ¿ªÊ¼¶Á
	 *
	 */
	void isRead();

	/**
	 * Í£Ö¹¶Á
	 *
	 */
	void isStopRead();
	
	/**
	 * Çå³ý
	 *
	 */
	void clear();
}
