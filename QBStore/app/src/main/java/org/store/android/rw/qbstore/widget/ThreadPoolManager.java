package org.store.android.rw.qbstore.widget;


public class ThreadPoolManager {
	private static ThreadPoolProxy longRunThreadPool;
	private static Object longRunLock = new Object();
	
	public static ThreadPoolProxy getLongRunThreadPool(){
		
		if(longRunThreadPool == null){
			synchronized (longRunLock) {
				if(longRunThreadPool == null){
					longRunThreadPool = new ThreadPoolProxy(3, 5, 1);
				}
			}
		}
		return longRunThreadPool;
	}
}
