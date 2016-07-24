package com.champ.base.response;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.champ.base.dto.FailedTransactionDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveTransactionResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 553463563065734335L;

	private List<FailedTransactionDto> failedTransactions;

	public List<FailedTransactionDto> getFailedTransactions() {
		return failedTransactions;
	}

	public void setFailedTransactions(List<FailedTransactionDto> failedTransactions) {
		this.failedTransactions = failedTransactions;
	}

}
