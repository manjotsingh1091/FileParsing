package com.project.fileParser.config;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.project.fileParser.dto.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//class implementing logic for output in xml format

@Component
public class FileParserForXml {

	private static final Logger LOGGER = LogManager.getLogger(FileParserForXml.class);

	@Autowired
	private AppConfigFileParser appConfigFileParser;

	/**
	 * This method constructing the output xml file.
	 * 
	 * @param fileText
	 * @throws FileParsingException
	 */
	public void marshalProcess(Text fileText) throws FileParsingException {

		LOGGER.debug("Entering the marshalProcess() method in FileParserForXml..");
		String outputFilePathXml = appConfigFileParser.getXmlOutputFilePath() + "\\output.xml";

		if (outputFilePathXml.trim().isEmpty()) {
			throw new FileParsingException("Empty xml output file..");
		}

		boolean generateXml = false;

		if (appConfigFileParser.getGenXml() == 1) {
			generateXml = true;
		}

		if (generateXml) {
			try (FileWriter fileWriter = new FileWriter(outputFilePathXml); StringWriter stringWriter = new StringWriter()) {

				JAXBContext jaxbCont = JAXBContext.newInstance(Text.class);
				Marshaller jaxbMarshaller = jaxbCont.createMarshaller();
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				jaxbMarshaller.marshal(fileText, stringWriter);

				fileWriter.append(stringWriter.toString());

				LOGGER.debug("Exiting the marshalProcess() method in FileParserForXml - Successfully created Xml file in the path--" + outputFilePathXml);
			} catch (IOException e) {
				LOGGER.error("Error occurred while generating XML file");
				System.out.println("Error occurred while generating XML file " + e.getMessage());
			} catch (JAXBException e) {
				LOGGER.error("Error occurred while generating XML file");
				System.out.println("Error occurred while generating XML file " + e.getMessage());
			}
		}
	}

}
