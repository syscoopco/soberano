package co.syscoop.soberano.beans;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import co.syscoop.soberano.exception.SoberanoException;

public interface IDocumentToPrint {
	
	public abstract void setParameters(HashMap<String, Object> parameters);
	public abstract void createFile(Object objectToPrint, String fileToPrintFullPath) throws IOException, SQLException, SoberanoException;
	public abstract void printFile(String fileToPrintFullPath, String printerNameParam, String jobName) throws Exception;
}
