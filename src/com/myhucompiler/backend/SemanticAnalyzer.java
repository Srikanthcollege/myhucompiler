package com.myhucompiler.backend;

import com.myhucompiler.util.IConstants;
import com.myhucompiler.util.Keyword;

public class SemanticAnalyzer {

	public static void analyze(String[] tokens, int expressionType ) {

		if(tokens[0].contains(IConstants.KEYWORD)) {
			int type = Integer.parseInt(tokens[0].substring(tokens[0].length()-1, tokens[0].length()));
			String s1, s2;
			if(type == 1) {
				s1 = Keyword.INTEGER.keywordType;
			} else {
				s1 = Keyword.FLOAT.keywordType;
			}
			if(expressionType == 1) {
				s2 = Keyword.INTEGER.keywordType;
			} else {
				s2 = Keyword.FLOAT.keywordType;
			}
			System.out.println("We are trying to assign " + s2 + " to " + s1 + ".");
			if(type == expressionType) {
				System.out.println("This is valid.");
			} else if(type < expressionType) {
				System.out.println("This is invalid.");
			} else {
				System.out.println("We will typecast " + s2 + " to " + s1 + ".");
			}
		}

	}
}
