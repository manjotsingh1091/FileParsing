package com.project.fileParser.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


//class implementing the logic for Text

@XmlRootElement(name = "text")
@XmlType(propOrder = { "sentences" })
public class Text {

	private List<Sentence> sentences = new ArrayList<Sentence>();

	@XmlElement(name = "sentence", type = Sentence.class)
	public List<Sentence> getSentences() {
		return sentences;
	}

	public void addSentences(Sentence sentence) {
		this.sentences.add(sentence);
	}

}
