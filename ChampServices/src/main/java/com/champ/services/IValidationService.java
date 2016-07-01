package com.champ.services;

import com.champ.base.request.GetUserBanksRequest;
import com.champ.base.request.GetUserTransactionRequest;
import com.champ.base.request.SigninRequest;
import com.champ.base.request.SignupRequest;

public interface IValidationService {

	public boolean validateSignupCall(SignupRequest request);

	public boolean validateSigninCall(SigninRequest request);

	public boolean validateCallToGetBanksForUser(GetUserBanksRequest request);
	
	public boolean validateCallToGetTransactionsForUser(GetUserTransactionRequest request);
}
