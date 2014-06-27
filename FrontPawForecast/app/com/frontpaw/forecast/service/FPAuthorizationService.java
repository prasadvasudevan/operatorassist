package com.frontpaw.forecast.service;

import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class FPAuthorizationService {
	private static final String UTF8_CHARSET = "UTF-8";
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	private String FPWAccessKeyId = "KLIJH2IOLJ3FI2ILMFHF";
	private String FPWSecretKey = "djiKJ98Jlj3FEjl2UIOasd983djERj2j34JZZlxY";

	public Hashtable getAuthorization() {

		String result;
		Hashtable headerResult = new Hashtable();
		try {

			// get an hmac_sha1 key from the raw key bytes
			SecretKeySpec signingKey = new SecretKeySpec(FPWSecretKey.getBytes(UTF8_CHARSET), HMAC_SHA1_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
			String timestamp = timestamp();
			String toSign = timestamp;
			String userId = "fpmainwebapp.admin";
			String myPassword = "zW3fQ39aN8";
			String userAndPassword = "fpmainwebapp.admin:zW3fQ39aN8";
			byte[] rawUserID = mac.doFinal(userId.getBytes(UTF8_CHARSET));
			byte[] rawHmac = mac.doFinal(toSign.getBytes(UTF8_CHARSET));
			byte[] rawPassword = mac.doFinal(myPassword.getBytes(UTF8_CHARSET));
		//	byte[] combined = mac.doFinal(userAndPassword.getBytes(UTF8_CHARSET));
			byte[] combined = userAndPassword.getBytes(UTF8_CHARSET);
			Base64 encoder = new Base64();
			String signature = new String(encoder.encode(rawHmac));
			String enUserID = new String(encoder.encode(rawUserID));
			String enPassword = new String(encoder.encode(rawPassword));
			String enCombined = new String(encoder.encode(combined));
			result = "FPWS" + " " + FPWAccessKeyId + ":" + signature;
			String result1 = "UserID:" + enUserID + " " + "Password:" + enPassword;
			headerResult.put("Authorization", result);
			headerResult.put("x-fpws-authorization",enCombined);
			headerResult.put("userAndPassword",result1);
			
			headerResult.put("date", timestamp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			headerResult = null;
		}
		return headerResult;

	}

	private String timestamp() {
		String timestamp = null;
		Calendar cal = Calendar.getInstance();
		DateFormat dfm = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
		dfm.setTimeZone(TimeZone.getTimeZone("GMT"));
		timestamp = dfm.format(cal.getTime());
		System.out.println(timestamp);
		return timestamp;
	}

	private String canonicalize(SortedMap<String, String> sortedParamMap) {
		if (sortedParamMap.isEmpty()) {
			return "";
		}

		StringBuffer buffer = new StringBuffer();
		Iterator<Map.Entry<String, String>> iter = sortedParamMap.entrySet()
				.iterator();

		while (iter.hasNext()) {
			Map.Entry<String, String> kvpair = iter.next();
			buffer.append(percentEncodeRfc3986(kvpair.getValue()));
			if (iter.hasNext()) {
				buffer.append("&");
			}
		}
		String cannoical = buffer.toString();
		return cannoical;
	}

	private String percentEncodeRfc3986(String s) {
		String out;
		try {
			out = URLEncoder.encode(s, UTF8_CHARSET).replace("+", "%20")
					.replace("*", "%2A").replace("%7E", "~");
		} catch (UnsupportedEncodingException e) {
			out = s;
		}
		return out;
	}

}
