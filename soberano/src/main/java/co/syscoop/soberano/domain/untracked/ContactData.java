package co.syscoop.soberano.domain.untracked;

public class ContactData {

	private String emailAddress = "";
	private String mobilePhoneNumber = "";
	private String Name = "";
	private String countryCode = "";
	private String address = "";
	private String postalCode = "";
	private String town = "";
	private Integer municipalityId = 0;
	private String city = "";
	private Integer provinceId = 0;
	private Double latitude = 0.0;
	private Double longitude = 0.0;
	
	public ContactData(String emailAddress,
						 String mobilePhoneNumber,
						 String Name,
						 String countryCode,
						 String address,
						 String postalCode,
						 String town,
						 Integer municipalityId,
						 String city,
						 Integer provinceId,
						 Double latitude,
						 Double longitude) {
		this.emailAddress = emailAddress.toLowerCase();;
		this.mobilePhoneNumber = mobilePhoneNumber;
		this.Name = Name;
		this.countryCode = countryCode;
		this.address = address;
		this.postalCode = postalCode;
		this.town = town;
		this.municipalityId = municipalityId;
		this.city = city;
		this.provinceId = provinceId;
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}
	
	public ContactData(ContactData contactData) {
		this(contactData.getEmailAddress(),
			contactData.getMobilePhoneNumber(),
			contactData.getName(),
			contactData.getCountryCode(),
			contactData.getAddress(),
			contactData.getPostalCode(),
			contactData.getTown(),
			contactData.getMunicipalityId(),
			contactData.getCity(),
			contactData.getProvinceId(),
			contactData.getLatitude(),
			contactData.getLongitude());
	}
	
	public String getEmailAddress() {
		return emailAddress.toLowerCase();
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress.toLowerCase();;
	}

	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public Integer getMunicipalityId() {
		return municipalityId;
	}

	public void setMunicipalityId(Integer municipalityId) {
		this.municipalityId = municipalityId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
