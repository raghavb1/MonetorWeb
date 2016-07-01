package com.champ.data.access.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.Parser;
import com.champ.data.access.services.IEntityDao;
import com.champ.data.access.services.IParserDao;

@Service("parserDao")
public class ParserDaoImpl implements IParserDao {

	@Autowired
	IEntityDao entityDao;

	public Parser getParserById(Long id) {
		return entityDao.findById(Parser.class, id);
	}

	public void saveOrUpdateParser(Parser parser) {
		entityDao.saveOrUpdate(parser);
	}

	public List<Parser> getAllParser() {
		return (List<Parser>) entityDao.findAll(Parser.class);
	}

}
