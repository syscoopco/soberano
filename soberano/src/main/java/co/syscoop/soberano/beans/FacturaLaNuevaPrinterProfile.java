package co.syscoop.soberano.beans;

import java.util.HashMap;

public class FacturaLaNuevaPrinterProfile implements IPDFDocumentToPrint {
	
	@Override
	public void setParameters(HashMap<String, Object> parameters) {		
	}

	@Override
	public void createPDFFile(Object objectToPrint, String fileToPrintFullPath) {
		return;
	}
	
	@Override
	public void printPDFFile(String fileToPrintFullPath) {
		return;
	}
}
