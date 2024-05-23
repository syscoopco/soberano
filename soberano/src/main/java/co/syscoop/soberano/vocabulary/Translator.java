package co.syscoop.soberano.vocabulary;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {
	
	public static String translate(String text) {
		
		String translatedText = text;		
		Pattern pattern = Pattern.compile("tt_(.*)_tt"); //the right PCRE2 expression is /tt_(.*)_tt/gU,
														//but doesn't work with Java 8. so, translation
														//isn't called for text with more than one group (tt_ ... _tt)
														//per paragraph. For example, object histories.
		Matcher matcher = pattern.matcher(text);
		while (matcher.find())
		{
			translatedText = translatedText.replace("tt_" + matcher.group(1) + "_tt", Labels.getLabel("translator." + matcher.group(1)));
		}	
		return translatedText;
	}
}
