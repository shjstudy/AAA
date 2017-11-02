package com.invengo.xcrf.core.i18n;

import java.util.Locale;


/**
 * Standard Message handler that takes a root package, plus key and resolves that into one/more
 * resultant messages.  This Handler is used by all message types to enable flexible look and feel
 * as well as i18n to be implemented in variable ways.
 * 
 * @author 
 *
 */
public abstract class AbstractMessageHandler implements MessageHandler {
	
	/**
	 * forced override, concrete implementations must provide implementation
	 * 
	 * @return Locale
	 */
	public synchronized static Locale getLocale() {
		return null;
	}
	
	/**
	 * forced override, concrete implementations must provide implementation
	 * 
	 * @param newLocale
	 */
	public synchronized static void setLocale(Locale newLocale) {}
	
}