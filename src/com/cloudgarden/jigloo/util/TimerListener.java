package com.cloudgarden.jigloo.util;

public abstract class TimerListener {

	private int intervalInMs = 10;
	private int msRemaining;
	
	public TimerListener(int intervalInMs) {
		setIntervalInMs(intervalInMs);
	}
	
	public void setIntervalInMs(int intervalInMs) {
		this.intervalInMs = intervalInMs;
		msRemaining = intervalInMs;
	}
	
	protected void decreaseTimeRemaining(int ms) {
		msRemaining -= ms;
		if(msRemaining <= 0) {
			msRemaining += intervalInMs;
			doAction();
		}
	}
	
	public abstract void doAction();
	
}
