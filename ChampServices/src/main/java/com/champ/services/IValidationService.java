package com.champ.services;

import com.champ.base.request.BaseRequest;
import com.champ.base.request.GetUserBanksRequest;
import com.champ.base.request.GetUserTransactionRequest;
import com.champ.base.request.RegisterUserRequest;
import com.champ.base.request.SaveMessageRequest;
import com.champ.base.request.SaveTransactionRequest;

public interface IValidationService {

	public boolean validateBaseCall(BaseRequest request);

	public boolean validateCallToGetBanksForUser(GetUserBanksRequest request);

	public boolean validateCallToGetTransactionsForUser(GetUserTransactionRequest request);
	
	public boolean validateSaveTransactionCall(SaveTransactionRequest request);
	
	public boolean validateRegisterUserCall(RegisterUserRequest request);
	
	public boolean validateSaveMessageCall(SaveMessageRequest request);
}
