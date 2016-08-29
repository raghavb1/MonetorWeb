package com.champ.services;

import java.util.Date;
import java.util.List;

import com.champ.core.entity.Parser;
import com.champ.gmail.api.dto.TransactionDTO;

public interface ISmsService {

	public TransactionDTO getTransactionDtoFromSms(String message, List<Parser> parsers, Date transactionDate);
}
