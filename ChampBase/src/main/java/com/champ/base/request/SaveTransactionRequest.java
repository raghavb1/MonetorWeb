package com.champ.base.request;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.champ.base.dto.UserMappedTransaction;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveTransactionRequest extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1565209850226936704L;

	List<UserMappedTransaction> transactions;

	public List<UserMappedTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<UserMappedTransaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "SaveTransactionRequest [transactions=" + transactions + "]";
	}

}
