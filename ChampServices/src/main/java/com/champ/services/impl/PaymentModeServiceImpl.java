package com.champ.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.PaymentMode;
import com.champ.data.access.services.IPaymentModeDao;
import com.champ.services.IPaymentModeService;

@Service("paymentModeService")
@Transactional
public class PaymentModeServiceImpl implements IPaymentModeService {

	@Autowired
	IPaymentModeDao paymentModeDao;

	public List<PaymentMode> getAllPaymentModes() {
		return paymentModeDao.getAllPaymentModes();
	}

	public PaymentMode saveOrUpdatePaymentMode(PaymentMode paymentMode) {
		return paymentModeDao.saveOrUpdatePaymentMode(paymentMode);
	}

	public PaymentMode findPaymentModeById(Long id) {
		return paymentModeDao.findPaymentModeById(id);
	}

}
