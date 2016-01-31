package m2dl.osgi.colorator.impl;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import m2dl.osgi.editor.interfaces.Colorizer;

public class ColorizerServiceImpl implements Colorizer {

	private static final Map<Pattern, Color> colorationRules = new HashMap<Pattern, Color>();
	static {
		// @see https://regex101.com/r/mZ2kA1/1
		colorationRules.put(Pattern.compile(":comment\\{([^\\}]*)\\}"), Color.GRAY);
		colorationRules.put(Pattern.compile(":keyword\\{([^\\}]*)\\}"), Color.ORANGE);
	}
	
	@Override
	public String colorize(String tokenizedFileContent) {
		
		String cumulativeColorizedText = tokenizedFileContent;
		for (Pattern tokenSelector: colorationRules.keySet()) {
			Color associatedColor = colorationRules.get(tokenSelector);
			String associatedColorAsHex = String.format("#%02x%02x%02x", associatedColor.getRed(), associatedColor.getGreen(), associatedColor.getBlue());
			String previousContent = cumulativeColorizedText;
			cumulativeColorizedText = tokenSelector.matcher(previousContent).replaceAll(String.format("<span style=\"color: %s\">$1</span>", associatedColorAsHex));
		}
		
		String textWithBR = cumulativeColorizedText.toString().replaceAll("\n", "<br/>"); 
		return "<html><body><pre>" + textWithBR  + "</pre></body></html>";
	}

}
