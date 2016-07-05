package com.champ.web.external.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.champ.base.request.BaseRequest;
import com.champ.base.request.GetUserBanksRequest;
import com.champ.base.request.GetUserTransactionRequest;
import com.champ.base.response.GetUserBankResponse;
import com.champ.base.response.GetUserTransactionResponse;
import com.champ.base.response.SignupResponse;
import com.champ.core.enums.ApiResponseCodes;
import com.champ.core.exception.MonetorServiceException;
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

	private static final Logger LOG = LoggerFactory.getLogger(MonetorWebServiceController.class);

	@RequestMapping(value = "/signin", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public SignupResponse signin(@RequestBody BaseRequest request) throws Exception {
		LOG.info("Request Recieved for Signin {}", request);
		SignupResponse response = null;
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

}
