package com.champ.services;

import com.champ.base.request.BaseRequest;
import com.champ.base.request.GetUserBanksRequest;
import com.champ.base.request.GetUserTransactionRequest;
import com.champ.base.response.GetUserBankResponse;
import com.champ.base.response.GetUserTransactionResponse;
import com.champ.base.response.SignupResponse;
import com.champ.gmail.api.response.GmailTokensResponse;
import com.champ.gmail.api.response.UserInfoResponse;

public interface IApiService {

	public SignupResponse signup(GmailTokensResponse request, UserInfoResponse userInfo) throws Exception;

	public SignupResponse signin(BaseRequest request) throws Exception;

	public GetUserBankResponse getBanksForUser(GetUserBanksRequest request) throws Exception;

	public GetUserTransactionResponse getTransactionsForUser(GetUserTransactionRequest request) throws Exception;
}
