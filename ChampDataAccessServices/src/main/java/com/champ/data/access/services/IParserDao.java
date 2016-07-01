package com.champ.data.access.services;

import java.util.List;

import com.champ.core.entity.Parser;

public interface IParserDao {

	public Parser getParserById(Long id);

	public void saveOrUpdateParser(Parser parser);

	public List<Parser> getAllParser();
}
