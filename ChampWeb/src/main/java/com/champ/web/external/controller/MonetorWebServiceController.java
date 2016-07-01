package com.champ.web.external.controller;

import java.net.URI;
import java.util.List;

import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.champ.base.request.GetUserBanksRequest;
import com.champ.base.request.GetUserTransactionRequest;
import com.champ.base.request.SigninRequest;
import com.champ.base.response.GetUserBankResponse;
import com.champ.base.response.GetUserTransactionResponse;
import com.champ.base.response.SigninResponse;
import com.champ.base.response.SignupResponse;
import com.champ.core.enums.ApiResponseCodes;
import com.champ.core.exception.MonetorServiceException;
import com.champ.gmail.api.response.GmailTokensResponse;
import com.champ.gmail.api.service.Helper;
import com.champ.gmail.api.service.URLGeneratorService;
import com.champ.services.IApiService;
import com.champ.services.IValidationService;

@Controller
@RequestMapping("/service")
public class MonetorWebServiceController {

	@Autowired
	IValidationService validationService;

	@Autowired
	IApiService apiService;

	@Autowired
	URLGeneratorService urlGeneratorService;

	@Autowired
	Helper helper;

	private static final Logger LOG = LoggerFactory.getLogger(MonetorWebServiceController.class);

	//TODO Delete this
	/*@RequestMapping(value = "/signup", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public SignupResponse signup(@RequestBody SignupRequest signupRequest) throws Exception {
		LOG.info("Request Recieved for Signup {}", signupRequest);
		SignupResponse response = null;
		if (validationService.validateSignupCall(signupRequest)) {
			response = apiService.signup(signupRequest);
		} else {
			LOG.info("Invalid Request");
			throw new MonetorServiceException(ApiResponseCodes.INVALID_REQUEST);
		}
		LOG.info("Sending Response for Signup Response {}", response);
		return response;
	}*/

	@RequestMapping(value = "/signin", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public SigninResponse signin(@RequestBody SigninRequest request) throws Exception {
		LOG.info("Request Recieved for Signin {}", request);
		SigninResponse response = null;
		if (validationService.validateSigninCall(request)) {
			response = apiService.signin(request);
		} else {
			LOG.info("Invalid Request");
			throw new MonetorServiceException(ApiResponseCodes.INVALID_REQUEST);
		}
		LOG.info("Sending Response for Signin Response {}", response);
		return response;
	}

	@RequestMapping(value = "/getBanksForUser", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public GetUserBankResponse getBanksForUser(@RequestBody GetUserBanksRequest request) throws Exception {
		LOG.info("Request Recieved for getting banks for a user {}", request);
		GetUserBankResponse response = null;
		if (validationService.validateCallToGetBanksForUser(request)) {
			response = apiService.getBanksForUser(request);
		} else {
			LOG.info("Invalid Request");
			throw new MonetorServiceException(ApiResponseCodes.INVALID_REQUEST);
		}
		LOG.info("Sending Response for getting banks for a user {}", response);
		return response;
	}

	@RequestMapping(value = "/getTransactionsForUser", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public GetUserTransactionResponse getTransactionsForUser(@RequestBody GetUserTransactionRequest request)
			throws Exception {
		LOG.info("Request Recieved for getting transactions for a user {}", request);
		GetUserTransactionResponse response = null;
		if (validationService.validateCallToGetTransactionsForUser(request)) {
			response = apiService.getTransactionsForUser(request);
		} else {
			LOG.info("Invalid Request");
			throw new MonetorServiceException(ApiResponseCodes.INVALID_REQUEST);
		}
		LOG.info("Sending Response for getting transactions for a user {}", response);
		return response;
	}

	@RequestMapping(value = "/signupCallback", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public SignupResponse getTransactionsForUser(@RequestParam("code") String code) throws Exception {
		LOG.info("Request Recieved from Google post authentication {}", code);
		URI uri = urlGeneratorService.getTokenURL();
		List<NameValuePair> urlParameters = urlGeneratorService.getTokenQueryParameters(code);
		StringBuffer sb = helper.executePost(uri.toString(), urlParameters);
		GmailTokensResponse tokenResponse = (GmailTokensResponse) helper.getObjectFromJsonString(sb,
				GmailTokensResponse.class);
		SignupResponse response = apiService.signup(tokenResponse);
		LOG.info("Sending Response for signup for a user {}", response);
		return response;
	}

}
