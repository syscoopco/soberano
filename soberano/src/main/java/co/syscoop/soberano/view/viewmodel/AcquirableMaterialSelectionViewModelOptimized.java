package co.syscoop.soberano.view.viewmodel;

import java.sql.SQLException;
import org.zkoss.zul.ListModel;
import co.syscoop.soberano.view.autodrop.AcquirableMaterialSelectionSubModel;

public class AcquirableMaterialSelectionViewModelOptimized extends IntellisenseViewModel {
	
	public AcquirableMaterialSelectionViewModelOptimized() {}
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public ListModel getModel() throws SQLException {
		
		//optimization for not bringing from database the entire items set. if not, server can end up in out-of-memory. 
		return new AcquirableMaterialSelectionSubModel();
	}
}
