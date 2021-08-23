package com.project.fileParser.config;

//class for handling exceptions related to parsing file

public class FileParsingException extends Exception {

	private static final long serialVersionUID = 1L;

	public FileParsingException() {
		super();
	}

	public FileParsingException(String error) {
		super(error);
	}

}
