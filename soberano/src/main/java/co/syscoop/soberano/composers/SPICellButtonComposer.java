package co.syscoop.soberano.composers;

import java.sql.SQLException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import co.syscoop.soberano.database.relational.SPIExtractor;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.SPI;
import co.syscoop.soberano.util.rowdata.SPIRowData;

@SuppressWarnings({ "rawtypes", "serial" })
public class SPICellButtonComposer extends SelectorComposer {
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	protected void updateSPIRow(Combobox cmbWarehouse, Intbox intAcquirableMaterialId) throws Exception, SQLException {
		
		Datebox dateShift = (Datebox) cmbWarehouse.query("#dateShift");
		Checkbox chkWithOpeningStock = (Checkbox) cmbWarehouse.query("#chkWithOpeningStock");
		Checkbox chkWithStockOnClosure = (Checkbox) cmbWarehouse.query("#chkWithStockOnClosure");
		Checkbox chkWithChanges = (Checkbox) cmbWarehouse.query("#chkWithChanges");
		Checkbox chkSurplus = (Checkbox) cmbWarehouse.query("#chkSurplus");
		
		SPIRowData spi = (SPIRowData)(new SPI(dateShift.getText(), 
								((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId(),
								intAcquirableMaterialId.getValue(), 
								chkWithOpeningStock.isChecked(),
								chkWithStockOnClosure.isChecked(),
								chkWithChanges.isChecked(),
								chkSurplus.isChecked(),
								"")).getAll("inventoryItemName", true, 1, 0, new SPIExtractor()).get(0);
		
		Row spiRow = (Row) ((Window) intAcquirableMaterialId.getParent().getParent().getParent()).getAttribute("SPIRow");
		((Decimalbox) spiRow.getChildren().get(4)).setValue(spi.getOpening());
		((Decimalbox) spiRow.getChildren().get(5)).setValue(spi.getInput());
		((Decimalbox) spiRow.getChildren().get(6)).setValue(spi.getLosses());
		((Decimalbox) spiRow.getChildren().get(7)).setValue(spi.getMovement());
		((Decimalbox) spiRow.getChildren().get(8)).setValue(spi.getAvailable());
		((Decimalbox) spiRow.getChildren().get(9)).setValue(spi.getOutput());
		((Decimalbox) spiRow.getChildren().get(10)).setValue(spi.getEnding());
	}
}