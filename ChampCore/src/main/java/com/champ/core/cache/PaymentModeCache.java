package com.champ.core.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.champ.base.dto.PaymentModeDto;
import com.champ.core.annotation.Cache;
import com.champ.core.entity.PaymentMode;

@Cache(name = "paymentModeCache")
public class PaymentModeCache {

	private List<PaymentModeDto> paymentModes = new ArrayList<PaymentModeDto>();
	private Map<String, PaymentMode> nameToPaymentMode = new HashMap<String, PaymentMode>();

	public void addPaymentMode(PaymentMode paymentMode) {
		PaymentModeDto dto = new PaymentModeDto();
		dto.setName(paymentMode.getName());
		dto.setIconUrl(paymentMode.getIconUrl());
		paymentModes.add(dto);
		nameToPaymentMode.put(paymentMode.getName(), paymentMode);
	}

	public List<PaymentModeDto> getPaymentModes() {
		return paymentModes;
	}

	public PaymentMode getPaymentModeByName(String name) {
		return nameToPaymentMode.get(name);
	}
}
