package com.champ.gmail.api.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

import org.springframework.stereotype.Service;

import com.champ.gmail.api.dto.TransactionDTO;

@Service
public class Convertor {

	public TransactionDTO getTransactionDTOFromMessage(Matcher m, String dateFormat) throws ParseException {
		TransactionDTO transaction = new TransactionDTO();
		try{
			String date = m.group("date");
			DateFormat df = new SimpleDateFormat(dateFormat);
			Date startDate = df.parse(date);
			transaction.setDate(startDate);
		}catch(Exception e){

		}

		transaction.setAmount(Double.parseDouble(m.group("amount").replace(",", "")));
		transaction.setPaymentModeString(m.group("transactionType").trim());
		transaction.setSubMerchant(m.group("merchant").trim());

		transaction.setBalance(Double.parseDouble(m.group("balance").replace(",", "")));
		return transaction;
	}
}
