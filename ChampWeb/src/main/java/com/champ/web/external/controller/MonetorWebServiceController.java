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

	@RequestMapping(value = "/registerUser", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public RegisterUserResponse registerUser(@RequestBody RegisterUserRequest request) throws Exception {
		LOG.info("Request Recieved for Register User {}", request);
		RegisterUserResponse response = null;
		if (validationService.validateRegisterUserCall(request)) {
			response = apiService.registerUser(request);
		} else {
			LOG.info("Invalid Request");
			throw new MonetorServiceException(ApiResponseCodes.INVALID_REQUEST);
		}
		LOG.info("Sending Response for Register User Response {}", response);
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

	@RequestMapping(value = "/getUserProperties", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public GetUserPropertiesResponse getUserProperties(@RequestBody BaseRequest request) throws Exception {
		LOG.info("Request Recieved for getting properties for a user {}", request);
		GetUserPropertiesResponse response = null;
		if (validationService.validateBaseCall(request)) {
			response = apiService.getUserProperties(request);
		} else {
			LOG.info("Invalid Request");
			throw new MonetorServiceException(ApiResponseCodes.INVALID_REQUEST);
		}
		LOG.info("Sending Response for getting properties for a user {}", response);
		return response;
	}

	@RequestMapping(value = "/getPaymentModes", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public PaymentModeResponse getPaymentModes(@RequestBody BaseRequest request) throws Exception {
		LOG.info("Request Recieved for getting payment modes for a user {}", request);
		PaymentModeResponse response = null;
		if (validationService.validateBaseCall(request)) {
			response = apiService.getPaymentModes(request);
		} else {
			LOG.info("Invalid Request");
			throw new MonetorServiceException(ApiResponseCodes.INVALID_REQUEST);
		}
		LOG.info("Sending Response for getting payment modes for a user {}", response);
		return response;
	}

	@RequestMapping(value = "/getCategories", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public CategoryResponse getCategories(@RequestBody BaseRequest request) throws Exception {
		LOG.info("Request Recieved for getting categories for a user {}", request);
		CategoryResponse response = null;
		if (validationService.validateBaseCall(request)) {
			response = apiService.getCategories(request);
		} else {
			LOG.info("Invalid Request");
			throw new MonetorServiceException(ApiResponseCodes.INVALID_REQUEST);
		}
		LOG.info("Sending Response for getting categories for a user {}", response);
		return response;
	}

	@RequestMapping(value = "/saveTransactions", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public SaveTransactionResponse saveUserTransaction(@RequestBody SaveTransactionRequest request) throws Exception {
		LOG.info("Request Recieved for saving user transaction for a user {}", request);
		SaveTransactionResponse response = null;
		if (validationService.validateSaveTransactionCall(request)) {
			response = apiService.saveUserTransactions(request);
		} else {
			LOG.info("Invalid Request");
			throw new MonetorServiceException(ApiResponseCodes.INVALID_REQUEST);
		}
		LOG.info("Sending Response for saving transactions for a user {}", response);
		return response;
	}

	@RequestMapping(value = "/saveMessages", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse saveUserMessages(@RequestBody SaveMessageRequest request) throws Exception {
		LOG.info("Request Recieved for saving user messages for a user {}", request);
		BaseResponse response = null;
		if (validationService.validateSaveMessageCall(request)) {
			response = apiService.saveUserMessages(request);
		} else {
			LOG.info("Invalid Request");
			throw new MonetorServiceException(ApiResponseCodes.INVALID_REQUEST);
		}
		LOG.info("Sending Response for saving messages for a user {}", response);
		return response;
	}
}
