package com.sample.cache.business;

import javax.ejb.Stateless;

@Stateless
public class CacheControlExample {
	
    public String doSlowOperation(String input1, String input2) {
        try {       	
        	Thread.currentThread().sleep(5000);
        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
        }
        String randomHex = Double.toHexString(Math.random());
        return randomHex;
    }

}
