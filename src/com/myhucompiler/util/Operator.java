package com.myhucompiler.util;

/**
 * This Enum will have our compiler supported Operators
 * @author Sreekanth
 *
 */
public enum Operator {

	EQUALS("="),
	PLUS("+"),
	MINUS("-"),
	MUL("*"),
	DIV("/");
	
	public final String operatorType;

    private Operator(String operatorType) {
      this.operatorType = operatorType;
    }
}
