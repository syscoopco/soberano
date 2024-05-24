package co.syscoop.soberano.initiators;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.models.ProductionLineBoardGridModel;
import co.syscoop.soberano.renderers.ProductionLineBoardGridRenderer;
import co.syscoop.soberano.util.ui.ZKUtilitity;

public class ProductionLineBoardInitiator implements Initiator, InitiatorExt {
	
	Integer productionLineId = 0;

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {
			Combobox cmbProductionLine = (Combobox) comps[1].query("#cmbProductionLine");					
			ZKUtilitity.setValueWOValidation(cmbProductionLine, productionLineId);
			
			ProductionLineBoardGridModel productionLineBoardGridModel = null;
			if (cmbProductionLine.getSelectedItem() != null) {
				
				//re-render the grid with the selected production line's allocations
				String orderColumn = (String) Executions.getCurrent().getSession().getAttribute("production_line_board_grid_order_column");
				String sortDirection = (String) Executions.getCurrent().getSession().getAttribute("production_line_board_grid_sort_direction");
								
				productionLineBoardGridModel = new ProductionLineBoardGridModel(((DomainObject) cmbProductionLine.getSelectedItem().getValue()).getId(),
																				orderColumn,
																				sortDirection);
				((Grid) comps[0].getPreviousSibling().query("#wndContentPanel").query("#grd")).setModel(productionLineBoardGridModel);
				Grid grd = (Grid) comps[0].getPreviousSibling().query("#wndContentPanel").query("#grd");
				grd.setModel(productionLineBoardGridModel);
				ProductionLineBoardGridRenderer productionLineBoardGridRenderer = new ProductionLineBoardGridRenderer();
				grd.setRowRenderer(productionLineBoardGridRenderer);
				if (productionLineBoardGridModel.getSize() > 0) {
					Clients.evalJavaScript("example3('square', 0.2)");
		  		}			
			}
		}
		catch(Exception ex) {
			ExceptionTreatment.log(ex);
		}		
	}
	
	@Override
	public boolean doCatch(Throwable ex) throws Exception {
		return false;
	}
	
	@Override
	public void doFinally() throws Exception {		
	}
	
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		try {
			if (ZKUtilitity.splitQuery().get("id") == null) {
				productionLineId = 0; 
			}
			else {
				productionLineId = Integer.parseInt(ZKUtilitity.splitQuery().get("id").get(0));
			}
		}
		catch(Exception ex) {
			productionLineId = 0;
			ExceptionTreatment.log(ex);
		}
	}
}
