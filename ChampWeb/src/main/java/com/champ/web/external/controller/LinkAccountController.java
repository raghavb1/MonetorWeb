package com.champ.web.external.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.champ.base.request.BaseRequest;
import com.champ.base.response.SignupResponse;
import com.champ.core.entity.AppUser;
import com.champ.gmail.api.response.GmailTokensResponse;
import com.champ.gmail.api.response.UserInfoResponse;
import com.champ.gmail.api.service.Helper;
import com.champ.gmail.api.service.URLGeneratorService;
import com.champ.services.IApiService;
import com.champ.services.IAppUserService;
import com.champ.services.IValidationService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/service")
public class LinkAccountController {

	@Autowired
	IValidationService validationService;

	@Autowired
	URLGeneratorService urlGeneratorService;

	@Autowired
	IApiService apiService;

	@Autowired
	IAppUserService appUserService;

	@Autowired
	Helper helper;

	private static final Logger LOG = LoggerFactory.getLogger(LinkAccountController.class);

	@RequestMapping(value = "/linkAccount", method = RequestMethod.GET)
	public String linkAccount(@RequestParam("authKey") String authKey, @RequestParam("mobile") String mobile,
			@RequestParam("token") String token, HttpServletRequest servletRequest) {
		LOG.info("Request Recieved for Signup");
		try {
			String url = null;
			BaseRequest request = new BaseRequest(mobile, authKey, token);
			if (validationService.validateBaseCall(request)) {
				AppUser user = appUserService.authenticateUser(request.getMobile(), request.getToken());
				servletRequest.getSession().setAttribute("user", user);
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
	public String getTransactionsForUser(@RequestParam("code") String code, ModelMap map, HttpServletRequest request)
			throws Exception {
		LOG.info("Request Recieved from Google post authentication {}", code);
		URI uri = urlGeneratorService.getTokenURL();
		List<NameValuePair> urlParameters = urlGeneratorService.getTokenQueryParameters(code);
		StringBuffer sb = helper.executePost(uri.toString(), urlParameters);
		GmailTokensResponse tokenResponse = (GmailTokensResponse) helper.getObjectFromJsonString(sb,
				GmailTokensResponse.class);
		uri = urlGeneratorService.getUserInfoURL(tokenResponse.getAccess_token());
		sb = helper.executeGet(uri.toString());
		UserInfoResponse userInfo = (UserInfoResponse) helper.getObjectFromJsonString(sb, UserInfoResponse.class);
		AppUser user = (AppUser) request.getSession().getAttribute("user");
		SignupResponse response = null;
		response = apiService.signup(tokenResponse, userInfo, user);
		Gson gson = new Gson();
		map.put("response", gson.toJson(response));
		return "Signup/returnBack";
	}
}
