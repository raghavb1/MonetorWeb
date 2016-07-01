package com.champ.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.champ.core.entity.Parser;
import com.champ.services.IBankService;
import com.champ.services.IParserService;
import com.champ.services.ISearchQueryService;

@Controller
@RequestMapping("/Parser")
public class ParserController {

	@Autowired
	IBankService bankService;

	@Autowired
	ISearchQueryService searchQueryService;

	@Autowired
	IParserService parserService;

	@RequestMapping("/view")
	public String viewAllParser(ModelMap map) {
		map.put("parsers", parserService.getAllParser());
		return "Parser/view";
	}

	@RequestMapping("/create")
	public String addParser(ModelMap map) {
		map.put("parser", new Parser());
		map.put("banks", bankService.getAllBanks());
		map.put("searchQueries", searchQueryService.getAllSearchQuery());
		return "Parser/create";
	}

	@RequestMapping("/save")
	public String saveParser(@ModelAttribute("parser") Parser parser, ModelMap map) {
		if (parser.getId() != null) {
			Parser persistedParser = parserService.getParserById(parser.getId());
			persistedParser.setSearchQuery(parser.getSearchQuery());
			persistedParser.setBank(parser.getBank());
			parserService.saveOrUpdateParser(persistedParser);
		} else {
			parserService.saveOrUpdateParser(parser);
		}
		map.put("message", "Parser saved Successfully");
		map.put("parsers", parserService.getAllParser());
		return "Parser/view";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editSubMerchant(@PathVariable("id") Long id, ModelMap map) {
		Parser parser = parserService.getParserById(id);
		map.put("parser", parser);
		map.put("banks", bankService.getAllBanks());
		map.put("searchQueries", searchQueryService.getAllSearchQuery());
		return "Parser/create";
	}
}
