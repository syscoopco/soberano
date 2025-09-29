package co.syscoop.soberano.util.rowdata;

public class TranslationRowData {
	private Integer translationId = 0;
	private String language = "";
	private Integer position = 0;
	private String fromTerm = "";
	private String toTerm = "";
	
	public TranslationRowData(Integer translationId) {
		this.setTranslationId(translationId);
	}
	
	public Integer getTranslationId() {
		return translationId;
	}
	
	public void setTranslationId(Integer translationId) {
		this.translationId = translationId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getFromTerm() {
		return fromTerm;
	}

	public void setFromTerm(String fromTerm) {
		this.fromTerm = fromTerm;
	}

	public String getToTerm() {
		return toTerm;
	}

	public void setToTerm(String toTerm) {
		this.toTerm = toTerm;
	}	
}
