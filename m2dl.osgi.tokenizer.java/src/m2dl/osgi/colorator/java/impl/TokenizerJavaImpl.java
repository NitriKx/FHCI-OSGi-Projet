package m2dl.osgi.colorator.java.impl;

import java.util.regex.Pattern;

import m2dl.osgi.editor.interfaces.Tokenizer;

public class TokenizerJavaImpl implements Tokenizer {

	private static final Pattern javaKeywordSelector = Pattern.compile(
			"(Interger|Long|Boolean|Float|Double|int|double|float|String|long|abstract|continue|for|new|switch|assert|default|goto|package|synchronized|boolean|do|if|private|this|break|double|implements|protected|throw|byte|else|import|public|throws|case|enum|instanceof|return|transient|catch|extends|int|short|try|char|final|interface|static|void|class|finally|long|strictfp|volatile|const|float|native|super|while)");
	
	private static final Pattern javaCommentSingleLineSelector = Pattern.compile(
			"//(.*)$", Pattern.MULTILINE);
	
	private static final Pattern javaCommentMultiLineSelector = Pattern.compile(
			"/\\*[^\\*]*\\*/");
	
	@Override
	public String tokenize(String textToTokenize) {

		String step1 = javaKeywordSelector.matcher(textToTokenize).replaceAll(":keyword{$1}");
		String step2 = javaCommentSingleLineSelector.matcher(step1).replaceAll(":comment{$1}");
		String step3 = javaCommentMultiLineSelector.matcher(step2).replaceAll(":comment{$1}");
		
		return step3;
	}

	@Override
	public Type getType() {
		return Type.JAVA;
	}

}
