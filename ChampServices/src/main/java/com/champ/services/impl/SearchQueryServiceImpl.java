package com.champ.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.SearchQuery;
import com.champ.data.access.services.ISearchQueryDao;
import com.champ.services.ISearchQueryService;

@Service("searchQueryService")
@Transactional
public class SearchQueryServiceImpl implements ISearchQueryService {

	@Autowired
	ISearchQueryDao searchQueryDao;

	public SearchQuery getSearchQueryById(Long id) {
		return searchQueryDao.getSearchQueryById(id);
	}

	public void saveOrUpdateSearchQuery(SearchQuery searchQuery) {
		searchQueryDao.saveOrUpdateSearchQuery(searchQuery);
	}

	public List<SearchQuery> getAllSearchQuery() {
		return searchQueryDao.getAllSearchQuery();
	}

}
