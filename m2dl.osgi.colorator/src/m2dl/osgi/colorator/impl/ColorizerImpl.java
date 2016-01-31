package m2dl.osgi.colorator.impl;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import m2dl.osgi.editor.interfaces.Colorizer;

public class ColorizerImpl implements Colorizer {

	private static final Map<Pattern, Color> colorationRules = new HashMap<Pattern, Color>();
	static {
		// @see https://regex101.com/r/mZ2kA1/1
		colorationRules.put(Pattern.compile(":comment{([^\\}]*)}", Pattern.CASE_INSENSITIVE), Color.GRAY);
		colorationRules.put(Pattern.compile(":keyword{([^\\}]*)}", Pattern.CASE_INSENSITIVE), Color.ORANGE);
	}
	
	@Override
	public String colorize(String tokenizedFileContent) {
		
		StringBuffer cumulativeColorizedText = new StringBuffer(tokenizedFileContent);
		for (Pattern tokenSelector: colorationRules.keySet()) {
			Color associatedColor = colorationRules.get(tokenSelector);
			String associatedColorAsHex = String.format("#%02x%02x%02x", associatedColor.getRed(), associatedColor.getGreen(), associatedColor.getBlue());
			String previousContent = cumulativeColorizedText.toString();
			cumulativeColorizedText = new StringBuffer();
			Matcher matcher = tokenSelector.matcher(previousContent);
			while (matcher.find()) {
				matcher.appendReplacement(cumulativeColorizedText, String.format("<span style=\"%s\">%s</span>", associatedColorAsHex, matcher.group()));
			}
			matcher.appendTail(cumulativeColorizedText);
		}
		
		return cumulativeColorizedText.toString();
	}

}
