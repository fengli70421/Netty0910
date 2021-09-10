

package com.bob.Entity;


import com.bob.client.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.Map.Entry;

public class TransactionManager {
	private static final Map<String, TransactionStats> map = new HashMap<String, TransactionStats>();
	private ArrayList<Transaction> doneTransactions;
	private static Set<Transaction> undoneTransactions = null;
	private Object syncObject;
	private HashMap<String, Double> userpoint;

	boolean isRunning = false;
	Timer timer = new Timer();
	int interval = 10000; // 间隔时间？

//	private static ThreadLocal<MyTransaction> current = new ThreadLocal<>();


	// friendly in packet
    public TransactionManager() {
		userpoint = new HashMap<String, Double>();
		doneTransactions = new ArrayList<Transaction>(10000);
		syncObject = new Object();
		undoneTransactions = Collections.synchronizedSet(new HashSet<Transaction>(10000));
	}

//	public static Transaction CreateTransaction() {
//	}

	@Autowired
	public void setUserPoint(String name, Double value) {
		synchronized (syncObject) {
			userpoint.put(name, value);
		}
	}

	public Iterator<Entry<String, Double>> getUserPoint() {
		HashMap<String, Double> tmp;
		synchronized (syncObject) {
			tmp = userpoint;
			userpoint = new HashMap<String, Double>();
		}
		return tmp.entrySet().iterator();
	}

//	public static Transaction CreateTransaction(String name,Tracing trace) {
//		return CreateTransaction(name,UserManager.transTimeout,trace);
//	}

	public Transaction createTransaction() {
		String name = UUID.randomUUID().toString();
		final Transaction t = new Transaction(name);
//		undoneTransactions.add(t);

//		if (!isRunning) {
//			timer.schedule(new Task(), 1000, interval);
//			//  安排指定的任务指定的延迟后开始进行重复的固定延迟执行
//			isRunning = true;
//		}
		return t;
	}



//		final Timeout timeout = UserManager.registerTimerTask(new Runnable() {
//			@Override
//			public void run() {
//				t.commitFail("timeout");
//				t.transactionIsTimeout.invoke(null,null);
//			}
//		}, interval);

//		EventHandler<Object> handler = new EventHandler<Object>() {
//			@Override
//			public void process(Object sender, Object args) {
//				Transaction tr = (Transaction) sender;
//				undoneTransactions.remove(tr);
//				synchronized (syncObject) {
//					doneTransactions.add(tr);
//				}
//				timer.cancel();
//				//forTransaction(tr.getName()).increment((long)(tr.getDuration()*1000));
//			}

//		};
//		t.transactionIsOver.addEventHandler(handler);


//	class Task extends TimerTask {
//
//		@Override
//		public void run() {
//
//		}
//	}

	public Transaction CreateTransaction(String name, float duration, boolean isPassed) {
		Transaction t = new Transaction(name, duration, isPassed);
		synchronized (syncObject) {
			doneTransactions.add(t);
		}
		return t;
	}

//	public void commitTransaction(Transaction transaction){
//    	Set<Transaction> tmp = undoneTransactions;
//		t = tmp.
//
//	}

    public void showTransaction(Transaction t) {
        synchronized (syncObject) {
            doneTransactions.add(t);
        }
	}

	public Iterator<Transaction> getTransactionIterator() {
		return getTransactionList().iterator();
	}

	public ArrayList<Transaction> getTransactionList() {
		ArrayList<Transaction> tmp;
		synchronized (syncObject) {
			tmp = doneTransactions;
			doneTransactions = new ArrayList<Transaction>(10000);
		}
		return tmp;
	}

	public Set<Transaction> getUndoneTransactions(){
    	return undoneTransactions;
	}

    public static TransactionStats forTransaction(String name) {
        TransactionStats ts;
        synchronized (map) {
            ts = map.get(name);
            if (ts == null) {
                map.put(name, ts = new TransactionStats());
            }
            return ts;
        }
    }

    public void dispose(){
        this.doneTransactions.clear();
        this.undoneTransactions.clear();
        this.userpoint.clear();
    }


}
