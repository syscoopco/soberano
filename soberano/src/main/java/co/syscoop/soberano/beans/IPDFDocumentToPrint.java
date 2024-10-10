package co.syscoop.soberano.beans;

import java.io.IOException;
import java.util.HashMap;

public interface IPDFDocumentToPrint {
	
	public abstract void setParameters(HashMap<String, Object> parameters);
	public abstract void createPDFFile(Object objectToPrint, String fileToPrintFullPath) throws IOException;
	public abstract void printPDFFile(String fileToPrintFullPath, String printerNameParam, String jobName) throws Exception;
}
