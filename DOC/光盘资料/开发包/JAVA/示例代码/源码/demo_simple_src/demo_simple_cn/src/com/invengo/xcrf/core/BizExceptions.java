package com.invengo.xcrf.core;

public class BizExceptions extends Exception {

	private static final long serialVersionUID = 5962569174759343819L;

	public BizExceptions() {
	}

	public BizExceptions(String message) {
		super(message);
	}

	public BizExceptions(Throwable cause) {
		super(cause);
	}

	public BizExceptions(String message, Throwable cause) {
		super(message, cause);
	}

}
