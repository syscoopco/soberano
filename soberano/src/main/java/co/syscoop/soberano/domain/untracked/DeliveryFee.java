package co.syscoop.soberano.domain.untracked;

import java.math.BigDecimal;

public class DeliveryFee {
	private String country = "";
	private String postalCode = "";
	private BigDecimal fee = new BigDecimal(0.0);
	
	public DeliveryFee() {};
	
	public DeliveryFee(String country, String postalCode, BigDecimal fee) {
		this.setCountry(country);
		this.setPostalCode(postalCode);
		this.setFee(fee);
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
}
