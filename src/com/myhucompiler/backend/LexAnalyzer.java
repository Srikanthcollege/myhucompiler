package com.myhucompiler.backend;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import com.myhucompiler.domain.Lexer;
import com.myhucompiler.util.IConstants;
import com.myhucompiler.util.Keyword;
import com.myhucompiler.util.Operator;
import com.myhucompiler.util.Symbol;

/**
 * This class will perform Lexical analysis, which is the first phase of a
 * compiler. It takes the modified source code from language preprocessors that
 * are written in the form of sentences. The lexical analyzer breaks these
 * syntaxes into a series of tokens, by removing any whitespace or comments in
 * the source code.
 * 
 * @author Sreekanth
 *
 */
public class LexAnalyzer {

	public static Lexer analyze(String input, int counter, Map<Integer, String> variableMap) {
		Lexer lexer = new Lexer();
		/*
		tokenList:
		KEYWORD :: Supported keywords
		IDENTIFIER :: Supported identifier/variable
		OPERATOR :: Supported Operators
		SYMBOL :: Supported Symbols
		CONSTANT :: Supported numeric constant
		*/
		List<String> tokenList = new LinkedList<>();
		StringTokenizer st = new StringTokenizer(input, " =+-*/(){},;", true);
		String token = new String();
		while(st.hasMoreTokens()) {
			token = st.nextToken();
			if(token.equals(" ")) {
				continue;
			}
			if(!isKeyword(tokenList, token)) {
				if(!isOperator(tokenList, token)) {
					if(!isSymbol(tokenList, token)) {
						try {
							// Check if token is a constant:
							int x = Integer.parseInt(token);
							tokenList.add(IConstants.CONSTANT + 1);
						} catch(NumberFormatException e1) {
							try {
								float y = Float.parseFloat(token);
								tokenList.add(IConstants.CONSTANT + 2);
							} catch(NumberFormatException e2) {
								if(Pattern.matches("[a-zA-Z]+", token)) {
									// valid identifier
									if(variableMap.containsValue(token)) {
										Iterator<Integer> iterator = variableMap.keySet().iterator();
										while(iterator.hasNext()) {
											int x = iterator.next();
											if(variableMap.get(x).equals(token)) {
												tokenList.add(IConstants.IDENTIFIER + x);
											}
										}
									} else {
										tokenList.add(IConstants.IDENTIFIER + (++counter));
										variableMap.put(counter, token);
									}
								} else {
									System.out.println("Invalid token: '" + token + "'");
									System.exit(0);
								}
							}
						}
					}
				}
			}
		}
		lexer.setCounter(counter);
		lexer.setTokenList(tokenList);
		return lexer;
}

	private static boolean isSymbol(List<String> tokenList, String token) {
		if(token.equals(Symbol.OPEN_BRACE.symbolType)) {
			tokenList.add(IConstants.SYMBOL + 1);
			return true;
		}
		if(token.equals(Symbol.CLOSE_BRACE.symbolType)) {
			tokenList.add(IConstants.SYMBOL + 2);
			return true;
		}
		if(token.equals(Symbol.SEMICOLON.symbolType)) {
			tokenList.add(IConstants.SYMBOL + 3);
			return true;
		}
		if(token.equals(Symbol.OPEN_CURLY_BRACE.symbolType)) {
			tokenList.add(IConstants.SYMBOL + 4);
			return true;
		}
		if(token.equals(Symbol.CLOSE_CURLY_BRACE.symbolType)) {
			tokenList.add(IConstants.SYMBOL + 5);
			return true;
		} 
		if(token.equals(Symbol.COMMA.symbolType)) {
			tokenList.add(IConstants.SYMBOL + 6);
			return true;
		}
		return false;
	}

	private static boolean isOperator(List<String> tokenList, String token) {
		if(token.equals(Operator.EQUALS.operatorType)) {
			tokenList.add(IConstants.OPERATOR + 1);
			return true;
		}
		if(token.equals(Operator.PLUS.operatorType)) {
			tokenList.add(IConstants.OPERATOR + 2);
			return true;
		}
		if(token.equals(Operator.MINUS.operatorType)) {
			tokenList.add(IConstants.OPERATOR + 3);
			return true;
		}
		if(token.equals(Operator.MUL.operatorType)) {
			tokenList.add(IConstants.OPERATOR + 4);
			return true;
		}
		if(token.equals(Operator.DIV.operatorType)) {
			tokenList.add(IConstants.OPERATOR + 5);
			return true;
		}
		return false;
	}

	private static boolean isKeyword(List<String> tokenList, String token) {
		if(token.equals(Keyword.INTEGER.keywordType)) {
			tokenList.add(IConstants.KEYWORD + "1");
			return true;
		}
		if(token.equals(Keyword.FLOAT.keywordType)) {
			tokenList.add(IConstants.KEYWORD + "2");
			return true;
		}
		return false;

	}
}
