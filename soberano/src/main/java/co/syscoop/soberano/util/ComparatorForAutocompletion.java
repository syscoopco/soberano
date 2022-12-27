package co.syscoop.soberano.util;

public class ComparatorForAutocompletion {

	public static int compare(String arg0, String arg1) {
		
		if (!(arg0).isEmpty()) {
			if (arg1.contains(arg0)) {
				return 0;
			}
			else {
				String[] wordArray = arg1.split(" ");
				for(String word : wordArray) {
					if (word.contains(arg0)) {
						return 0;
					}
				}
				return -1;
			}
		}
		else {
			return -1;
		}
	}
}
