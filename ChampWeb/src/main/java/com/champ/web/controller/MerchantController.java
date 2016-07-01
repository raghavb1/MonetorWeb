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

import com.champ.core.entity.Category;
import com.champ.core.entity.Merchant;
import com.champ.services.ICategoryService;
import com.champ.services.IMerchantManagementService;

@Controller
@RequestMapping("/Merchant")
public class MerchantController {

	@Autowired
	IMerchantManagementService merchantManagementService;

	@Autowired
	ICategoryService categoryService;

	@RequestMapping("/create")
	public String addMerchant(ModelMap map) {
		List<Category> categories = categoryService.getAllCategories();
		map.put("categories", categories);
		map.put("merchant", new Merchant());
		return "Merchant/create";
	}

	@RequestMapping("/save")
	public String saveMerchant(@ModelAttribute("merchant") Merchant merchant, ModelMap map) {
		if (merchant.getId() != null) {
			Merchant persistedMerchant = merchantManagementService.findMerchantById(merchant.getId());
			persistedMerchant.setCategory(merchant.getCategory());
			persistedMerchant.setEnabled(merchant.getEnabled());
			persistedMerchant.setImageUrl(merchant.getImageUrl());
			persistedMerchant.setName(merchant.getName());
			merchantManagementService.saveOrUpdateMerchant(persistedMerchant);
		}else{
			merchantManagementService.saveOrUpdateMerchant(merchant);	
		}
		map.put("message", "Merchant saved Successfully");
		List<Merchant> merchants = merchantManagementService.getAllMerchants();
		map.put("merchants", merchants);
		return "Merchant/view";
	}

	@RequestMapping("/view")
	public String viewAllMerchant(ModelMap map) {
		List<Merchant> merchants = merchantManagementService.getAllMerchants();
		map.put("merchants", merchants);
		return "Merchant/view";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editMerchant(@PathVariable("id") Long id, ModelMap map) {
		Merchant merchant = merchantManagementService.findMerchantById(id);
		List<Category> categories = categoryService.getAllCategories();
		map.put("categories", categories);
		map.put("merchant", merchant);
		map.put("edit", "true");
		return "Merchant/create";
	}

	@RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
	public String disableMerchant(@PathVariable("id") Long id, ModelMap map) {
		merchantManagementService.disableMerchant(id);
		List<Merchant> merchants = merchantManagementService.getAllMerchants();
		map.put("merchants", merchants);
		map.put("message", "Merchant disabled Successfully");
		return "Merchant/view";
	}

	@RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
	public String enableMerchant(@PathVariable("id") Long id, ModelMap map) {
		merchantManagementService.enableMerchant(id);
		List<Merchant> merchants = merchantManagementService.getAllMerchants();
		map.put("merchants", merchants);
		map.put("message", "Merchant enabled Successfully");
		return "Merchant/view";
	}

	@RequestMapping("/checkMerchant")
	public @ResponseBody String checkMerchant(@ModelAttribute("name") String name) {
		boolean result = merchantManagementService.checkMerchant(name);
		if (result) {
			return "success";
		} else {
			return "failure";
		}
	}

}
