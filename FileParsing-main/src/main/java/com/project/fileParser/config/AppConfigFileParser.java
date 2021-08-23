package com.project.fileParser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

 // Config class responsible for loading all the properties from application.properties

@PropertySource("application.properties")
@Configuration
public class AppConfigFileParser {

	@Value("${text.file.path}")
	private String inputFilePath;

	@Value("${generateXml}")
	private int generateXml;

	@Value("${generateCsv}")
	private int generateCsv;

	@Value("${xml.file.path}")
	private String xmlOutputFilePath;

	@Value("${csv.file.path}")
	private String csvOutputFilePath;

	public String getInputFilePath() {
		return inputFilePath;
	}

	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}

	public int getGenXml() {
		return generateXml;
	}

	public void setGenXml(int genXml) {
		this.generateXml = genXml;
	}

	public int getGenCsv() {
		return generateCsv;
	}

	public void setGenCsv(int genCsv) {
		this.generateCsv = genCsv;
	}

	public String getXmlOutputFilePath() {
		return xmlOutputFilePath;
	}

	public void setXmlOutputFilePath(String xmlOutputFilePath) {
		this.xmlOutputFilePath = xmlOutputFilePath;
	}

	public String getCsvOutputFilePath() {
		return csvOutputFilePath;
	}

	public void setCsvOutputFilePath(String csvOutputFilePath) {
		this.csvOutputFilePath = csvOutputFilePath;
	}



}
