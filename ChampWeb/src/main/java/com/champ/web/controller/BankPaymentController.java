package com.champ.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.champ.core.entity.BankPaymentMode;
import com.champ.services.IBankPaymentModeService;
import com.champ.services.IBankService;

@Controller
@RequestMapping("/BankPayment")
public class BankPaymentController {

	@Autowired
	IBankPaymentModeService bankPaymentModeService;

	@Autowired
	IBankService bankService;

	@RequestMapping("/create")
	public String addPaymentMode(ModelMap map) {
		map.put("bankPaymentMode", new BankPaymentMode());
		map.put("banks", bankService.getAllBanks());
		return "BankPayment/create";
	}

	@RequestMapping("/save")
	public String savePaymentMode(@ModelAttribute("bankPaymentMode") BankPaymentMode bankPaymentMode, ModelMap map) {
		if (bankPaymentMode.getId() != null) {
			BankPaymentMode persistedBankPaymentMode = bankPaymentModeService
					.findBankPaymentModeById(bankPaymentMode.getId());
			persistedBankPaymentMode.setExtractedString(bankPaymentMode.getExtractedString());
			persistedBankPaymentMode.setBank(bankPaymentMode.getBank());
			persistedBankPaymentMode.setPaymentMode(bankPaymentMode.getPaymentMode());
			bankPaymentModeService.saveOrUpdateBankPaymentMode(persistedBankPaymentMode);
		} else {
			bankPaymentModeService.saveOrUpdateBankPaymentMode(bankPaymentMode);
		}
		map.put("message", "Bank Payment Mode saved Successfully");
		List<BankPaymentMode> bankPaymentModes = bankPaymentModeService.getAllBankPaymentModes();
		map.put("bankPaymentModes", bankPaymentModes);
		return "BankPayment/view";
	}

	@RequestMapping("/view")
	public String viewAllPaymentModes(ModelMap map) {
		List<BankPaymentMode> bankPaymentModes = bankPaymentModeService.getAllBankPaymentModes();
		map.put("bankPaymentModes", bankPaymentModes);
		return "BankPayment/view";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editPaymentModes(@PathVariable("id") Long id, ModelMap map) {
		BankPaymentMode bankPaymentMode = bankPaymentModeService.findBankPaymentModeById(id);
		map.put("bankPaymentMode", bankPaymentMode);
		map.put("banks", bankService.getAllBanks());
		map.put("edit", "true");
		return "BankPayment/create";
	}

}
