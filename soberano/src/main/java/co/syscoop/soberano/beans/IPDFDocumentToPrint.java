package co.syscoop.soberano.beans;

import java.util.HashMap;

public interface IPDFDocumentToPrint {
	
	public abstract void setParameters(HashMap<String, Object> parameters);
	public abstract void createPDFFile(Object objectToPrint, String fileToPrintFullPath);
	public abstract void printPDFFile(String fileToPrintFullPath);
}
