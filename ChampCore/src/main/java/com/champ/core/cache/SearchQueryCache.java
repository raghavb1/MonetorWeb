package com.champ.core.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.champ.core.annotation.Cache;
import com.champ.core.entity.SearchQuery;

@Cache(name = "searchQueryCache")
public class SearchQueryCache {

	private Map<String, List<SearchQuery>> mediumToSearchQueries = new HashMap<String, List<SearchQuery>>();

	public void addSearchQueries(String medium, List<SearchQuery> queries) {
		mediumToSearchQueries.put(medium, queries);
	}

	public List<SearchQuery> getSearchQueriesByMedium(String medium) {
		return mediumToSearchQueries.get(medium);
	}
}
