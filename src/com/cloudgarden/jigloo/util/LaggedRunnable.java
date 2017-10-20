/*
 * Created on Sep 8, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.util;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class LaggedRunnable {

	Thread thread;
	private int pause;
	private boolean isAlive = false;

	public LaggedRunnable(int pause) {
		this.pause = pause;
	}

	public void trigger() {
		if (thread == null || !isAlive) {
			isAlive = true;
			thread = new Thread() {
				public void run() {
					LaggedRunnable.this.run();
					try {
						sleep(pause);
					} catch (Exception e) {}
					isAlive = false;
				}
			};
			thread.start();
		}
	}

	public abstract void run();

}
