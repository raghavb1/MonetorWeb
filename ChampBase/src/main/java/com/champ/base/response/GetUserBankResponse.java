package com.champ.base.response;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUserBankResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1016174786577769344L;

	private List<UserBank> userBanks;

	public List<UserBank> getUserBanks() {
		return userBanks;
	}

	public void setUserBanks(List<UserBank> userBanks) {
		this.userBanks = userBanks;
	}

	@Override
	public String toString() {
		return "GetUserBankResponse [userBanks=" + userBanks + ", toString()=" + super.toString() + "]";
	}

}
