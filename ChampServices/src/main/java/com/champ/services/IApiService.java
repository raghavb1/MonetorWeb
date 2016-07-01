package com.champ.services;

import com.champ.base.request.GetUserBanksRequest;
import com.champ.base.request.GetUserTransactionRequest;
import com.champ.base.request.SigninRequest;
import com.champ.base.response.GetUserBankResponse;
import com.champ.base.response.GetUserTransactionResponse;
import com.champ.base.response.SigninResponse;
import com.champ.base.response.SignupResponse;
import com.champ.gmail.api.response.GmailTokensResponse;

public interface IApiService {

	public SignupResponse signup(GmailTokensResponse request) throws Exception;

	public SigninResponse signin(SigninRequest request) throws Exception;

	public GetUserBankResponse getBanksForUser(GetUserBanksRequest request) throws Exception;

	public GetUserTransactionResponse getTransactionsForUser(GetUserTransactionRequest request) throws Exception;
}
