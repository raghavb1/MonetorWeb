package com.champ.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "parser")
public class Parser extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4330083575476647064L;

	@ManyToOne(fetch = FetchType.EAGER)
	private Bank bank;

	@Column(name = "template")
	private String template;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "search_query_id", referencedColumnName = "id")
	private SearchQuery searchQuery;

	@Column(name = "date_format")
	private String dateFormat;

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public SearchQuery getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(SearchQuery searchQuery) {
		this.searchQuery = searchQuery;
	}

}
