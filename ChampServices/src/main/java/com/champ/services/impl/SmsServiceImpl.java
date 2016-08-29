package com.champ.services.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.champ.core.entity.Parser;
import com.champ.gmail.api.dto.TransactionDTO;
import com.champ.services.IConverterService;
import com.champ.services.ISmsService;

@Service
public class SmsServiceImpl implements ISmsService {

	private static final Logger LOG = LoggerFactory.getLogger(SmsServiceImpl.class);

	@Autowired
	private IConverterService converterService;

	public TransactionDTO getTransactionDtoFromSms(String message, List<Parser> parsers, Date transactionDate) {
		TransactionDTO dto = null;
		if (!CollectionUtils.isEmpty(parsers)) {
			for (Parser parser : parsers) {
				Pattern r = Pattern.compile(parser.getTemplate());
				Matcher m = r.matcher(message);
				if (m.find()) {
					try {
						dto = converterService.getTransactionDtoFromMessage(m, transactionDate);
					} catch (ParseException e) {
						LOG.error("Exception while getting dto from message", e);
					}
					break;
				}
			}
		}
		return dto;
	}

}
