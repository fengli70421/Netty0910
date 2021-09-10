//package com.bob.Entity;
//
//import java.util.LinkedList;
//
//public class Event<T> {
//	boolean isInvoked = false;
//	private LinkedList<EventHandler<T>> handlersSet = new LinkedList<EventHandler<T>>();
//	private LinkedList<EventHandler<T>> backup = new LinkedList<EventHandler<T>>();
//
//	public Event<T> addEventHandler(EventHandler<Object> handler) {
//		synchronized (handlersSet) {
//			handlersSet.add(handler);
//			return this;
//		}
//	}
//
//	public void removeEventHandler(EventHandler<T> handler) {
//		synchronized (handlersSet) {
//			handlersSet.remove(handler);
//		}
//	}
//
//	public int getHandlersCount() {
//		synchronized (handlersSet) {
//			return handlersSet.size();
//		}
//	}
//
//	public void clearAllHandlers() {
//		synchronized (handlersSet) {
//			handlersSet.clear();
//		}
//	}
//
//	private kraken.common.EventHandler<T> poll() {
//		synchronized (handlersSet) {
//			kraken.common.EventHandler<T> eventHandler = handlersSet.poll();
//			if (eventHandler != null) {
//				if (eventHandler.getType() == HandleType.longlive) {
//					backup.add(eventHandler);
//				}
//			}
//			return eventHandler;
//		}
//	}
//
//	/*
//	 * 所有handler都响应这个事件, 在invoke过程中被新加入的事件也能被处理
//	 */
//	public synchronized void invoke(Object sender, T args) {
//		isInvoked = true;
//		kraken.common.EventHandler<T> eventHandler;
//		while ((eventHandler = poll()) != null) {
//			eventHandler.process(sender, args);
//		}
//		synchronized (handlersSet) {
//			handlersSet.addAll(backup);
//			backup.clear();
//		}
//		return;
//	}
//
//	public boolean isInvoked() {
//		return isInvoked;
//	}
//
//};