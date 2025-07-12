package co.syscoop.soberano.beans;

import java.util.HashMap;

public interface IExportToFile {
	
	public abstract void setParameters(HashMap<String, Object> parameters);
	public abstract void export() throws Exception;
}
