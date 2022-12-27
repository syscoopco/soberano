package co.syscoop.soberano.view.viewmodel;

import java.sql.SQLException;

import org.zkoss.zul.ListModel;

public interface IViewModel {
	
	@SuppressWarnings("rawtypes")
	abstract public ListModel getModel() throws SQLException;
}
