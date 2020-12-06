package com.myhucompiler.domain;

import java.util.List;

/**
 * This will holds information from Lexical Analyzer
 * 
 * @author Sreekanth
 *
 */
public class Lexer {
	List<String> tokenList;
	int counter;

	public List<String> getTokenList() {
		return tokenList;
	}

	public void setTokenList(List<String> tokenList) {
		this.tokenList = tokenList;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

}
