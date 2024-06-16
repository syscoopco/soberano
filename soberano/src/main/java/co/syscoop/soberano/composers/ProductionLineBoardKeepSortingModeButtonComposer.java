package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;

import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.models.ProductionLineBoardGridModel;
import co.syscoop.soberano.util.ui.ZKUtilitity;

@SuppressWarnings({ "serial", "rawtypes" })
public class ProductionLineBoardKeepSortingModeButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnKeepSorted;
	
	@Listen("onClick = button#btnKeepSorted")
    public void btnKeepSorted_onClick() {
		
		Grid grd = (Grid) btnKeepSorted.getParent().getParent().getParent().query("#wndContentPanel").query("#grd");
		ListModel<Object> model = grd.getModel();
		Executions.getCurrent().getSession().setAttribute("production_line_board_grid_order_column", ((ProductionLineBoardGridModel) model).get_orderBy());
		if (((ProductionLineBoardGridModel) model).is_ascending()) {
			Executions.getCurrent().getSession().setAttribute("production_line_board_grid_sort_direction", "ASC");
		}
		else {
			Executions.getCurrent().getSession().setAttribute("production_line_board_grid_sort_direction", "DESC");
		}
		Integer productionLineId = 0;
		try {
			productionLineId = ZKUtilitity.getObjectIdFromURLQuery("id");
		}
		catch(Exception ex) {
			productionLineId = 0;
			ExceptionTreatment.log(ex);
		}
		Executions.sendRedirect("/production_line_board.zul?id=" + productionLineId);
    }
}