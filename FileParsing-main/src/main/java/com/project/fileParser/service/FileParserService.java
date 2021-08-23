package com.project.fileParser.service;

import com.project.fileParser.config.FileParsingException;
import com.project.fileParser.dto.Text;
import org.springframework.stereotype.Component;

//Interface created for file parsing

@Component
public interface FileParserService {
	
	Text parseFileContent() throws FileParsingException;
	
	void generateOutputFile(Text fileText);

}
