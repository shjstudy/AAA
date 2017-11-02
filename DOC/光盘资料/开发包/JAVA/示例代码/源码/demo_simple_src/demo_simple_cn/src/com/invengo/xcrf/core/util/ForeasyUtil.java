package com.invengo.xcrf.core.util;

public class ForeasyUtil {
	
	
	
	public static boolean isNotNull(Object o)
	{
		return !isNull(o);
	}
	
	
	public static boolean isNull(Object o)
	{
		if(o == null)
			return true;
		if(o instanceof String)
			return isEmptyString((String)o);
		return false;
	}
	
	
	public static boolean isEmptyString(String s)
	{
		return s==null || s.length()==0;
	}

	
	public static String getString(String s , String def)
	{
		return isEmptyString(s) ? def : s;
			
	}
	
}
