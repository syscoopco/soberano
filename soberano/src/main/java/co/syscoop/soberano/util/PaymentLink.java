package co.syscoop.soberano.util;

public class PaymentLink {
	private String paymentLinkURL = "";
	private String paymentLinkQRImage = "";
	private Boolean openInNewWindow = false;
	
	public PaymentLink(String paymentLinkURL, String paymentLinkQRImage) {
		this.setPaymentLinkURL(paymentLinkURL);
		this.setPaymentLinkQRImage(paymentLinkQRImage);
	}

	public String getPaymentLinkURL() {
		return paymentLinkURL;
	}

	public void setPaymentLinkURL(String paymentLinkURL) {
		this.paymentLinkURL = paymentLinkURL;
	}

	public String getPaymentLinkQRImage() {
		return paymentLinkQRImage;
	}

	public void setPaymentLinkQRImage(String paymentLinkQRImage) {
		this.paymentLinkQRImage = paymentLinkQRImage;
	}

	public Boolean getOpenInNewWindow() {
		return openInNewWindow;
	}

	public void setOpenInNewWindow(Boolean openInNewWindow) {
		this.openInNewWindow = openInNewWindow;
	}
}
