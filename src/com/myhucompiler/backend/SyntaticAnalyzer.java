package com.myhucompiler.backend;

import java.util.List;
import java.util.Map;

import com.myhucompiler.domain.ArthimeticResult;
import com.myhucompiler.domain.DeclarationResult;
import com.myhucompiler.domain.ExpressionResult;
import com.myhucompiler.domain.IdentifierResult;
import com.myhucompiler.domain.LineResult;
import com.myhucompiler.domain.TerminalResult;
import com.myhucompiler.util.IConstants;

/**
 * Syntax Analyzer creates the syntactic structure of the given source program.
 * This syntactic structure is mostly a parse tree. Syntax Analyzer is also
 * known as parser. The syntax analyzer (parser) checks whether a given source
 * program satisfies the rules implied by a context-free grammar or not. If it
 * satisfies, the parser creates the parse tree of that program. Otherwise the
 * parser gives the error messages.
 * 
 * @author Sreekanth
 *
 */
public class SyntaticAnalyzer {
	static int pointer;
	static int expressionType;
	public static LineResult analyze(List<String> tokenList, List<String> floatVariables, List<String> intVariables,
			Map<Integer, String> variableMap) {
		
		pointer = 0;
		String[] tokens = tokenList.toArray(new String[0]);
		LineResult lineResult = lineAnalysis(tokens, floatVariables, intVariables, variableMap);
		lineResult.setExpressionType(expressionType);
		if (lineResult.isResult()) {
			System.out.println("Valid syntax.");
		} else {
			System.out.println("Invalid syntax.");
			System.exit(0);
		}
		return lineResult;

	}

