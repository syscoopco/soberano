package co.syscoop.soberano.composers;

import java.sql.SQLException;

import org.zkoss.zul.Include;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.ui.helper.WorkerFormHelper;

@SuppressWarnings("serial")
public class WorkerViewModelComposer extends ViewModelComposer {

	/*
	@Override
	*/	
	protected void fillTheForm(Include incDetails, Treeitem treeItem) throws SQLException {
		
		WorkerFormHelper.fillTheForm(incDetails, treeItem);
	}

}
