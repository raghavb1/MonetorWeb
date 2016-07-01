package com.champ.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.champ.core.entity.Merchant;
import com.champ.core.entity.SubMerchant;
import com.champ.services.IMerchantManagementService;
import com.champ.services.ISubMerchantService;

@Controller
@RequestMapping("/SubMerchant")
public class SubMerchantController {

	@Autowired
	IMerchantManagementService merchantManagementService;

	@Autowired
	ISubMerchantService subMerchantService;

	@RequestMapping("/view")
	public String viewAllSubMerchant(ModelMap map) {
		List<Merchant> merchants = merchantManagementService.getAllMerchants();
		List<SubMerchant> subMerchants = subMerchantService.getAllUnaprovedSubMerchants();
		map.put("submerchants", subMerchants);
		map.put("merchants", merchants);
		return "SubMerchant/view";
	}

	@RequestMapping(value = "/approve/{id}", method = RequestMethod.POST)
	public @ResponseBody String approveSubMerchant(@PathVariable("id") Long subMerchantId,
			@RequestParam("merchantId") Long merchantId) {
		boolean result = subMerchantService.approveSubmerchant(subMerchantId, merchantId);
		if (result) {
			return "success";
		} else {
			return "failure";
		}
	}

	@RequestMapping("/create")
	public String addSubMerchant(ModelMap map) {
		map.put("subMerchant", new SubMerchant());
		map.put("merchants", merchantManagementService.getAllMerchants());
		return "SubMerchant/create";
	}

	@RequestMapping("/save")
	public String saveSubMerchant(@ModelAttribute("subMerchant") SubMerchant subMerchant, ModelMap map) {
		if (subMerchant.getId() != null) {
			SubMerchant persistedSubMerchant = subMerchantService.findSubMerchantById(subMerchant.getId());
			persistedSubMerchant.setApproved(true);
			persistedSubMerchant.setMerchant(subMerchant.getMerchant());
			persistedSubMerchant.setCode(subMerchant.getCode());
			subMerchantService.saveOrUpdateSubMerchant(persistedSubMerchant);
		} else {
			subMerchant.setApproved(true);
			subMerchantService.saveOrUpdateSubMerchant(subMerchant);
		}
		map.put("message", "Sub Merchant saved Successfully");
		List<Merchant> merchants = merchantManagementService.getAllMerchants();
		List<SubMerchant> subMerchants = subMerchantService.getAllUnaprovedSubMerchants();
		map.put("submerchants", subMerchants);
		map.put("merchants", merchants);
		return "SubMerchant/view";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editSubMerchant(@PathVariable("id") Long id, ModelMap map) {
		SubMerchant subMerchant = subMerchantService.findSubMerchantById(id);
		map.put("subMerchant", subMerchant);
		map.put("merchants", merchantManagementService.getAllMerchants());
		return "SubMerchant/create";
	}

	@RequestMapping("/checkSubMerchant")
	public @ResponseBody String checkSubMerchant(@ModelAttribute("code") String code) {
		boolean result = subMerchantService.checkSubMerchant(code);
		if (result) {
			return "success";
		} else {
			return "failure";
		}
	}

}
