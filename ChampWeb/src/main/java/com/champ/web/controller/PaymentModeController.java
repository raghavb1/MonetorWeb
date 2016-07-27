package com.champ.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.champ.core.entity.PaymentMode;
import com.champ.services.IPaymentModeService;

@Controller
@RequestMapping("/PaymentMode")
public class PaymentModeController {

	@Autowired
	IPaymentModeService paymentModeService;

	@RequestMapping("/create")
	public String addPaymentMode(ModelMap map) {
		map.put("paymentMode", new PaymentMode());
		return "PaymentMode/create";
	}

	@RequestMapping("/save")
	public String savePaymentMode(@ModelAttribute("paymentMode") PaymentMode paymentMode, ModelMap map) {
		if (paymentMode.getId() != null) {
			PaymentMode persistedPaymentMode = paymentModeService.findPaymentModeById(paymentMode.getId());
			persistedPaymentMode.setName(paymentMode.getName());
			persistedPaymentMode.setIconUrl(paymentMode.getIconUrl());
			paymentModeService.saveOrUpdatePaymentMode(persistedPaymentMode);
		} else {
			paymentModeService.saveOrUpdatePaymentMode(paymentMode);
		}
		map.put("message", "Payment Mode saved Successfully");
		List<PaymentMode> paymentModes = paymentModeService.getAllPaymentModes();
		map.put("paymentModes", paymentModes);
		return "PaymentMode/view";
	}

	@RequestMapping("/view")
	public String viewAllPaymentModes(ModelMap map) {
		List<PaymentMode> paymentModes = paymentModeService.getAllPaymentModes();
		map.put("paymentModes", paymentModes);
		return "PaymentMode/view";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editPaymentModes(@PathVariable("id") Long id, ModelMap map) {
		PaymentMode paymentMode = paymentModeService.findPaymentModeById(id);
		map.put("paymentMode", paymentMode);
		return "PaymentMode/create";
	}

}
