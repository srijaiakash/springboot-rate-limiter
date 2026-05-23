package com.example.rate_limiter.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import com.example.rate_limiter.model.RateLimitData;


@Service
public class RateLimiterService {
	
	@Value("${app.rate-limit.max-requests}")
	private int maxRequests;
	
	@Value("${app.rate-limit.window-size}")
	private long windowSize;
	
	
	private final ConcurrentHashMap<String, RateLimitData> requestTracker = new ConcurrentHashMap<String, RateLimitData>();
	
	public boolean allowRequest(String clientIp) {
		long currentTime = System.currentTimeMillis();
		if(!requestTracker.containsKey(clientIp)) {
			requestTracker.put(clientIp, new RateLimitData(1,currentTime));
			return true;
		}
		RateLimitData data = requestTracker.get(clientIp);
		long timeDifference = currentTime - data.getStartTime();
		if(timeDifference > windowSize) {
			data.setRequestCount(1);
			data.setStartTime(currentTime);
			return true;
		}
		if(data.getRequestCount()>=maxRequests) {
			return false;
		}
		data.setRequestCount(data.getRequestCount()+1);
		return true;
		
	}

}
