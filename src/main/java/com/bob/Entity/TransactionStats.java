
package com.bob.Entity;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


public class TransactionStats {

    long[] durationMS;
    long lastDurationMS;
    long averageDurationMS;
    long start = 0;
    AtomicLong count = new AtomicLong();
    AtomicInteger currentPosition= new AtomicInteger();
    
    public TransactionStats(){
        durationMS = new long[10000];
        count.set(0);
        currentPosition.set(0);
    }
    
    public long getCount(){
        return count.get();
    }
        
    public long getLastDurationMS(){
        return lastDurationMS;
    }
    
    public void setLastDurationMS(long duration){
        lastDurationMS = duration;
        //durationMS[currentPosition.get()] = lastDurationMS;
        currentPosition.incrementAndGet();
    }
    
    
    public void startAction() {
        start = System.currentTimeMillis();
    }

    public void endAction() {
        setLastDurationMS( System.currentTimeMillis() - start);
        increment();
    }
    
    public long increment(){
        return count.incrementAndGet();
    }
        
    public long increment(long duration){
        setLastDurationMS( duration);
        return count.incrementAndGet();
    }
    
    public long reset(){
        long c= count.get();
        count.set(0);
        return c;
    }
}
