package com.champ.services;

import java.util.List;

import com.champ.core.entity.BankPaymentMode;

public interface IBankPaymentModeService {

	public List<BankPaymentMode> getAllBankPaymentModes();

	public void saveOrUpdateBankPaymentMode(BankPaymentMode bankPaymentMode);

	public BankPaymentMode findBankPaymentModeById(Long id);
}
