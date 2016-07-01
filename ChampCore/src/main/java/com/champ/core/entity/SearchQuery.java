package com.champ.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "search_query")
public class SearchQuery extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6095784885218379187L;

	@Column(name = "search_query")
	private String searchQuery;

	@ManyToOne(fetch = FetchType.EAGER)
	private Bank bank;

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

}
