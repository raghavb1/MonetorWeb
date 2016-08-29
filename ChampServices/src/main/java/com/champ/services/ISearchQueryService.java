package com.champ.services;

import java.util.List;

import com.champ.core.entity.SearchQuery;
import com.champ.core.enums.Medium;

public interface ISearchQueryService {

	public SearchQuery getSearchQueryById(Long id);

	public void saveOrUpdateSearchQuery(SearchQuery searchQuery);

	public List<SearchQuery> getAllSearchQuery();
	
	public List<SearchQuery> getSearchQueryByMedium(Medium medium);
}
