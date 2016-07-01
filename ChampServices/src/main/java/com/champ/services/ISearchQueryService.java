package com.champ.services;

import java.util.List;

import com.champ.core.entity.SearchQuery;

public interface ISearchQueryService {

	public SearchQuery getSearchQueryById(Long id);

	public void saveOrUpdateSearchQuery(SearchQuery searchQuery);

	public List<SearchQuery> getAllSearchQuery();
}
