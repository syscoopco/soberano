package co.syscoop.soberano.vocabulary;

public class Labels {

	public static String getLabel(String key) {		
		String translation = org.zkoss.util.resource.Labels.getLabel(key);
		return translation == null ? "1) Label's translation not found; 2) system under testing; 3) Spring web application context loaded unproperly." : translation; 
	}
}
