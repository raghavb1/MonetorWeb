package com.champ.core.cache;

import java.util.ArrayList;
import java.util.List;

import com.champ.base.dto.PaymentModeDto;
import com.champ.core.annotation.Cache;
import com.champ.core.entity.PaymentMode;

@Cache(name = "paymentModeCache")
public class PaymentModeCache {

	private List<PaymentModeDto> paymentModes = new ArrayList<PaymentModeDto>();

	public void addPaymentMode(PaymentMode paymentMode) {
		PaymentModeDto dto = new PaymentModeDto();
		dto.setName(paymentMode.getName());
		dto.setIconUrl(paymentMode.getIconUrl());
		paymentModes.add(dto);
	}

	public List<PaymentModeDto> getPaymentModes() {
		return paymentModes;
	}
}
