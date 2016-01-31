package m2dl.osgi.colorator.css.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import m2dl.osgi.editor.interfaces.Tokenizer;

public class TokenizerCSSImpl implements Tokenizer {

	private static final Map<Pattern, String> tokenizerRules = new HashMap<Pattern, String>();
	static {
		tokenizerRules.put(Pattern.compile("(\\/\\*(?:.|\\n)*?\\*\\/)", Pattern.CASE_INSENSITIVE), ":comment{$1}");
	}
	
	@Override
	public String tokenize(String textToTokenize) {
		
		StringBuffer cumulativeColorizedText = new StringBuffer(textToTokenize);
		for (Pattern tokenSelector: tokenizerRules.keySet()) {
			String replacement = tokenizerRules.get(tokenSelector);
			String previousContent = cumulativeColorizedText.toString();
			cumulativeColorizedText = new StringBuffer();
			Matcher matcher = tokenSelector.matcher(previousContent);
			while (matcher.find()) {
				matcher.appendReplacement(cumulativeColorizedText, replacement);
			}
			matcher.appendTail(cumulativeColorizedText);
		}
		
		return cumulativeColorizedText.toString();
	}

	@Override
	public Type getType() {
		return Type.CSS;
	}

}
