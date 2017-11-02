package com.invengo.xcrf.util;

public class NativeUtil {
	static {
		System.loadLibrary("nativeUtil1");
	}
	
	public native static void beep();
	
	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			NativeUtil.beep();
		}
		
	}
	
	
}
