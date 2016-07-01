package com.champ.core.cache;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.champ.core.annotation.Cache;
import com.champ.core.entity.BankPaymentMode;

@Cache(name = "paymentModeCache")
public class PaymentModeCache {

	private Map<String, String> paymentModes = new HashMap<String, String>();

	private static final String separator = "~";

	private static final Logger LOG = LoggerFactory.getLogger(PaymentModeCache.class);

	public void addPaymentMode(BankPaymentMode bankPaymentMode) {
		if (bankPaymentMode.getBank() != null && bankPaymentMode.getExtractedString() != null
				&& bankPaymentMode.getExtractedString() != "") {
			paymentModes.put(bankPaymentMode.getBank().getId() + separator + bankPaymentMode.getExtractedString(),
					bankPaymentMode.getPaymentMode());
		} else {
			LOG.info("Bank or extracted string not found. Not adding in cache");
		}
	}

	public String getPaymentMode(Long bankId, String extractedString) {
		String key = bankId + separator + extractedString;
		return paymentModes.get(key);
	}
}
