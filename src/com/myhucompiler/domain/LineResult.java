package com.myhucompiler.domain;

/**
 * This class is used for Line Result
 * 
 * @author Sreekanth
 *
 */
public class LineResult {

	int expressionType;
	boolean result;

	public int getExpressionType() {
		return expressionType;
	}

	public void setExpressionType(int expressionType) {
		this.expressionType = expressionType;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}
