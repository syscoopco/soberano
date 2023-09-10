package co.syscoop.soberano.renderers;

import java.text.SimpleDateFormat;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Button;
import org.zkoss.zul.Group;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.util.ProcessRunRowData;

public class ProcessRunsGridRenderer extends DomainObjectRowRenderer {

	public void prepareRow(Row row, Object data) {
		
		ProcessRunRowData processRunRowData = (ProcessRunRowData) data;
		
		//operation id
		row.appendChild(new Label(processRunRowData.getProcessRunId().toString()));
		
		//process
		row.appendChild(new Label(processRunRowData.getProcess()));
		
		//cost center
		row.appendChild(new Label(processRunRowData.getCostCenter()));
		
		//stage
		row.appendChild(new Label(Labels.getLabel("translation.stage." + processRunRowData.getStage())));
		
		//description
		Textbox txtDescription = new Textbox(processRunRowData.getDescription());
		txtDescription.setMultiline(true);
		txtDescription.setRows(15);
		txtDescription.setReadonly(true);		
		row.appendChild(txtDescription);
		
		//history
		Textbox txtHistory = new Textbox(processRunRowData.getHistory());
		txtHistory.setMultiline(true);
		txtHistory.setRows(15);
		txtHistory.setReadonly(true);		
		row.appendChild(txtHistory);
		
		//recording date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		row.appendChild(new Label(dateFormat.format(processRunRowData.getRecordingDate())));
				
		//action column
		Vbox actionCell = new Vbox();
		actionCell.setHflex("1");
		actionCell.setVflex("1");
		actionCell.setAlign("center");
		actionCell.setPack("center");
		
		Button btnEnd = new Button(Labels.getLabel("caption.action.end"));
		btnEnd.setWidth("90%");
		btnEnd.setDisabled(true);
		btnEnd.setId(btnEnd.getUuid());
		
		Button btnCancel = new Button(Labels.getLabel("caption.action.cancel"));
		btnCancel.setWidth("90%");
		btnCancel.setDisabled(true);
		btnCancel.setId(btnCancel.getUuid());
		
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
		
		//entity type instance id
		row.appendChild(new Intbox(processRunRowData.getEntityTypeInstanceId()));
	}
	
	@Override
	public void render(Row row, Object data, int index) throws Exception {

		if (!(row instanceof Group)) {
			prepareRow(row, data);
        }
	}
}
