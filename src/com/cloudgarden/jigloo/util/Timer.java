package com.cloudgarden.jigloo.util;

import java.util.Vector;

public class Timer {

	private static Vector listeners = new Vector();
	private static Thread counter;
	private static long time = 0;
	private static boolean running = true;
	
	static {
		counter = new Thread() {
			public void run() {
				time = System.currentTimeMillis();
				while(running) {
					try {
						sleep(10);
						long now = System.currentTimeMillis();
						doAction((int) (now - time));
						time = now;
					} catch (InterruptedException e) {
					}
				}
			}
		};
		counter.start();
	}

//	public static void main(String[] args) {
//		Timer.addListener(new TimerListener(1000) {
//			public void doAction() {
//				System.out.println("time out..."+System.currentTimeMillis());
//			}
//		});
//	}
	
	public static void shutdown() {
		running = false;
	}
	
	private static void doAction(int elapsed) {
		for(int i=0;i<listeners.size();i++) {
			((TimerListener)listeners.get(i)).decreaseTimeRemaining(elapsed);
		}
	};
	
	public static void removeListener(TimerListener listener) {
		listeners.remove(listener);
	}
		
	public static void addListener(TimerListener listener) {
		if(!listeners.contains(listener))
			listeners.add(listener);
	}
}
