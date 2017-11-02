package com.invengo.xcrf.core.i18n;


/**
 * Standard Message handler that takes a root package, plus key and resolves that into one/more
 * resultant messages.  This Handler is used by all message types to enable flexible look and feel
 * as well as i18n to be implemented in variable ways.
 * 
 * @author  
 *
 */
public interface MessageHandler {

	/**
	 * get a key from the default (System global) bundle
	 * @param key
	 * @return
	 */
	public String getString(String key);
	
	/**
	 * get a key from the defined package bundle, by key
	 * @param packageName
	 * @param key
	 * @return
	 */
	public String getString(String packageName, String key);

	/**
	 * get a key from the defined package bundle, by key
	 * @param packageName
	 * @param key
	 * @param parameters
	 * @return
	 */
	public String getString(String packageName, String key, String...parameters);

	/**
	 * Get a string from the defined package bundle, by key and by a resource class 
	 *  
	 * @param packageName
	 * @param key
	 * @param resourceClass
	 * @param parameters
	 * @return
	 */
	public String getString(String packageName, String key, Class<?> resourceClass, String...parameters);
}
