package co.syscoop.soberano.beans;

public class WebApplicationProperties {
	
	private String vocabulary = "soberano";
	private String gtinDatabaseFilePath = "${catalina.home}/gtin.db";
	private String cashRegisterPrintTicketWhenCollectingPayment = "false";

	public String getVocabulary() {
		return vocabulary;
	}

	public void setVocabulary(String vocabulary) {
		this.vocabulary = vocabulary;
	}

	public String getGtinDatabaseFilePath() {
		return gtinDatabaseFilePath;
	}

	public void setGtinDatabaseFilePath(String gtinDatabaseFilePath) {
		this.gtinDatabaseFilePath = gtinDatabaseFilePath;
	}

	public String getCashRegisterPrintTicketWhenCollectingPayment() {
		return cashRegisterPrintTicketWhenCollectingPayment;
	}

	public void setCashRegisterPrintTicketWhenCollectingPayment(String cashRegisterPrintTicketWhenCollectingPayment) {
		this.cashRegisterPrintTicketWhenCollectingPayment = cashRegisterPrintTicketWhenCollectingPayment;
	}
}
