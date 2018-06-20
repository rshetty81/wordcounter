package com.wordcounter.domain;

import java.util.Calendar;

public class UrlResponse {
	
	private Calendar createdTime;
	private String data;
	
	public UrlResponse(final String data, final  Calendar createdTime){
		this.createdTime = createdTime;
		this.data = data;
	}
	
	public Calendar getCreatedTime(){
		return (Calendar) createdTime.clone();
	}
	
	public String getData(){
		return data;
	}

}
