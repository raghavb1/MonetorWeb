package com.champ.data.access.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.PaymentMode;
import com.champ.data.access.services.IEntityDao;
import com.champ.data.access.services.IPaymentModeDao;

@Service("paymentModeDao")
public class PaymentModeDaoImpl implements IPaymentModeDao {

	@Autowired
	IEntityDao entityDao;

	public List<PaymentMode> getAllPaymentModes() {
		return entityDao.findAll(PaymentMode.class);
	}

	public PaymentMode saveOrUpdatePaymentMode(PaymentMode paymentMode) {
		return entityDao.saveOrUpdate(paymentMode);
	}

	public PaymentMode findPaymentModeById(Long id) {
		return entityDao.findById(PaymentMode.class, id);
	}

}
