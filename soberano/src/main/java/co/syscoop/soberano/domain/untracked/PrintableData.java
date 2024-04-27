package co.syscoop.soberano.domain.untracked;

public class PrintableData {
	private String textToPrint = "";
	private Integer printerProfile = null;
	
	public PrintableData() {};
	
	public PrintableData(String textToPrint, Integer printerProfile) {
		this.setTextToPrint(textToPrint);
		this.setPrinterProfile(printerProfile);
	}

	public String getTextToPrint() {
		return textToPrint;
	}

	public void setTextToPrint(String textToPrint) {
		this.textToPrint = textToPrint;
	}

	public Integer getPrinterProfile() {
		return printerProfile;
	}

	public void setPrinterProfile(Integer printerProfile) {
		this.printerProfile = printerProfile;
	}
}
