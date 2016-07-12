package com.champ.core.dto;

import java.io.Serializable;

import com.champ.core.entity.Parser;
import com.champ.core.entity.SearchQuery;

public class SearchQueryParserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2081323811162945368L;

	private SearchQuery searchQuery;

	private Parser parser;

	public SearchQueryParserDto(SearchQuery searchQuery, Parser parser) {
		super();
		this.searchQuery = searchQuery;
		this.parser = parser;
	}

	public SearchQueryParserDto() {
		super();
	}

	public SearchQuery getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(SearchQuery searchQuery) {
		this.searchQuery = searchQuery;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

}
