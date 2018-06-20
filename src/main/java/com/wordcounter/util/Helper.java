package com.wordcounter.util;

import java.net.URL;

public class Helper {
	
	public static boolean isValidUrl(String url) {
		if (url == null || url.trim().length() == 0) {
			return false;
		}
		try {
			new URL(url);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
