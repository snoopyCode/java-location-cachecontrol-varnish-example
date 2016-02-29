package com.sample.cache.business;

import javax.cache.CacheManager;
import javax.cache.annotation.CacheResult;
import javax.cache.spi.CachingProvider;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;


@RequestScoped
public class JCacheExample {
	
    @Inject
    CachingProvider cp;
    
    @Inject
    CacheManager cm;

    public String doSlowOperation(String input1, String input2) {
        try {
            
            System.out.println(cp);
            System.out.println(cm);
            System.out.println(cm.getCache("TestCache"));
        	
        	Thread.currentThread().sleep(5000);
        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
        }
        String randomHex = Double.toHexString(Math.random());
        return randomHex;
    }
}