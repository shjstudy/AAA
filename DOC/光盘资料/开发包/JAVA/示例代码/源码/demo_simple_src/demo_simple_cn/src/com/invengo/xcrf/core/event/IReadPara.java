package com.invengo.xcrf.core.event;

/**
 * 取得连接参数接口
 * 
 * @author zxl672
 * 
 */
public interface IReadPara {

	/**
	 * 取得读的类型
	 * 
	 * @return
	 */
	int getReadType();

	/**
	 * 取得Q值
	 * 
	 * @return
	 */
	int getQvalue();

	/**
	 * 取得天线号
	 * 
	 * @return
	 */
	int getAntNo();
	
	boolean isOpenBeep();
}
