package m2dl.osgi.colorator.css.impl;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import m2dl.osgi.editor.interfaces.Tokenizer;

public class TokenizerCSSImpl implements Tokenizer {

	private static final Map<Pattern, String> tokenizerRules = new HashMap<Pattern, String>();
	static {
		tokenizerRules.put(Pattern.compile("^([^{}]+)\\{\\s*$", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE), ":keyword{$1}");
		//tokenizerRules.put(Pattern.compile("([^;\\s:]+)\\s*:\\s*[^;{}]*;?", Pattern.CASE_INSENSITIVE), ":keyword{$1}");
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

	public static void main(String[] args) {
		TokenizerCSSImpl i = new TokenizerCSSImpl();
		String file = " \n" + 
				"\n" + 
				"H1 {\n" + 
				"color: white; background: teal; FONT-FAMILY: arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 18pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"\n" + 
				"H2 {\n" + 
				"COLOR: #000000; FONT-FAMILY: verdana, helvetica, lucida-sans, sans-serif; FONT-SIZE: 14pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"\n" + 
				"H3 {\n" + 
				"COLOR: #000000; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 14pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"\n" + 
				"H4 {\n" + 
				"COLOR: #000000; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 12pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H5 {\n" + 
				"color: white; background: darkblue; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 12pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H6 {\n" + 
				"color: yellow; background: green; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 10pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"\n" + 
				"body {\n" + 
				"COLOR: #000000; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 10pt; FONT-STYLE: normal; FONT-VARIANT: normal; background-image: url('bkgnd.gif') \n" + 
				"}\n" + 
				".localheader {\n" + 
				"COLOR: #dddddd; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 15px; TEXT-DECORATION: none\n" + 
				"}\n" + 
				".locallink {\n" + 
				"COLOR: #dddddd; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 13px; FONT-WEIGHT: normal; TEXT-DECORATION: none\n" + 
				"}\n" + 
				".localsublink {\n" + 
				"COLOR: #dddddd; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 11px; FONT-WEIGHT: normal; TEXT-DECORATION: none\n" + 
				"}\n" + 
				"   .tm      	{\n" + 
				"COLOR: #000000; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 8pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"\n" + 
				"A:link .nonu {\n" + 
				"COLOR: #000000; TEXT-DECORATION: none\n" + 
				"}\n" + 
				"\n" + 
				".nonuw {\n" + 
				"COLOR: #ffffff; TEXT-DECORATION: none\n" + 
				"}\n" + 
				"A:visited .nonu {\n" + 
				"TEXT-DECORATION: none\n" + 
				"}\n" + 
				".linespace {\n" + 
				"LINE-HEIGHT: 120%\n" + 
				"}\n" + 
				".just {\n" + 
				"TEXT-ALIGN: justify\n" + 
				"}\n" + 
				"CODE {\n" + 
				"COLOR: #666666; FONT-FAMILY: courier, monaco, courier new; FONT-SIZE: 12pt\n" + 
				"}\n" + 
				"KBD {\n" + 
				"COLOR: #666666; FONT-FAMILY: courier, monaco, courier new; FONT-SIZE: 12pt\n" + 
				"}\n" + 
				"\n" + 
				"#testId { \n" + 
				"	background-color: dzkqmo;\n" + 
				"}\n" + 
				"\n" + 
				" \n" + 
				"\n" + 
				"H1 {\n" + 
				"color: white; background: teal; FONT-FAMILY: arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 18pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H2 {\n" + 
				"COLOR: #000000; FONT-FAMILY: verdana, helvetica, lucida-sans, sans-serif; FONT-SIZE: 14pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H3 {\n" + 
				"COLOR: #000000; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 14pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H4 {\n" + 
				"COLOR: #000000; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 12pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H5 {\n" + 
				"color: white; background: darkblue; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 12pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H6 {\n" + 
				"color: yellow; background: green; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 10pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"\n" + 
				"body {\n" + 
				"COLOR: #000000; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 10pt; FONT-STYLE: normal; FONT-VARIANT: normal; background-image: url('bkgnd.gif') \n" + 
				"}\n" + 
				".localheader {\n" + 
				"COLOR: #dddddd; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 15px; TEXT-DECORATION: none\n" + 
				"}\n" + 
				".locallink {\n" + 
				"COLOR: #dddddd; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 13px; FONT-WEIGHT: normal; TEXT-DECORATION: none\n" + 
				"}\n" + 
				".localsublink {\n" + 
				"COLOR: #dddddd; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 11px; FONT-WEIGHT: normal; TEXT-DECORATION: none\n" + 
				"}\n" + 
				"   .tm      	{\n" + 
				"COLOR: #000000; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 8pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"\n" + 
				"A:link .nonu {\n" + 
				"COLOR: #000000; TEXT-DECORATION: none\n" + 
				"}\n" + 
				"\n" + 
				".nonuw {\n" + 
				"COLOR: #ffffff; TEXT-DECORATION: none\n" + 
				"}\n" + 
				"A:visited .nonu {\n" + 
				"TEXT-DECORATION: none\n" + 
				"}\n" + 
				".linespace {\n" + 
				"LINE-HEIGHT: 120%\n" + 
				"}\n" + 
				".just {\n" + 
				"TEXT-ALIGN: justify\n" + 
				"}\n" + 
				"CODE {\n" + 
				"COLOR: #666666; FONT-FAMILY: courier, monaco, courier new; FONT-SIZE: 12pt\n" + 
				"}\n" + 
				"KBD {\n" + 
				"COLOR: #666666; FONT-FAMILY: courier, monaco, courier new; FONT-SIZE: 12pt\n" + 
				"}\n" + 
				"\n" + 
				"#testId { \n" + 
				"	background-color: dzkqmo;\n" + 
				"}\n" + 
				"\n" + 
				" \n" + 
				"\n" + 
				"H1 {\n" + 
				"color: white; background: teal; FONT-FAMILY: arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 18pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H2 {\n" + 
				"COLOR: #000000; FONT-FAMILY: verdana, helvetica, lucida-sans, sans-serif; FONT-SIZE: 14pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H3 {\n" + 
				"COLOR: #000000; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 14pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H4 {\n" + 
				"COLOR: #000000; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 12pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H5 {\n" + 
				"color: white; background: darkblue; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 12pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H6 {\n" + 
				"color: yellow; background: green; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 10pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"\n" + 
				"body {\n" + 
				"COLOR: #000000; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 10pt; FONT-STYLE: normal; FONT-VARIANT: normal; background-image: url('bkgnd.gif') \n" + 
				"}\n" + 
				".localheader {\n" + 
				"COLOR: #dddddd; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 15px; TEXT-DECORATION: none\n" + 
				"}\n" + 
				".locallink {\n" + 
				"COLOR: #dddddd; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 13px; FONT-WEIGHT: normal; TEXT-DECORATION: none\n" + 
				"}\n" + 
				".localsublink {\n" + 
				"COLOR: #dddddd; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 11px; FONT-WEIGHT: normal; TEXT-DECORATION: none\n" + 
				"}\n" + 
				"   .tm      	{\n" + 
				"COLOR: #000000; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 8pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"\n" + 
				"A:link .nonu {\n" + 
				"COLOR: #000000; TEXT-DECORATION: none\n" + 
				"}\n" + 
				"\n" + 
				".nonuw {\n" + 
				"COLOR: #ffffff; TEXT-DECORATION: none\n" + 
				"}\n" + 
				"A:visited .nonu {\n" + 
				"TEXT-DECORATION: none\n" + 
				"}\n" + 
				".linespace {\n" + 
				"LINE-HEIGHT: 120%\n" + 
				"}\n" + 
				".just {\n" + 
				"TEXT-ALIGN: justify\n" + 
				"}\n" + 
				"CODE {\n" + 
				"COLOR: #666666; FONT-FAMILY: courier, monaco, courier new; FONT-SIZE: 12pt\n" + 
				"}\n" + 
				"KBD {\n" + 
				"COLOR: #666666; FONT-FAMILY: courier, monaco, courier new; FONT-SIZE: 12pt\n" + 
				"}\n" + 
				"\n" + 
				"#testId { \n" + 
				"	background-color: dzkqmo;\n" + 
				"}\n" + 
				"\n" + 
				" \n" + 
				"\n" + 
				"H1 {\n" + 
				"color: white; background: teal; FONT-FAMILY: arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 18pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H2 {\n" + 
				"COLOR: #000000; FONT-FAMILY: verdana, helvetica, lucida-sans, sans-serif; FONT-SIZE: 14pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H3 {\n" + 
				"COLOR: #000000; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 14pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H4 {\n" + 
				"COLOR: #000000; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 12pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H5 {\n" + 
				"color: white; background: darkblue; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 12pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H6 {\n" + 
				"color: yellow; background: green; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 10pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"\n" + 
				"body {\n" + 
				"COLOR: #000000; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 10pt; FONT-STYLE: normal; FONT-VARIANT: normal; background-image: url('bkgnd.gif') \n" + 
				"}\n" + 
				".localheader {\n" + 
				"COLOR: #dddddd; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 15px; TEXT-DECORATION: none\n" + 
				"}\n" + 
				".locallink {\n" + 
				"COLOR: #dddddd; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 13px; FONT-WEIGHT: normal; TEXT-DECORATION: none\n" + 
				"}\n" + 
				".localsublink {\n" + 
				"COLOR: #dddddd; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 11px; FONT-WEIGHT: normal; TEXT-DECORATION: none\n" + 
				"}\n" + 
				"   .tm      	{\n" + 
				"COLOR: #000000; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 8pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"\n" + 
				"A:link .nonu {\n" + 
				"COLOR: #000000; TEXT-DECORATION: none\n" + 
				"}\n" + 
				"\n" + 
				".nonuw {\n" + 
				"COLOR: #ffffff; TEXT-DECORATION: none\n" + 
				"}\n" + 
				"A:visited .nonu {\n" + 
				"TEXT-DECORATION: none\n" + 
				"}\n" + 
				".linespace {\n" + 
				"LINE-HEIGHT: 120%\n" + 
				"}\n" + 
				".just {\n" + 
				"TEXT-ALIGN: justify\n" + 
				"}\n" + 
				"CODE {\n" + 
				"COLOR: #666666; FONT-FAMILY: courier, monaco, courier new; FONT-SIZE: 12pt\n" + 
				"}\n" + 
				"KBD {\n" + 
				"COLOR: #666666; FONT-FAMILY: courier, monaco, courier new; FONT-SIZE: 12pt\n" + 
				"}\n" + 
				"\n" + 
				"#testId { \n" + 
				"	background-color: dzkqmo;\n" + 
				"}\n" + 
				"\n" + 
				" \n" + 
				"\n" + 
				"H1 {\n" + 
				"color: white; background: teal; FONT-FAMILY: arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 18pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H2 {\n" + 
				"COLOR: #000000; FONT-FAMILY: verdana, helvetica, lucida-sans, sans-serif; FONT-SIZE: 14pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H3 {\n" + 
				"COLOR: #000000; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 14pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H4 {\n" + 
				"COLOR: #000000; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 12pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H5 {\n" + 
				"color: white; background: darkblue; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 12pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"H6 {\n" + 
				"color: yellow; background: green; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 10pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"\n" + 
				"body {\n" + 
				"COLOR: #000000; FONT-FAMILY: lucida-sans, sans-serif; FONT-SIZE: 10pt; FONT-STYLE: normal; FONT-VARIANT: normal; background-image: url('bkgnd.gif') \n" + 
				"}\n" + 
				".localheader {\n" + 
				"COLOR: #dddddd; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 15px; TEXT-DECORATION: none\n" + 
				"}\n" + 
				".locallink {\n" + 
				"COLOR: #dddddd; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 13px; FONT-WEIGHT: normal; TEXT-DECORATION: none\n" + 
				"}\n" + 
				".localsublink {\n" + 
				"COLOR: #dddddd; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 11px; FONT-WEIGHT: normal; TEXT-DECORATION: none\n" + 
				"}\n" + 
				"   .tm      	{\n" + 
				"COLOR: #000000; FONT-FAMILY: geneva, arial, helvetica, lucida-sans, sans-serif; FONT-SIZE: 8pt; FONT-STYLE: normal; FONT-VARIANT: normal\n" + 
				"}\n" + 
				"\n" + 
				"A:link .nonu {\n" + 
				"COLOR: #000000; TEXT-DECORATION: none\n" + 
				"}\n" + 
				"\n" + 
				".nonuw {\n" + 
				"COLOR: #ffffff; TEXT-DECORATION: none\n" + 
				"}\n" + 
				"A:visited .nonu {\n" + 
				"TEXT-DECORATION: none\n" + 
				"}\n" + 
				".linespace {\n" + 
				"LINE-HEIGHT: 120%\n" + 
				"}\n" + 
				".just {\n" + 
				"TEXT-ALIGN: justify\n" + 
				"}\n" + 
				"CODE {\n" + 
				"COLOR: #666666; FONT-FAMILY: courier, monaco, courier new; FONT-SIZE: 12pt\n" + 
				"}\n" + 
				"KBD {\n" + 
				"COLOR: #666666; FONT-FAMILY: courier, monaco, courier new; FONT-SIZE: 12pt\n" + 
				"}\n" + 
				"\n" + 
				"#testId { \n" + 
				"	background-color: dzkqmo;\n" + 
				"}";
		
		System.out.println(i.tokenize(file));
	}
}
