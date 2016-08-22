package com.champ.gmail.api.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.champ.gmail.api.dto.TransactionDTO;

@Service
public class Convertor {

	private static final Logger LOG = LoggerFactory.getLogger(Convertor.class);

	public TransactionDTO getTransactionDTOFromMessage(Matcher m, String dateFormat) throws ParseException {
		TransactionDTO transaction = new TransactionDTO();
		try {
			String date = m.group("date");
			DateFormat df = new SimpleDateFormat(dateFormat);
			Date startDate = df.parse(date);
			transaction.setDate(startDate);
		} catch (Exception e) {
			LOG.error("Exception while getting transaction dto", e);
		}
		transaction.setAmount(Double.parseDouble(m.group("amount").replace(",", "")));
		transaction.setPaymentModeString(m.group("transactionType").trim());
		transaction.setSubMerchant(m.group("merchant").trim());
		transaction.setBalance(Double.parseDouble(m.group("balance").replace(",", "")));
		return transaction;
	}
}
