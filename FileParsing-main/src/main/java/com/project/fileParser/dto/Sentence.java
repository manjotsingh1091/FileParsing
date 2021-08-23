package com.project.fileParser.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;


 //class implementing logic for Sentence

public class Sentence {

	private String sentenceName;

	private List<String> words;

	public Sentence() {
	}

	public Sentence(String sentenceName) {
		super();
		this.sentenceName = sentenceName;
	}

	@XmlTransient
	public String getSentenceName() {
		return sentenceName;
	}

	public void setSentenceName(String sentenceName) {
		this.sentenceName = sentenceName;
	}

	@XmlElement(name = "word", type = String.class)
	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

	@Override
	public int hashCode() {
		final int prime = 23;
		int result = 1;
		result = prime * result + ((sentenceName == null) ? 0 : sentenceName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (this == object)
			return true;
		if (getClass() != object.getClass())
			return false;
		Sentence sent = (Sentence) object;
		if (sentenceName == null) {
			if (sent.sentenceName != null)
				return false;
		} else if (!sentenceName.equals(sent.sentenceName))
			return false;
		return true;
	}

}
