package com.champ.base.response;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.champ.base.dto.PaymentModeDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentModeResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5256250254983887494L;

	private List<PaymentModeDto> paymentModes;

	public List<PaymentModeDto> getPaymentModes() {
		return paymentModes;
	}

	public void setPaymentModes(List<PaymentModeDto> paymentModes) {
		this.paymentModes = paymentModes;
	}

}
