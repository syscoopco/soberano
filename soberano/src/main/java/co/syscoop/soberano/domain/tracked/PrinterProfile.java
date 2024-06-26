package co.syscoop.soberano.domain.tracked;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.domain.untracked.helper.DomainObjectQualifiedMapper;
import co.syscoop.soberano.exception.SoberanoException;

public class PrinterProfile extends TrackedObject {

	private Integer font; 
	private Integer fontSize;
	private Integer pageWidth;
	private Integer pageHeight;
	private Integer margin;
	private String header = "";
	private String footer = "";
	private Boolean compactFormat = true;
	private Boolean isDefaultPrinter = true;
	private Boolean isManagementPrinter = false;
	private String printServer = "";
	private String printerName = "";
	private ArrayList<Integer> objectsUsingThisIds = new ArrayList<Integer>();
	private ArrayList<String> objectsUsingThisQualifiedNames = new ArrayList<String>();
		
	public PrinterProfile(Integer id) {
		super(id);
	}
	
	public PrinterProfile(Integer id, String name) {
		super(id, name);
	}
	
	public PrinterProfile(Integer id, 
			Integer entityTypeInstanceId, 
			String name,
			Integer fontSize,
			Integer pageWidth,
			Integer pageHeight,
			Integer margin,
			String header,
			String footer,
			Boolean compactFormat,
			Boolean isDefaultPrinter,
			Boolean isManagementPrinter,
			String printServer,
			String printerName) {
		super(id, entityTypeInstanceId, name);
		this.setQualifiedName(name + " : " + printServer);		
		this.setFontSize(fontSize);
		this.setPageWidth(pageWidth);
		this.setPageHeight(pageHeight);
		this.setMargin(margin);
		this.setHeader(header);
		this.setFooter(footer);
		this.setCompactFormat(compactFormat);
		this.setIsDefaultPrinter(isDefaultPrinter);
		this.setIsManagementPrinter(isManagementPrinter);
		this.setPrintServer(printServer);
		this.setPrinterName(printerName);
	}
	
	public PrinterProfile(Integer id, 
			Integer entityTypeInstanceId, 
			String name,
			Integer fontSize,
			Integer pageWidth,
			Integer pageHeight,
			Integer margin,
			String header,
			String footer,
			Boolean compactFormat,
			Boolean isDefaultPrinter,
			Boolean isManagementPrinter,
			String printServer,
			String printerName,
			ArrayList<Integer> objectsUsingThisIds,
			ArrayList<String> objectsUsingThisQualifiedNames) {
		this(id, 
			entityTypeInstanceId, 
			name,
			fontSize,
			pageWidth,
			pageHeight,
			margin,
			header,
			footer,
			compactFormat,
			isDefaultPrinter,
			isManagementPrinter,
			printServer,
			printerName);	
		this.objectsUsingThisIds = objectsUsingThisIds;
		this.objectsUsingThisQualifiedNames = objectsUsingThisQualifiedNames;
	}
	
	public PrinterProfile() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_PrinterProfile_getAll\"(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_PrinterProfile_create\"(:printerProfileName, "
				+ "											:fontSize, "				
				+ "											:pageWidth, "
				+ "											:pageHeight, "
				+ "											:margin, "
				+ "											:header, "
				+ "											:footer, "
				+ "											:compactFormat, "
				+ "											:isDefaultPrinter, "
				+ "											:isManagementPrinter, "
				+ "											:printServer, "
				+ "											:printerName, "
				+ "											:objectUsingThisIds, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("printerProfileName", this.getName());
		recordParameters.addValue("fontSize", this.getFontSize());
		recordParameters.addValue("pageWidth", this.getPageWidth());
		recordParameters.addValue("pageHeight", this.getPageHeight());
		recordParameters.addValue("margin", this.getMargin());
		recordParameters.addValue("header", this.getHeader());
		recordParameters.addValue("footer", this.getFooter());
		recordParameters.addValue("compactFormat", this.getCompactFormat());
		recordParameters.addValue("isDefaultPrinter", this.getIsDefaultPrinter());
		recordParameters.addValue("isManagementPrinter", this.getIsManagementPrinter());
		recordParameters.addValue("printServer", this.getPrintServer());
		recordParameters.addValue("printerName", this.getPrinterName());
		recordParameters.addValue("objectUsingThisIds", createArrayOfSQLType("integer", this.getObjectsUsingThisIds().toArray()));
		Integer qryResult = super.record();
		return qryResult > 0 ? qryResult : -1;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_PrinterProfile_modify\"(:printerProfileId,"
				+ "													:printerProfileName,"
				+ "													:fontSize,"	
				+ "													:pageWidth,"
				+ "													:pageHeight,"
				+ "													:margin,"
				+ "													:header,"
				+ "													:footer,"
				+ "													:compactFormat,"
				+ "													:isDefaultPrinter,"
				+ "													:isManagementPrinter,"
				+ "													:printServer, "
				+ "													:printerName, "
				+ "													:objectUsingThisIds,"	
				+ "													:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("printerProfileId", this.getId());
		modifyParameters.addValue("printerProfileName", this.getName());
		modifyParameters.addValue("fontSize", this.getFontSize());
		modifyParameters.addValue("pageWidth", this.getPageWidth());
		modifyParameters.addValue("pageHeight", this.getPageHeight());
		modifyParameters.addValue("margin", this.getMargin());
		modifyParameters.addValue("header", this.getHeader());
		modifyParameters.addValue("footer", this.getFooter());
		modifyParameters.addValue("compactFormat", this.getCompactFormat());
		modifyParameters.addValue("isDefaultPrinter", this.getIsDefaultPrinter());
		modifyParameters.addValue("isManagementPrinter", this.getIsManagementPrinter());
		modifyParameters.addValue("printServer", this.getPrintServer());
		modifyParameters.addValue("printerName", this.getPrinterName());	
		modifyParameters.addValue("objectUsingThisIds", createArrayOfSQLType("integer", this.getObjectsUsingThisIds().toArray()));
		Integer qryResult = super.modify();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_PrinterProfile_disable\"(:printerProfileId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("printerProfileId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
		
