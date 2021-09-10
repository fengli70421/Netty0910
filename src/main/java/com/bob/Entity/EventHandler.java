
package com.bob.Entity;

public abstract class EventHandler<T> {
	private final HandleType type;
	public EventHandler(HandleType type) {
		this.type = type;
	}
	public EventHandler() {
		type = HandleType.longlive;;
	}
	public abstract void process(Object sender, T args );
	public HandleType getType() {
		return type;
	}
}

