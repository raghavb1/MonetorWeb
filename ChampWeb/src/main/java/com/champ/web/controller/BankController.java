package com.champ.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.champ.core.entity.Bank;
import com.champ.services.IBankService;

@Controller
@RequestMapping("/Bank")
public class BankController {

	@Autowired
	IBankService bankService;

	@RequestMapping("/create")
	public String addBank(ModelMap map) {
		map.put("bank", new Bank());
		return "Bank/create";
	}

	@RequestMapping("/save")
	public String saveBank(@ModelAttribute("bank") Bank bank, ModelMap map) {
		if (bank.getId() != null) {
			Bank persistedBank = bankService.findBankById(bank.getId());
			persistedBank.setIconUrl(bank.getIconUrl());
			persistedBank.setName(bank.getName());
			persistedBank.setWebsiteLink(bank.getWebsiteLink());
			bankService.saveOrUpdateBank(persistedBank);
		} else {
			bankService.saveOrUpdateBank(bank);
		}
		map.put("message", "Bank saved Successfully");
		List<Bank> banks = bankService.getAllBanks();
		map.put("banks", banks);
		return "Bank/view";
	}

	@RequestMapping("/view")
	public String viewAllBanks(ModelMap map) {
		List<Bank> banks = bankService.getAllBanks();
		map.put("banks", banks);
		return "Bank/view";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editBank(@PathVariable("id") Long id, ModelMap map) {
		Bank bank = bankService.findBankById(id);
		map.put("bank", bank);
		map.put("edit", "true");
		return "Bank/create";
	}

	@RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
	public String disableBank(@PathVariable("id") Long id, ModelMap map) {
		bankService.disableBank(id);
		List<Bank> banks = bankService.getAllBanks();
		map.put("banks", banks);
		map.put("message", "Bank disabled Successfully");
		return "Bank/view";
	}

	@RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
	public String enableBank(@PathVariable("id") Long id, ModelMap map) {
		bankService.enableBank(id);
		List<Bank> banks = bankService.getAllBanks();
		map.put("banks", banks);
		map.put("message", "Bank enabled Successfully");
		return "Bank/view";
	}

	@RequestMapping("/checkBank")
	public @ResponseBody String checkBank(@ModelAttribute("name") String name) {
		boolean result = bankService.checkBank(name);
		if (result) {
			return "success";
		} else {
			return "failure";
		}
	}

}
