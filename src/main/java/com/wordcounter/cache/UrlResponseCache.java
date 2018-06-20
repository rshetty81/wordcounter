package com.wordcounter.cache;

import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

import com.wordcounter.domain.UrlResponse;

public class UrlResponseCache {
	
	private static final ConcurrentHashMap<String,UrlResponse>  cache =  new ConcurrentHashMap<String,UrlResponse>();
	
	private static final int CACHE_EXPIRATION_TIME_IN_MILLIS = 3600000;
	
	
	public void add(final String url, final String data){
		cache.put(url, new UrlResponse(data, Calendar.getInstance()));
	}
	
	
	
	
	public String get(final String url){
		final UrlResponse response = cache.get(url);
		if (response == null){ 
			return null;
		} else {
			final Calendar now = Calendar.getInstance();
			final Calendar createdTime = response.getCreatedTime();
			if ( now.getTimeInMillis() - createdTime.getTimeInMillis() >= CACHE_EXPIRATION_TIME_IN_MILLIS){
				return null;
			} else {
				return response.getData();
				
			}
			
		}
		
	}
	

}
