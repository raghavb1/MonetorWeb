package com.champ.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.Parser;
import com.champ.data.access.services.IParserDao;
import com.champ.services.IParserService;

@Service("parserService")
@Transactional
public class ParserServiceImpl implements IParserService {

	@Autowired
	IParserDao parserDao;

	public Parser getParserById(Long id) {
		return parserDao.getParserById(id);
	}

	public void saveOrUpdateParser(Parser parser) {
		parserDao.saveOrUpdateParser(parser);
	}

	public List<Parser> getAllParser() {
		return (List<Parser>) parserDao.getAllParser();
	}
}
