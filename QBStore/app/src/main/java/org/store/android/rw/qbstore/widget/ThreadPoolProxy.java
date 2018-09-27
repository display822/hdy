package org.store.android.rw.qbstore.widget;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPoolProxy {
	private ThreadPoolExecutor pool;
	private int corePoolSize;
	private int maximumPoolSize;
	private int keepAliveTime;
	private TimeUnit unit = TimeUnit.SECONDS;
	
	
	
	public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, int keepAliveTime){
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maximumPoolSize;
		this.keepAliveTime = keepAliveTime;
	}
	
	
	public Future<?> execute(Runnable task){
		
		if(pool == null){
			BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(5);
			ThreadFactory factory = Executors.defaultThreadFactory();
			RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();
			pool = new ThreadPoolExecutor(
					corePoolSize,
					maximumPoolSize,
					keepAliveTime,
					unit,
					workQueue,
					factory,
					handler);
		}
		
		return pool.submit(task);
	}
	
	public void remove(Runnable task){
		pool.getQueue().remove(task);
	}
}
