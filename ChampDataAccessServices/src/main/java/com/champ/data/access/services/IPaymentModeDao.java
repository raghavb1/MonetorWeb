package com.champ.data.access.services;

import java.util.List;

import com.champ.core.entity.PaymentMode;

public interface IPaymentModeDao {

	public List<PaymentMode> getAllPaymentModes();

	public PaymentMode saveOrUpdatePaymentMode(PaymentMode paymentMode);

	public PaymentMode findPaymentModeById(Long id);

}