	public final class PrinterProfileMapper implements RowMapper<Object> {

		public PrinterProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				PrinterProfile printerProfile = null;
				int id = rs.getInt("printerProfileId");
				if (!rs.wasNull()) {
					printerProfile = new PrinterProfile(id,
														rs.getInt("entityTypeInstanceId"), 
														rs.getString("profileName"),
														rs.getInt("fontSize"),
														rs.getInt("pageWidth"),
														rs.getInt("pageHeight"),
														rs.getInt("margin"),
														rs.getString("header"),
														rs.getString("footer"),
														rs.getBoolean("compactFormat"),
														rs.getBoolean("isDefaultPrinter"),
														rs.getBoolean("isManagementPrinter"),
														rs.getString("printServer"),
														rs.getString("printerName"));
				}
				return printerProfile;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_PrinterProfile_get\"(:printerProfileId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("printerProfileId", this.getId());
		super.get(new PrinterProfileMapper());
	}
	
	public List<Object> getObjectsUsingThis() throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_PrinterProfile_getObjectsUsingThis\"(:printerProfileId, :loginname)";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("printerProfileId", this.getId());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(qryStr, parametersMap, new DomainObjectQualifiedMapper());
	}
	
	public List<Object> getPrintJobSources() throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_PrinterProfile_getPrintJobSources\"()";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		return super.query(qryStr, parametersMap, new DomainObjectQualifiedMapper());
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		PrinterProfile sourcePrinterProfile = (PrinterProfile) sourceObject;
		setId(sourcePrinterProfile.getId());
		setStringId(sourcePrinterProfile.getStringId());
		setEntityTypeInstanceId(sourcePrinterProfile.getEntityTypeInstanceId());
		setName(sourcePrinterProfile.getName());	
		setQualifiedName(sourcePrinterProfile.getQualifiedName());
		setFontSize(sourcePrinterProfile.getFontSize());
		setPageWidth(sourcePrinterProfile.getPageWidth());
		setPageHeight(sourcePrinterProfile.getPageHeight());
		setMargin(sourcePrinterProfile.getMargin());
		setHeader(sourcePrinterProfile.getHeader());
		setFooter(sourcePrinterProfile.getFooter());
		setCompactFormat(sourcePrinterProfile.getCompactFormat());
		setIsDefaultPrinter(sourcePrinterProfile.getIsDefaultPrinter());
		setIsManagementPrinter(sourcePrinterProfile.getIsManagementPrinter());
		setPrintServer(sourcePrinterProfile.getPrintServer());
		setPrinterName(sourcePrinterProfile.getPrinterName());
		setObjectsUsingThisIds(sourcePrinterProfile.getObjectsUsingThisIds());
		setObjectsUsingThisQualifiedNames(sourcePrinterProfile.getObjectsUsingThisQualifiedNames());
	}
	
	@Override
	public Integer print() throws SoberanoException {
		return null;
	}

	@Override
	public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException {
		return null;
	}
	
	@Override
	public Integer getCount() throws SQLException {
		return 0;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public Integer getPageWidth() {
		return pageWidth;
	}

	public void setPageWidth(Integer pageWidth) {
		this.pageWidth = pageWidth;
	}

	public Integer getPageHeight() {
		return pageHeight;
	}

	public void setPageHeight(Integer pageHeight) {
		this.pageHeight = pageHeight;
	}

	public Integer getMargin() {
		return margin;
	}

	public void setMargin(Integer margin) {
		this.margin = margin;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public Boolean getCompactFormat() {
		return compactFormat;
	}

	public void setCompactFormat(Boolean compactFormat) {
		this.compactFormat = compactFormat;
	}

	public Boolean getIsDefaultPrinter() {
		return isDefaultPrinter;
	}

	public void setIsDefaultPrinter(Boolean isDefaultPrinter) {
		this.isDefaultPrinter = isDefaultPrinter;
	}

	public Boolean getIsManagementPrinter() {
		return isManagementPrinter;
	}

	public void setIsManagementPrinter(Boolean isManagementPrinter) {
		this.isManagementPrinter = isManagementPrinter;
	}

	public String getPrintServer() {
		return printServer;
	}

	public void setPrintServer(String printServer) {
		this.printServer = printServer;
	}

	public ArrayList<Integer> getObjectsUsingThisIds() {
		return objectsUsingThisIds;
	}

	public void setObjectsUsingThisIds(ArrayList<Integer> objectsUsingThisIds) {
		this.objectsUsingThisIds = objectsUsingThisIds;
	}

	public ArrayList<String> getObjectsUsingThisQualifiedNames() {
		return objectsUsingThisQualifiedNames;
	}

	public void setObjectsUsingThisQualifiedNames(ArrayList<String> objectsUsingThisQualifiedNames) {
		this.objectsUsingThisQualifiedNames = objectsUsingThisQualifiedNames;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public Integer getFont() {
		return font;
	}

	public void setFont(Integer font) {
		this.font = font;
	}

	@Override
	public PrintableData getReportFull() throws SQLException {
		return null;
	}

	@Override
	public PrintableData getReportMinimal() throws SQLException {
		return null;
	}
}
