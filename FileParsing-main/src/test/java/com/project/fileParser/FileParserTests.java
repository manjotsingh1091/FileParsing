package com.project.fileParser;

import static org.junit.Assert.assertEquals;

import com.project.fileParser.config.AppConfigFileParser;
import com.project.fileParser.config.FileParserForXml;
import com.project.fileParser.service.FileParserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FileParserTests {

	@InjectMocks
	private FileParserServiceImpl fileParserServiceImpl;

	@Mock
	private AppConfigFileParser appConfigFileParser;

	@Mock
	private FileParserForXml fileParserForXml;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testEmptyInputFilePath() {
		try {
			Mockito.when(appConfigFileParser.getInputFilePath()).thenReturn("");
			fileParserServiceImpl.parseFileContent();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Empty input file");
		}
	}

	@Test
	public void testEmptyOutputXmlFilePath() {
		try {
			Mockito.when(appConfigFileParser.getXmlOutputFilePath()).thenReturn("");
			fileParserForXml.marshalProcess(Mockito.any());
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Empty output file");
		}
	}

}
