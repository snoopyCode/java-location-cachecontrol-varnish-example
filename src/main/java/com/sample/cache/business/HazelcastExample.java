package com.sample.cache.business;

import java.util.Map;

import javax.enterprise.context.RequestScoped;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@RequestScoped
public class HazelcastExample {
	
	HazelcastInstance instance;
	boolean inited = false;
	
    public void init() {

		if(!inited) {
			Config cfg = new Config();
	        instance = Hazelcast.newHazelcastInstance(cfg);
		}
	}

	public String saveData() {

        String randomHex = Double.toHexString(Math.random());
        Integer randomNum = 0 + (int)(Math.random() * 100000); 
        
        Map<Integer, String> mapCustomers = instance.getMap("data");
        mapCustomers.put(randomNum, randomHex);

        
        return randomHex;
    }

}
