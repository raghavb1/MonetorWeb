package com.champ.data.access.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.SearchQuery;
import com.champ.data.access.services.IEntityDao;
import com.champ.data.access.services.ISearchQueryDao;

@Service("searchQueryDao")
public class SearchQueryDaoImpl implements ISearchQueryDao {

	@Autowired
	IEntityDao entityDao;

	public SearchQuery getSearchQueryById(Long id) {
		return entityDao.findById(SearchQuery.class, id);
	}

	public void saveOrUpdateSearchQuery(SearchQuery searchQuery) {
		entityDao.saveOrUpdate(searchQuery);
	}

	public List<SearchQuery> getAllSearchQuery() {
		return (List<SearchQuery>) entityDao.findAll(SearchQuery.class);
	}

}
