/*
 * Created on Sep 8, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.util;

import org.eclipse.swt.widgets.Display;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class DelayableRunnable implements Runnable {

	private Thread thread;
	private int pause;
	private int temporaryDelay = -1;
	private boolean cancelled = false;
	private boolean isWaiting = false;
	private boolean isAlive = false;
	private boolean isRunning = false;
	private boolean inDisplayThread = false;

	public DelayableRunnable(int pause) {
		this.pause = pause;
	}

	public DelayableRunnable(int pause, boolean inDisplayThread) {
		this.pause = pause;
		this.inDisplayThread = inDisplayThread;
	}

	public void cancel() {
		cancelled = true;
	}

	public void setDelay(int ms) {
		pause = ms;
		targetTime = triggerTime + pause;
	}

	/**
	 * If the DelayableRunnable has already been triggered and is in the wait loop,
	 * this will set the targetTime to now+ms, but will not change the delay time,
	 * so after the Runnable has been run, the next time it is triggered it will use
	 * the original delay 
	 * @param ms
	 */
	public void setTemporaryDelay(int ms) {
		temporaryDelay = ms;
		targetTime = System.currentTimeMillis() + temporaryDelay;
	}

	public void extend() {
		if (!isAlive)
			return;
		isWaiting = true;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public boolean isRunning() {
		return isRunning;
	}

	private long targetTime = -1;
	private long triggerTime;

	public void trigger() {
		isWaiting = true;
		cancelled = false;
		triggerTime = System.currentTimeMillis();
		
		if(temporaryDelay > 0)
			targetTime = triggerTime + temporaryDelay;
		else
			targetTime = triggerTime + pause;
		
		if (thread == null || !isAlive || thread.isInterrupted() || !thread.isAlive()) {
			isAlive = true;
			thread = new Thread() {
				public void run() {
					while (isWaiting) {
						isWaiting = false;
						while (System.currentTimeMillis() < targetTime) {
							try {
								sleep(20);
							} catch (Exception e) {}
						}
					}
					try {
						if (!cancelled) {
							isRunning = true;
							if (inDisplayThread) {
								Display.getDefault().asyncExec(DelayableRunnable.this);
							} else {
								DelayableRunnable.this.run();
							}
							isRunning = false;
						}
					} catch (Exception e) {}
					isAlive = false;
					temporaryDelay = -1;
				}
			};
			thread.start();
		}
	}

	public abstract void run();

}
