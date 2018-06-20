package com.wordcounter.main;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wordcounter.cache.UrlResponseCache;
import com.wordcounter.util.Helper;

public class WordCounter {

	private static final UrlResponseCache urlResponseCache = new UrlResponseCache();

	public static void main(String... args) {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter a url: ");
		String url = reader.next();
		if (!Helper.isValidUrl(url)) {
			reader.close();
			throw new IllegalArgumentException("Url[" + url + "] is invalid.");
		}
		reader.nextLine();
		System.out.println("Please enter keywords seperated by a space:  eg: Hello Word Counter");
		String keywordInput = reader.nextLine();
		reader.close();
		List<String> keywords = Arrays.asList(keywordInput.split(" "));

		WordCounter wc = new WordCounter();
		int keywordCount = wc.getKeywordCount(keywords, url.trim());
		System.out.println("The keywords [" + keywordInput + "]  occured  [" + keywordCount + "] times in the url ["
				+ url + "] resource .");

	}

	public int getKeywordCount(List<String> keywords, String url) {

		String urlResource = urlResponseCache.get(url);

		if (urlResource == null) {

			try {
				URLConnection urlConn = new URL(url).openConnection();
				InputStream is = urlConn.getInputStream();
				String response = readFromAnInputStream(is);
				urlResponseCache.add(url, response);
				urlResource = urlResponseCache.get(url);
			} catch (IOException e) {
				
				throw new RuntimeException("Could not retrieve resource from the Url[" + url + "]" + e.getMessage());
			}

		}

		int count = countKeyWords(keywords, urlResource);
		System.out.println(count);
		return count;

	}

	public synchronized int countKeyWords(List<String> keywords, String urlResource) {
		int count = 0;
		for (String keyword : keywords) {

			final Pattern p = Pattern.compile("\\b" + keyword + "\\b");
			final Matcher m = p.matcher(urlResource);

			while (m.find()) {
				count++;
			}

		}
		return count;
	}

	public synchronized String readFromAnInputStream(InputStream is) throws IOException, UnsupportedEncodingException {
		BufferedInputStream bis = new BufferedInputStream(is);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while (result != -1) {
			buf.write((byte) result);
			result = bis.read();
		}

		return buf.toString("UTF-8");
	}

	
}
