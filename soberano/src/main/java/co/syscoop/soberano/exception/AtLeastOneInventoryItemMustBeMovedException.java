package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class AtLeastOneInventoryItemMustBeMovedException extends SoberanoException {
	
	public AtLeastOneInventoryItemMustBeMovedException() {
		super(SoberanoExceptionCodes.AT_LEAST_ONE_INVENTORY_ITEM_MUST_BE_MOVED);
	}

	public AtLeastOneInventoryItemMustBeMovedException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.AT_LEAST_ONE_INVENTORY_ITEM_MUST_BE_MOVED;
	}
}