	private static LineResult lineAnalysis(String[] tokens, List<String> floatVariables,
			List<String> intVariables, Map<Integer, String> variableMap) {
		
		expressionType = 0;
		LineResult lineResult = new LineResult();
		lineResult.setExpressionType(expressionType);
		
		if ((!tokens[pointer].equals(IConstants.KEYWORD + 1)) && (!tokens[pointer].equals(IConstants.KEYWORD + 2))) {
			if (!tokens[pointer].equals(IConstants.SYMBOL + 4)) {
				lineResult.setResult(Boolean.FALSE);
				return lineResult;
			} else {
				pointer++;
				DeclarationResult declarationResult = declaration(tokens, floatVariables, intVariables, variableMap);
				if (!declarationResult.isResult()) {
					lineResult.setResult(Boolean.FALSE);
					return lineResult;
				}
				if (!tokens[pointer++].equals(IConstants.SYMBOL + 5)) {
					lineResult.setResult(Boolean.FALSE);
					return lineResult;
				}
				lineResult.setResult(Boolean.TRUE);
				return lineResult;
			}
		}
		pointer++;
		if (!tokens[pointer++].contains(IConstants.IDENTIFIER)) {
			lineResult.setResult(Boolean.FALSE);
			return lineResult;
		}
		if (!tokens[pointer++].equals(IConstants.OPERATOR + 1)) {
			lineResult.setResult(Boolean.FALSE);
			return lineResult;
		}
		ExpressionResult expressionResult = expression(tokens, variableMap, intVariables);
		if (!expressionResult.isResult()) {
			lineResult.setResult(Boolean.FALSE);
			return lineResult;
		}
		try {
			if (!tokens[pointer++].equals(IConstants.SYMBOL + 3)) {
				lineResult.setResult(Boolean.FALSE);
				return lineResult;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Missing semicolon.");
			lineResult.setResult(Boolean.FALSE);
			return lineResult;
		}
		lineResult.setResult(Boolean.TRUE);
		return lineResult;
	}

	static DeclarationResult declaration(String[] tokens, List<String> floatVariables, List<String> intVariables,
			Map<Integer, String> variableMap) {
	
		DeclarationResult declarationResult = new DeclarationResult();
		if ((!tokens[pointer].equals(IConstants.KEYWORD + 1)) && (!tokens[pointer].equals(IConstants.KEYWORD + 2))) {
			declarationResult.setResult(Boolean.FALSE);
			return declarationResult;
		}
		pointer++;
		IdentifierResult identifierResult = identifiers(
				Integer.parseInt(tokens[pointer - 1].substring(tokens[pointer].length() - 1, tokens[pointer].length())),
				tokens, floatVariables, intVariables, variableMap);
		declarationResult.setResult(identifierResult.isResult());
		return declarationResult;
	}

	static IdentifierResult identifiers(int type, String[] tokens, List<String> floatVariables,
			List<String> intVariables, Map<Integer, String> variableMap) {
		
		IdentifierResult identifierResult = new IdentifierResult();
		if (tokens[pointer].contains(IConstants.IDENTIFIER)) {
			if (type == 1) {
				intVariables.add(variableMap.get(Integer
						.parseInt(tokens[pointer].substring(tokens[pointer].length() - 1, tokens[pointer].length()))));
			} else {
				floatVariables.add(variableMap.get(Integer
						.parseInt(tokens[pointer].substring(tokens[pointer].length() - 1, tokens[pointer].length()))));

			}
			pointer++;
			int fallback = pointer;
			if (tokens[pointer++].equals(IConstants.SYMBOL + 6)) {
				IdentifierResult identifierResult1 = identifiers(type, tokens, floatVariables, intVariables, variableMap); 
				if (identifierResult1.isResult()) {
					return identifierResult1;
				}
			} else {
				pointer = fallback;
			}
			identifierResult.setResult(Boolean.TRUE);
			return identifierResult;
		}
		identifierResult.setResult(Boolean.FALSE);
		return identifierResult;
	}

	static ExpressionResult expression(String[] tokens, Map<Integer, String> variableMap, List<String> intVariables) {
		
		ExpressionResult expressionResult = new ExpressionResult();
		int fallback = pointer;
		TerminalResult terminalResult = terminal(tokens,  variableMap, intVariables);
		if (terminalResult.isResult()) {
			ArthimeticResult arthimeticResult = arithmeticOperator(tokens);
			if (arthimeticResult.isResult()) {
				ExpressionResult expressionResult2 = expression(tokens, variableMap, intVariables);
				if (expressionResult2.isResult()) {
					expressionResult.setResult(Boolean.TRUE);
					return expressionResult;
				}
			}
			expressionResult.setResult(Boolean.TRUE);
			return expressionResult;
		} else {
			if (tokens[pointer++].equals(IConstants.SYMBOL + 1)) {
				ExpressionResult expressionResult3 = expression(tokens, variableMap, intVariables);
				if (expressionResult3.isResult()) {
					if (tokens[pointer++].equals(IConstants.SYMBOL + 2)) {
						ArthimeticResult arthimeticResult = arithmeticOperator(tokens);
						if (arthimeticResult.isResult()) {
							ExpressionResult expressionResult2 = expression(tokens, variableMap, intVariables);
							if (expressionResult2.isResult()) {
								expressionResult.setResult(Boolean.TRUE);
								return expressionResult;
							}
						}
						expressionResult.setResult(Boolean.TRUE);
						return expressionResult;
					}
				}
			} else {
				pointer = fallback;
			}
		}
		expressionResult.setResult(Boolean.FALSE);
		return expressionResult;
	}
	
	static TerminalResult terminal(String[] tokens, Map<Integer, String> variableMap, List<String> intVariables) {
		
		TerminalResult terminalResult = new TerminalResult();
		if(tokens[pointer].contains(IConstants.IDENTIFIER)) {
			String variable = variableMap.get(Integer.parseInt(tokens[pointer].substring(tokens[pointer].length()-1, tokens[pointer].length())));
			if((intVariables.contains(variable)) && (expressionType <= 1)) {
				expressionType = 1;
			} else {
				if(expressionType == 1) {
					System.out.println("Type casting to float.");
				}
				expressionType = 2;
			}
			pointer++;
			terminalResult.setResult(Boolean.TRUE);
			return terminalResult;
		} else if(tokens[pointer].equals(IConstants.CONSTANT + 1)) {
			pointer++;
			if(expressionType < 1) {
				expressionType = 1;
			}
			terminalResult.setResult(Boolean.TRUE);
			return terminalResult;
		} else if(tokens[pointer].equals(IConstants.CONSTANT + 2)) {
			pointer++;
			if(expressionType == 1) {
				System.out.println("Type casting to float.");
			}
			expressionType = 2;
			terminalResult.setResult(Boolean.TRUE);
			return terminalResult;
		}
		terminalResult.setResult(Boolean.FALSE);
		return terminalResult;
	}

	static ArthimeticResult arithmeticOperator(String[] tokens) {
		ArthimeticResult arthimeticResult = new ArthimeticResult();
		if(tokens[pointer].equals(IConstants.OPERATOR + 2)) {
			pointer++;
			arthimeticResult.setResult(Boolean.TRUE);
			return arthimeticResult;
		}
		if(tokens[pointer].equals(IConstants.OPERATOR + 3)) {
			pointer++;
			arthimeticResult.setResult(Boolean.TRUE);
			return arthimeticResult;
		}
		if(tokens[pointer].equals(IConstants.OPERATOR + 4)) {
			pointer++;
			arthimeticResult.setResult(Boolean.TRUE);
			return arthimeticResult;
		}
		if(tokens[pointer].equals(IConstants.OPERATOR + 5)) {
			pointer++;
			arthimeticResult.setResult(Boolean.TRUE);
			return arthimeticResult;
		}
		arthimeticResult.setResult(Boolean.FALSE);
		return arthimeticResult;
	}
}
