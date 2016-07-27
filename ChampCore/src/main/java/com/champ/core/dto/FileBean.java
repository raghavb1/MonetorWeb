package com.champ.core.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class FileBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2265329525325746642L;
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
