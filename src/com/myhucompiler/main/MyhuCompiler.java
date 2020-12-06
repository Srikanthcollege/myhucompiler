package com.myhucompiler.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.myhucompiler.backend.LexAnalyzer;
import com.myhucompiler.backend.SemanticAnalyzer;
import com.myhucompiler.backend.SyntaticAnalyzer;
import com.myhucompiler.domain.Lexer;
import com.myhucompiler.domain.LineResult;

/**
 * This is a Toy compiler which will compile code and process synthetic, semantic analysis
 * @author Sreekanth
 *
 */
public class MyhuCompiler {

	public static void main(String[] args) {
		String filePath = args[0];
		if(null == filePath || filePath.isEmpty() || !mhFile(filePath)) {
			System.out.println("Enter Valid File Path");
		} else {
			try {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
				String input;
				int counter = 0;
				Map<Integer, String> variableMap  = new HashMap<>();
				 List<String> floatVariables = new LinkedList<>();
				 List<String> intVariables = new LinkedList<>();
				while((input = bufferedReader.readLine()) != null) {
					System.out.println(input);
					Lexer lexer = LexAnalyzer.analyze(input, counter, variableMap);
					List<String> tokenList = lexer.getTokenList();
					counter = lexer.getCounter();
					LineResult lineResult = SyntaticAnalyzer.analyze(tokenList, floatVariables, intVariables, variableMap);
					String[] tokens = tokenList.toArray(new String[0]);
					SemanticAnalyzer.analyze(tokens, lineResult.getExpressionType());
				}
			} catch (IOException e) {
				System.out.println("Enter Valid File Path");
			}

		}
	}

	private static boolean mhFile(String filePath) {
		boolean isMhFile = false;
		int index = filePath.lastIndexOf('.');
	    if(index > 0) {
	      String extension = filePath.substring(index + 1);
	      if("mh".equals(extension)) {
	    	  isMhFile = true;
	      } else {
	    	  System.out.println("Invalid File!. File extension Must be b'mh'");
	      }
	     
	    }
		return isMhFile;
	}
}
