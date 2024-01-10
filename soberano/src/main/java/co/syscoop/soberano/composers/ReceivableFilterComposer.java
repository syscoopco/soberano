package co.syscoop.soberano.composers;

import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.ReceivablesGridModel;
import co.syscoop.soberano.util.SQLQueryFilterParam;

@SuppressWarnings({ "serial", "rawtypes" })
public class ReceivableFilterComposer extends SelectorComposer {
	
	@Wire
	private Intbox intDelayedDays;
	
	@Wire
	private Combobox cmbCustomer;
	
	@Wire
	private Combobox cmbDebtor;
	
	@Wire
	private Checkbox chkDishonored;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	private ArrayList<SQLQueryFilterParam> getFilterParamsArray() {
		
		ArrayList<SQLQueryFilterParam> params = new ArrayList<SQLQueryFilterParam>();
		params.add(new SQLQueryFilterParam("delayedDays", intDelayedDays.getValue()));
		params.add(new SQLQueryFilterParam("custome", cmbCustomer.getSelectedItem() != null ? ((DomainObject) cmbCustomer.getSelectedItem().getValue()).getId() : null));
		params.add(new SQLQueryFilterParam("debto", cmbDebtor.getSelectedItem() != null ? ((DomainObject) cmbDebtor.getSelectedItem().getValue()).getId() : null));
		params.add(new SQLQueryFilterParam("dishonored", chkDishonored.isChecked()));
		return params;
	}
	
	private void rerenderGrid() {
		
		ReceivablesGridModel model = new ReceivablesGridModel();
		model.setFilterParams(getFilterParamsArray());
		Grid grd = (Grid) intDelayedDays.getParent().getParent().query("window").getParent().query("#wndGrid").query("#grd");
		grd.setModel(model);
		grd.setRowRenderer(grd.getRowRenderer());
		grd.invalidate();
	}
	
	@Listen("onChange = intbox#intDelayedDays")
    public void intDelayedDays_onChange() throws Throwable {
		rerenderGrid();
	}
	
	@Listen("onChange = combobox#cmbCustomer")
    public void cmbCustomer_onChange() throws Throwable {
		rerenderGrid();
	}
	
	@Listen("onChange = combobox#cmbDebtor")
    public void cmbDebtor_onChange() throws Throwable {
		rerenderGrid();
	}
	
	@Listen("onCheck = checkbox#chkDishonored")
    public void chkDishonored_onCheck() throws Throwable {
		rerenderGrid();
	}
}