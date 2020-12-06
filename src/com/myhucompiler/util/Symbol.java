package com.myhucompiler.util;

/**
 * This Enum will have our compiler supported Symbols
 * @author Sreekanth
 *
 */
public enum Symbol {

	OPEN_BRACE("("),
	CLOSE_BRACE(")"),
	OPEN_CURLY_BRACE("{"),
	CLOSE_CURLY_BRACE("}"),
	SEMICOLON(";"),
	COMMA(",");
	
	public final String symbolType;

    private Symbol(String symbolType) {
      this.symbolType = symbolType;
    }

}
