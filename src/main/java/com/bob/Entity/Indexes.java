package com.bob.Entity;

import java.util.Date;

public class Indexes {
	
	private String name;
	
	private long start;

	private float duration;

	private int endStatus;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public int getEndStatus() {
		return endStatus;
	}

	public void setEndStatus(int endStatus) {
		this.endStatus = endStatus;
	}
}
