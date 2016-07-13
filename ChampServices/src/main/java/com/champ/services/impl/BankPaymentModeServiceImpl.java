package com.champ.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.BankPaymentMode;
import com.champ.data.access.services.IBankPaymentDao;
import com.champ.services.IBankPaymentModeService;

@Service("bankPaymentModeService")
@Transactional
public class BankPaymentModeServiceImpl implements IBankPaymentModeService {

	@Autowired
	IBankPaymentDao bankPaymentDao;

	public List<BankPaymentMode> getAllBankPaymentModes() {
		return bankPaymentDao.getAllBankPaymentModes();
	}

	public void saveOrUpdateBankPaymentMode(BankPaymentMode bankPaymentMode) {
		bankPaymentDao.saveOrUpdateBankPaymentMode(bankPaymentMode);
	}

	public BankPaymentMode findBankPaymentModeById(Long id) {
		return bankPaymentDao.findBankPaymentModeById(id);
	}

	public List<BankPaymentMode> findBankPaymentModesByBankId(Long id) {
		return bankPaymentDao.findBankPaymentModesByBankId(id);
	}

}
