package com.champ.web.external.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.champ.gmail.api.service.URLGeneratorService;
import com.champ.services.IValidationService;

@Controller
@RequestMapping("/service")
public class SignupController {

	@Autowired
	IValidationService validationService;

	@Autowired
	URLGeneratorService urlGeneratorService;

	private static final Logger LOG = LoggerFactory.getLogger(SignupController.class);

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(@RequestParam("authKey") String authenticationKey) {
		LOG.info("Request Recieved for Signup");
		try {
			String url = urlGeneratorService.getAuthURL().toString();
			return "redirect:" + url;
		} catch (Exception e) {
			LOG.error("Error while signing up ", e);
			return "fail";
		}

	}
}
