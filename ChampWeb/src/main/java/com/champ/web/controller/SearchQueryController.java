package com.champ.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.champ.core.entity.SearchQuery;
import com.champ.services.IBankService;
import com.champ.services.ISearchQueryService;

@Controller
@RequestMapping("/SearchQuery")
public class SearchQueryController {

	@Autowired
	IBankService bankService;

	@Autowired
	ISearchQueryService searchQueryService;

	@RequestMapping("/view")
	public String viewAllSearchQuery(ModelMap map) {
		List<SearchQuery> searchQueries = searchQueryService.getAllSearchQuery();
		map.put("searchQueries", searchQueries);
		return "SearchQuery/view";
	}

	@RequestMapping("/create")
	public String addSearchQuery(ModelMap map) {
		map.put("searchQuery", new SearchQuery());
		map.put("banks", bankService.getAllBanks());
		return "SearchQuery/create";
	}

	@RequestMapping("/save")
	public String saveSearchQuery(@ModelAttribute("searchQuery") SearchQuery searchQuery, ModelMap map) {
		if (searchQuery.getId() != null) {
			SearchQuery persistedSearchQuery = searchQueryService.getSearchQueryById(searchQuery.getId());
			persistedSearchQuery.setBank(searchQuery.getBank());
			persistedSearchQuery.setSearchQuery(searchQuery.getSearchQuery());
			searchQueryService.saveOrUpdateSearchQuery(persistedSearchQuery);
		} else {
			searchQueryService.saveOrUpdateSearchQuery(searchQuery);
		}
		map.put("message", "Search Query saved Successfully");
		List<SearchQuery> searchQueries = searchQueryService.getAllSearchQuery();
		map.put("searchQueries", searchQueries);
		return "SearchQuery/view";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editSearchQuery(@PathVariable("id") Long id, ModelMap map)
	{
		SearchQuery searchQuery = searchQueryService.getSearchQueryById(id);
		map.put("searchQuery", searchQuery);
		map.put("banks", bankService.getAllBanks());
		return "SearchQuery/create";
	}
}
