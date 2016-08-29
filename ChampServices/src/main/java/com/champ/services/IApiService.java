package com.champ.services;

import com.champ.base.request.BaseRequest;
import com.champ.base.request.GetUserBanksRequest;
import com.champ.base.request.GetUserTransactionRequest;
import com.champ.base.request.RegisterUserRequest;
import com.champ.base.request.SaveMessageRequest;
import com.champ.base.request.SaveTransactionRequest;
import com.champ.base.response.BaseResponse;
import com.champ.base.response.CategoryResponse;
import com.champ.base.response.GetUserBankResponse;
import com.champ.base.response.GetUserPropertiesResponse;
import com.champ.base.response.GetUserTransactionResponse;
import com.champ.base.response.PaymentModeResponse;
import com.champ.base.response.RegisterUserResponse;
import com.champ.base.response.SaveTransactionResponse;
import com.champ.base.response.SignupResponse;
import com.champ.core.entity.AppUser;
import com.champ.gmail.api.response.GmailTokensResponse;
import com.champ.gmail.api.response.UserInfoResponse;

public interface IApiService {

	public SignupResponse signup(GmailTokensResponse request, UserInfoResponse userInfo, AppUser user) throws Exception;

	public RegisterUserResponse registerUser(RegisterUserRequest request) throws Exception;

	public GetUserBankResponse getBanksForUser(GetUserBanksRequest request) throws Exception;

	public GetUserTransactionResponse getTransactionsForUser(GetUserTransactionRequest request) throws Exception;

	public GetUserPropertiesResponse getUserProperties(BaseRequest request) throws Exception;

	public PaymentModeResponse getPaymentModes(BaseRequest request) throws Exception;

	public CategoryResponse getCategories(BaseRequest request) throws Exception;

	public SaveTransactionResponse saveUserTransactions(SaveTransactionRequest request) throws Exception;
	
	public BaseResponse saveUserMessages(SaveMessageRequest request) throws Exception;
}
