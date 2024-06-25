package co.syscoop.soberano.ui.helper;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.*;

@SuppressWarnings("serial")
public class PaymentLinkWindow extends Window {
	
	public PaymentLinkWindow(String paymentProcessorName, String paymentLinkURL, String paymentLinkQRImage) {
		
		this.setTitle(Labels.getLabel("other.PaymentLinkGeneratedBy") + " " + paymentProcessorName);
		this.setBorder("normal");
		this.setSizable(true);
		this.setClosable(true);
		
		this.setHeight("100%");
		this.setHflex("1");
		
		Vbox vbox = new Vbox();
		vbox.setVflex("1");
		vbox.setHflex("1");
		vbox.setParent(this);
		
		Label lblUrl = new Label(Labels.getLabel("other.Link") + ": " + paymentLinkURL);
		lblUrl.setHflex("1");
		lblUrl.setParent(vbox);
		
		Separator sep = new Separator();
		sep.setParent(vbox);
		
		if (!paymentLinkQRImage.isEmpty()) {
			Iframe iframeQR = new Iframe(paymentLinkQRImage);
			iframeQR.setHeight("5%");
			iframeQR.setWidth("100%");
			iframeQR.setParent(vbox);
		}
		
		Separator sep1 = new Separator();
		sep1.setParent(vbox);
		
		Iframe iframeURL = new Iframe(paymentLinkURL);
		iframeURL.setVflex("1");
		iframeURL.setHflex("1");
		iframeURL.setParent(vbox);
	}
}
