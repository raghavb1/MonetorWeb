package com.champ.base.request;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUserTransactionRequest extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7899157243967180268L;

	private Boolean userCreatedTransaction = false;

	public Boolean getUserCreatedTransaction() {
		return userCreatedTransaction;
	}

	public void setUserCreatedTransaction(Boolean userCreatedTransaction) {
		this.userCreatedTransaction = userCreatedTransaction;
	}

	@Override
	public String toString() {
		return "GetUserTransactionRequest [userCreatedTransaction=" + userCreatedTransaction + "]";
	}

}
