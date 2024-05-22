package co.syscoop.soberano.renderers;

import java.text.SimpleDateFormat;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Button;
import org.zkoss.zul.Group;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.util.rowdata.CashRegisterOperationRowData;
import co.syscoop.soberano.vocabulary.Translator;

public class CashRegisterGridRenderer extends DomainObjectRowRenderer {

	public void prepareRow(Row row, Object data) {
		
		CashRegisterOperationRowData cashRegisterOperation = (CashRegisterOperationRowData) data;
		
		//operation
		row.appendChild(new Label(Translator.translate(cashRegisterOperation.getOperation())));
		
		//worker
		row.appendChild(new Label(cashRegisterOperation.getWorker()));
		
		//description
		row.appendChild(new Label(Translator.translate(cashRegisterOperation.getDescription())));
		
		//recording date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		row.appendChild(new Label(dateFormat.format(cashRegisterOperation.getRecordingDate())));
				
		//action column
		Vbox actionCell = new Vbox();
		actionCell.setHflex("1");
		actionCell.setVflex("1");
		actionCell.setAlign("center");
		actionCell.setPack("center");
		Button btnPrint = new Button(Labels.getLabel("caption.action.print"));
		btnPrint.setWidth("90%");
		btnPrint.setDisabled(true);
		btnPrint.setId(btnPrint.getUuid());
		Button btnUpload = new Button(Labels.getLabel("caption.action.upload"));
		btnUpload.setWidth("90%");
		btnUpload.setDisabled(true);
		btnUpload.setId(btnUpload.getUuid());
		Button btnDocument = new Button(Labels.getLabel("caption.action.document"));
		btnDocument.setWidth("90%");
		btnDocument.setDisabled(true);
		btnDocument.setId(btnDocument.getUuid());
			
		actionCell.appendChild(btnPrint);
		actionCell.appendChild(btnUpload);
		actionCell.appendChild(btnDocument);
		row.appendChild(actionCell);
		
		//operation id
		row.appendChild(new Intbox(cashRegisterOperation.getCashRegisterOperationId()));
		
		//entity type instance id
		row.appendChild(new Intbox(cashRegisterOperation.getEntityTypeInstanceId()));
	}
	
	@Override
	public void render(Row row, Object data, int index) throws Exception {

		if (!(row instanceof Group)) {
			prepareRow(row, data);
        }
	}
}
