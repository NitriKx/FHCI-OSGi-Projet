package m2dl.osgi.colorator.impl;

import m2dl.osgi.editor.interfaces.Tokenizer;

public class TokenizerImpl implements Tokenizer {

	@Override
	public String tokenize(String textToTokenize) {
		return "ok";
	}

	@Override
	public Type getType() {
		return Type.JAVA;
	}

}
