package com.champ.web.external.controller;

import java.net.URI;
import java.util.List;

import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.champ.base.response.SignupResponse;
import com.champ.core.cache.PropertyMapCache;
import com.champ.core.enums.Property;
import com.champ.core.utility.CacheManager;
import com.champ.gmail.api.response.GmailTokensResponse;
import com.champ.gmail.api.response.UserInfoResponse;
import com.champ.gmail.api.service.Helper;
import com.champ.gmail.api.service.URLGeneratorService;
import com.champ.services.IApiService;
import com.champ.services.IValidationService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/service")
public class SignupController {

	@Autowired
	IValidationService validationService;

	@Autowired
	URLGeneratorService urlGeneratorService;

	@Autowired
	IApiService apiService;

	@Autowired
	Helper helper;

	private static final Logger LOG = LoggerFactory.getLogger(SignupController.class);

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(@RequestParam("authKey") String authenticationKey) {
		LOG.info("Request Recieved for Signup");
		try {
			String url = null;
			if (authenticationKey != null && authenticationKey.equals(CacheManager.getInstance()
					.getCache(PropertyMapCache.class).getPropertyString(Property.API_AUTHENTICATION_KEY))) {
				url = urlGeneratorService.getAuthURL().toString();
				return "redirect:" + url;
			} else {
				return "fail";
			}

		} catch (Exception e) {
			LOG.error("Error while signing up ", e);
			return "fail";
		}

	}

	@RequestMapping(value = "/signupCallback", produces = "application/json", method = RequestMethod.GET)
	public String getTransactionsForUser(@RequestParam("code") String code, ModelMap map) throws Exception {
		LOG.info("Request Recieved from Google post authentication {}", code);
		URI uri = urlGeneratorService.getTokenURL();
		List<NameValuePair> urlParameters = urlGeneratorService.getTokenQueryParameters(code);
		StringBuffer sb = helper.executePost(uri.toString(), urlParameters);
		GmailTokensResponse tokenResponse = (GmailTokensResponse) helper.getObjectFromJsonString(sb,
				GmailTokensResponse.class);

		urlGeneratorService.getUserInfoURL(tokenResponse.getAccess_token());
		sb = helper.executeGet(uri.toString());
		UserInfoResponse userInfo = (UserInfoResponse) helper.getObjectFromJsonString(sb, UserInfoResponse.class);

		SignupResponse response = apiService.signup(tokenResponse, userInfo);
		Gson gson = new Gson();
		map.put("response", gson.toJson(response));
		return "Signup/returnBack";
	}
}
