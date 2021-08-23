package com.project.fileParser.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.project.fileParser.config.FileParsingException;
import com.project.fileParser.dto.Sentence;
import com.project.fileParser.dto.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.fileParser.config.AppConfigFileParser;
import com.project.fileParser.config.FileParserForXml;


// implementation class responsible for file parsing to all output formats

@Service
public class FileParserServiceImpl implements FileParserService {

	private static final Logger LOGGER = LogManager.getLogger(FileParserServiceImpl.class);

	public final static String REG_EX= "(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=\\.|\\?)\\s";

	@Autowired
	private AppConfigFileParser appConfigFileParser;

	@Autowired
	private FileParserForXml fileParserForXML;

	String header = " ";

//Method for reading the input file and parsing it to construct Text object containing sentences and words.

	@Override
	public Text parseFileContent() throws FileParsingException {

		LOGGER.debug("Entering parseFileContent() method in FileParserServiceImpl ..");

		String inputFilePath = appConfigFileParser.getInputFilePath();

		Text fileText = null;

		try {
			if (inputFilePath.trim().isEmpty()) {
				throw new FileParsingException("Empty input file");
			}

			List<String> allLines = Files.readAllLines(Paths.get(inputFilePath));

			List<String> sentences = getSentences(allLines);

			Map<Sentence, List<String>> map = getWords(sentences);

			fileText = buildText(map);

		} catch (IOException e) {
			LOGGER.debug("Error occurred while parsing the file in parseFileContent() " + e.getMessage());
			System.out.println("Error occurred while parsing the file in parseFileContent() " + e.getMessage());
		}

		LOGGER.info("Exiting parseFileContent() method in FileParserServiceImpl");

		return fileText;

	}

	/**
	 * Method is generating the Output file in CSV/XML format based on the
	 * configuration.
	 * 
	 * @param fileText
	 */
	@Override
	public void generateOutputFile(Text fileText) {

		LOGGER.debug("Entering generateOutputFile() method in FileParserServiceImpl");

		try {
			boolean createXml = false;
			if (appConfigFileParser.getGenXml() == 1) {
				createXml = true;
			}
			if (createXml) {
				fileParserForXML.marshalProcess(fileText);
			}

			boolean createCsv = false;

			if (appConfigFileParser.getGenCsv() == 1) {
				createCsv = true;
			}

			if (createCsv) {
				generateOutputCSV(fileText.getSentences());
			}

		} catch (FileParsingException e) {
			LOGGER.error("Error occurred while generating the file in generateOutputFile() " + e.getMessage());
			System.out.println("Error occurred while generating the file in generateOutputFile() " + e.getMessage());
		}
		LOGGER.debug("Exiting generateOutputFile() method in FileParserServiceImpl");
	}

	/**
	 * This method is generating sentences from the lines.
	 * 
	 * @param lines
	 * @return collection of sentences.
	 */
	private List<String> getSentences(List<String> lines) {

		LOGGER.debug("Entering getSentences() method in FileParserServiceImpl");

		List<String> sentenceFormatted = new ArrayList<>();
		List<String> linesFormatted = new ArrayList<>();

		String emptyValue = "";

		for (String line : lines) {
			String sentence[] = line.split(REG_EX);
			for (String s : sentence) {
				linesFormatted.add(s);
			}
		}

		for (String line : linesFormatted) {

			if (!line.trim().isEmpty()) {
				if (!emptyValue.trim().isEmpty()) {
					line = emptyValue + line;
					emptyValue = "";
				}

				if (!(line.endsWith("!") || line.endsWith(".") || line.endsWith("?"))) {
					emptyValue = line;
				} else {
					sentenceFormatted.add(line);
				}
			}
		}

		LOGGER.debug("Exiting getSentences() method in FileParserServiceImpl");
		return escapeSpecialCharacters(sentenceFormatted);
	}

