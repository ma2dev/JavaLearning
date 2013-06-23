package com.github.ma2dev.bcs.dataFormat;

/**
 * データフォーマット違反の場合の例外
 *
 * @author ma2dev
 *
 */
public class IllegalDataFormatException extends Exception {

	/**
	 * コンストラクタ
	 */
	public IllegalDataFormatException() {
		super();
	}

	/**
	 * コンストラクタ
	 *
	 * @param string
	 *            内容
	 */
	public IllegalDataFormatException(String string) {
		super(string);
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

}
