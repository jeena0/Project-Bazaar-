package com.asiya.projectbazar.service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;


import org.springframework.stereotype.Service;

@Service	
public class PaymentServiceImpl implements PaymentService {

	@SuppressWarnings("deprecation")
	@Override
	public String EsewaPaymentSignature(String secret, String message) {
		String hash = "";
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(),"HmacSHA256");
			sha256_HMAC.init(secret_key);
			hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hash;
	}

}
