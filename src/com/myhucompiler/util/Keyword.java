package com.myhucompiler.util;
/**
 * This Enum will have our compiler supported keywords
 * @author Sreekanth
 *
 */
public enum Keyword {

	INTEGER("int"),
	FLOAT("float");
	
	public final String keywordType;

    private Keyword(String keywordType) {
      this.keywordType = keywordType;
    }
}
