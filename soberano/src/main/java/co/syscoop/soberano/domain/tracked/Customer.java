package co.syscoop.soberano.domain.tracked;

import java.sql.SQLException;

import co.syscoop.soberano.domain.untracked.ContactData;

public class Customer extends TrackedObject {
	
	private ContactData contactData = null;
	
	public Customer(Integer id,
					Integer entityTypeInstanceObject,
					String emailAddress,
					String mobilePhoneNumber,
					String name,
					String countryCode,
					String address,
					String postalCode,
					String town,
					Integer municipality,
					String city,
					Integer province,
					Double latitude,
					Double longitude) {
		super(id, entityTypeInstanceObject, name);
		this.setContactData(new ContactData(emailAddress,
										 mobilePhoneNumber,
										 name,
										 countryCode,
										 address,
										 postalCode,
										 town,
										 municipality,
										 city,
										 province,
										 latitude,
										 longitude));
	}

	public ContactData getContactData() {
		return contactData;
	}

	public void setContactData(ContactData contactData) {
		this.contactData = contactData;
	}

	@Override
	protected void copyFrom(Object object) {
		
		// TODO Auto-generated method stub		
	}

	@Override
	public Integer print() throws SQLException {
		// TODO print customer
		return null;
	}

	@Override
	public void get() throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