	/**
	 * Method for removing the special characters from the sentences.
	 * 
	 * @param sentences
	 * @return sentences without special characters.
	 */
	private List<String> escapeSpecialCharacters(List<String> sentences) {

		LOGGER.debug("Entering the escapeSpecialCharacters()");

		sentences = sentences.stream().map(sentence -> {
			sentence = sentence.trim();
			sentence = sentence.replace("(", "");
			sentence = sentence.replace(")", "");
			sentence = sentence.replace(" - ", " ");
			sentence = sentence.replace(",", " ");
			sentence = sentence.replace(":", "");
			sentence = sentence.replace("  ", " ");
			sentence = sentence.replace("\\s", " ");
			sentence = sentence.replace("\t", " ");

			return sentence;

		}).collect(Collectors.toList());

		LOGGER.debug("Exiting the escapeSpecialCharacters() method in FileParserServiceImpl");
		return sentences;
	}

	/**
	 * Method for retrieving words from the sentence.
	 * 
	 * @param sentences
	 * @return Map with KEY-SentenceName;VALUE-List of words for that
	 *         sentence.
	 */
	private Map<Sentence, List<String>> getWords(List<String> sentences) {
		LOGGER.debug("Entering the getWords() methods in FileParserServiceImpl");

		Map<Sentence, List<String>> map = new LinkedHashMap<>();
		int sentenceSequenceNumber = 1;
		for (String sentence : sentences) {
			List<String> listOfWords = new ArrayList<String>();
			String[] words = sentence.split(" ");
			for (String word : words) {
				if (word.length() != 0) {
					word= word.replaceAll("[.!?]","");
					//word=word.replaceAll("[^A-Za-z0-9]","");
					listOfWords.add(word);
				}
			}
			listOfWords = listOfWords.stream().sorted((word1, word2) -> word1.compareToIgnoreCase(word2))
					.collect(Collectors.toList());

			map.put(new Sentence("Sentence " + sentenceSequenceNumber), listOfWords);

			sentenceSequenceNumber++;
		}
		LOGGER.debug("Exiting the getWords() method in FileParserServiceImpl");
		return map;
	}

	/**
	 * This method is building the Text from the input map.
	 * 
	 * @param map
	 * @return Text object
	 */
	private Text buildText(Map<Sentence, List<String>> map) {
		LOGGER.debug("Entering the buildTextPojo()");
		Text fileText = new Text();
		for (Map.Entry<Sentence, List<String>> entry : map.entrySet()) {
			Sentence sentence = new Sentence();
			sentence.setSentenceName(entry.getKey().getSentenceName());
			sentence.setWords(entry.getValue());
			fileText.addSentences(sentence);
		}
		LOGGER.debug("Exiting the buildText() method in FileParserServiceImpl");
		return fileText;
	}

	/**
	 * Method for generating the CSV file output.
	 * 
	 * @param sentenceList
	 */
	private void generateOutputCSV(List<Sentence> sentenceList) {

		LOGGER.debug("Entering the generateOutputCSV() method in FileParserServiceImpl");
		String outputFilePathCsv = appConfigFileParser.getCsvOutputFilePath() + "\\output.csv";

		try (FileWriter fileWriter = new FileWriter(new File(outputFilePathCsv));
				BufferedWriter bufferedwriter = new BufferedWriter(fileWriter)) {

			IntStream stream = IntStream.range(1, 50);
			stream.forEach(i -> {
				header = header + "," + "Word " + i;
			});

			bufferedwriter.write(header);
			bufferedwriter.newLine();

			sentenceList.forEach(sentence -> {
				String sentenceName = sentence.getSentenceName();
				String words = sentence.getWords().stream().collect(Collectors.joining(","));
				try {
					bufferedwriter.write(sentenceName + "," + words);
					bufferedwriter.newLine();
				} catch (IOException e) {
					LOGGER.error("Error generating CSV output files in generateOutputCSV()" + e.getMessage());
					System.out.println("Error occurred while creating CSV file " + e.getMessage());
				}
			});

		} catch (IOException e) {
			LOGGER.error("Error generating CSV output files in generateOutputCSV()" + e.getMessage());
			System.out.println("Error occurred while generating CSV file " + e.getMessage());
		}
		LOGGER.debug("Exiting the generateOutputCSV() in FileParserServiceImpl - Successfully generated Output file in CSV format in the path  "
				+ outputFilePathCsv);
		System.out.println("Successfully generated Output CSV file in  " + outputFilePathCsv);
	}

}
