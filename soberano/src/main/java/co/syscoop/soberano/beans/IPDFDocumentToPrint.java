package co.syscoop.soberano.beans;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import co.syscoop.soberano.exception.SoberanoException;

public interface IPDFDocumentToPrint {
	
	public abstract void setParameters(HashMap<String, Object> parameters);
	public abstract void createPDFFile(Object objectToPrint, String fileToPrintFullPath) throws IOException, SQLException, SoberanoException;
	public abstract void printPDFFile(String fileToPrintFullPath, String printerNameParam, String jobName) throws Exception;
}
