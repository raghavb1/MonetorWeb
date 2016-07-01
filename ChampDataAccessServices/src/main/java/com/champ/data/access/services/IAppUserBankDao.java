package com.champ.data.access.services;

import java.util.List;

import com.champ.core.entity.Bank;

public interface IAppUserBankDao {

	public List<Bank> getBanksForUser(String email, String token);
}
