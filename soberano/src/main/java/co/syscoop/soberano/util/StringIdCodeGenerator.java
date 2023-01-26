package co.syscoop.soberano.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StringIdCodeGenerator {
	
	private Map<Integer, String> intToChar = new HashMap<>();

	public StringIdCodeGenerator() {
		intToChar.put(0, "A");
		intToChar.put(1, "B");
		intToChar.put(2, "C");
		intToChar.put(3, "D");
		intToChar.put(4, "E");
		intToChar.put(5, "F");
		intToChar.put(6, "G");
		intToChar.put(7, "H");
		intToChar.put(8, "I");
		intToChar.put(9, "J");
	}
	
	public String getTenCharsRandomString(String inputString) {
		
		if (inputString.length() == 10) {
			return inputString;
		}
		else {
			Random rng = new Random();
			Integer rInt = rng.nextInt(10);
			
			//if even,
			if (rInt%2 == 0) {
				return getTenCharsRandomString(inputString + intToChar.get(rng.nextInt(10)));
			}
			else {
				return getTenCharsRandomString(inputString + new Integer(rng.nextInt(10)).toString());
			}
		}
	}
}
