package com.invengo.xcrf.core;

import invengo.javaapi.core.APIPath;

import java.util.Locale;

/**
 * This class is used to define a number of default values for various settings
 * throughout Kettle. It also contains a number of static final methods to make
 * your life easier.
 * 
 * @author
 * @since
 * 
 */
public class Const {
	public final static String fn = APIPath.folderName + "Sysit.xml";

	public static final String FILE_SEPARATOR = System
			.getProperty("file.separator");

	public static final Locale DEFAULT_LOCALE = Locale.getDefault();
	public static final String XCRF_IMAGE_PATH = "image" + FILE_SEPARATOR
			+ "Winter" + FILE_SEPARATOR;
	public static final String XCRF_ROOT_NODE_NAME = "ËùÓÐ¶ÁÐ´Æ÷";
	public static final String XF_GROUP_DEFAULT_NAME = "Î´·Ö×é¶ÁÐ´Æ÷";
	public static final String CONN_TYPE_COM = "RS232";
	public static final String CONN_TYPE_TCP = "TCP_CLIENT";

	public static final String getXcrfDirectory() {
		return System.getProperty("user.dir");
	}

	public static final String[] MSG_TYPES = { "EPC_6C", "TID_6C",
			"EPC_TID_UserData_6C", "EPC_TID_UserData_6C_2", "ID_6B",
			"ID_UserData_6B", "EPC_6C_ID_6B", "TID_6C_ID_6B",
			"EPC_TID_UserData_6C_ID_UserData_6B", "EPC_PC_6C" };
	public static final String[] ANTENNA_ITEMS_800 = { "1#", "2#", "3#", "4#",
			"1-2#", "1-3#", "1-4#" };
	public static final String[] ANTENNA_ITEMS_500 = { "1#", "2#", "1-2#" };
	public static final String[] STOP_TYPES = { "500", "800" };
}
