package co.syscoop.soberano.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.zkoss.util.Locales;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.database.relational.QueryStringResultMapper;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.vocabulary.Translator;

public class ExportFromShiftClosuresBean extends ExportBean implements IExportToFile {
	
	private String relativePath = "";
		
	public ExportFromShiftClosuresBean(SoberanoDatasource soberanoDatasource) throws Exception {
		super(soberanoDatasource);
		relativePath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath());
	}
	
	@SuppressWarnings("unused")
	private class WorkbookData {
		public String filePath;
		public String fileFullPath;
		public Workbook wb;
	}
	
	private final class ExportCostCenterReportToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public WorkbookData extractData(ResultSet rs) throws SQLException {
			
			String format = (String) getParameters().get("format");
			String costCenterName = (String) getParameters().get("costCenterName");
			Integer shiftClosureId = (Integer) getParameters().get("shiftClosureId");
			
			Workbook workbook = new XSSFWorkbook();
			
			Sheet sheet = workbook.createSheet(!costCenterName.isEmpty() ? costCenterName : "ALL COST CENTERS");
			sheet.setColumnWidth(0, 6000);
			sheet.setColumnWidth(1, 4000);

			Row header = sheet.createRow(0);
			
			CellStyle headerStyle = workbook.createCellStyle();
			headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			XSSFFont font = ((XSSFWorkbook) workbook).createFont();
			font.setFontName("Arial");
			font.setFontHeightInPoints((short) 16);
			font.setBold(true);
			headerStyle.setFont(font);
			
			Cell headerCell = header.createCell(0);
			headerCell.setCellValue("Item");
			headerCell.setCellStyle(headerStyle);

			headerCell = header.createCell(1);
			headerCell.setCellValue("Quantity");
			headerCell.setCellStyle(headerStyle);
			
			headerCell = header.createCell(2);
			headerCell.setCellValue("Amount");
			headerCell.setCellStyle(headerStyle);
			
			CellStyle style = workbook.createCellStyle();
			style.setWrapText(true);
			
			Integer rowCount = 1;
			while (rs.next()) {				
				Row row = sheet.createRow(rowCount);
				Cell cell = row.createCell(0);
				cell.setCellValue(rs.getString("itemName"));
				cell.setCellStyle(style);

				cell = row.createCell(1);
				cell.setCellValue(rs.getDouble("itemQtyDoublePrecision"));
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(rs.getBigDecimal("itemAmount").doubleValue());
				cell.setCellStyle(style);				
				
	        	rowCount++;
		    }
			
			Date shiftClosureDate = (Date) runGetShiftClosureDateDBQuery(shiftClosureId);
			
			String xlsFileName = "cost_center_" + (costCenterName.equals("")?"":costCenterName.replace(" ", "")) + "_" + shiftClosureDate.toString() + "." + format;
			String filePath = "/records/export/" + xlsFileName;
			String fileFullPath = relativePath + "records/export/" + xlsFileName;
			
			FileOutputStream outputStream;
			try {
				outputStream = new FileOutputStream(fileFullPath);
				workbook.write(outputStream);
				workbook.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Messagebox.show(Labels.getLabel("message.error.FileNotFound"), 
	  					org.zkoss.util.resource.Labels.getLabel("messageBoxTitle.Error"), 
						0, 
						Messagebox.ERROR);
			} catch (IOException e) {
				e.printStackTrace();
				Messagebox.show(e.getMessage(), 
	  					org.zkoss.util.resource.Labels.getLabel("messageBoxTitle.Error"), 
						0, 
						Messagebox.ERROR);
			}			
			
			WorkbookData wbd = new WorkbookData();
			wbd.filePath = filePath;
			wbd.fileFullPath = fileFullPath;
			wbd.wb = workbook;
			
			return wbd;
		}
	}
	
	private final class ShiftClosureDateExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Date extractData(ResultSet rs) throws SQLException {
			
			rs.next();
			return rs.getDate("shift");
		}
	}
	
	private String runCostCenterReportToCsvDBQuery(Integer shiftClosureId, String costCenterName) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"fn_ShiftClosure_costCenterReportToCsv\"(:lang, :closureId, :costCenterName, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());	
		qryParameters.put("closureId", shiftClosureId);
		qryParameters.put("costCenterName", costCenterName);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (String) super.query(query, qryParameters, new QueryStringResultMapper()).get(0);
	}
	
	private void getCostCenterReportToCsv(Integer shiftClosureId, String costCenterName) throws SQLException, IOException {
		
		String csv = Translator.translate(runCostCenterReportToCsvDBQuery(shiftClosureId, costCenterName));
		String csvFileName = "cost_center_" + (costCenterName.equals("")?"":costCenterName.replace(" ", "")) + "_" + shiftClosureId.toString() + ".csv";
		String fileFullPath = relativePath + "records/export/" + csvFileName;
		
		Files.write(Paths.get(fileFullPath), csv.getBytes());
					
		java.io.InputStream is = Executions.getCurrent().getDesktop().getWebApp().getResourceAsStream("/records/export/" + csvFileName);
		if (is != null) {
			Filedownload.save(is, "text/html", csvFileName);
		}
		else {
			Messagebox.show(Labels.getLabel("message.error.FileNotFound"), 
  					org.zkoss.util.resource.Labels.getLabel("messageBoxTitle.Error"), 
					0, 
					Messagebox.ERROR);			
		}
	}
	
	private Object runCostCenterReportToXlsxDBQuery(Integer shiftClosureId, String costCenterName) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_costCenter\"(:lang, :closureId, :costCenterName, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());	
		qryParameters.put("closureId", shiftClosureId);
		qryParameters.put("costCenterName", costCenterName);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportCostCenterReportToXLSExtractor());
	}
	
	private Object runGetShiftClosureDateDBQuery(Integer shiftClosureId) throws SQLException {
		
		String query = "SELECT * FROM soberano.\"fn_ShiftClosure_getTimes\"(:closureId) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("closureId", shiftClosureId);
		return super.query(query, qryParameters, new ShiftClosureDateExtractor());
	}
	
	private void getCostCenterReportToXlsx(Integer shiftClosureId, String costCenterName) throws SQLException, IOException {
		
		WorkbookData wbd = (WorkbookData) runCostCenterReportToXlsxDBQuery(shiftClosureId, costCenterName);
					
		InputStream is = Executions.getCurrent().getDesktop().getWebApp().getResourceAsStream(wbd.filePath);
		if (is != null) {
			Filedownload.save(new AMedia(new File(wbd.fileFullPath), "application/vnd.ms-excel", null));
		}
		else {
			Messagebox.show(Labels.getLabel("message.error.FileNotFound"), 
  					org.zkoss.util.resource.Labels.getLabel("messageBoxTitle.Error"), 
					0, 
					Messagebox.ERROR);		
		}
	}

	@Override
	public void export() throws Exception {
		try{
			String format = (String) getParameters().get("format");
			String shownReport = (String) getParameters().get("shownReport");
			String costCenterName = (String) getParameters().get("costCenterName");
			Integer shiftClosureId = (Integer) getParameters().get("shiftClosureId");
			
			if (shownReport.equals("receivables")) {
				
			}
			else if (shownReport.equals("cashregister")) {
				
			}
			else if (shownReport.equals("housebill")) {
				
			}
			else if (shownReport.equals("costcenter")) {
				if (format.equals("csv")) {
					getCostCenterReportToCsv(shiftClosureId, costCenterName);
				}
				else {
					getCostCenterReportToXlsx(shiftClosureId, costCenterName);
				}
			}
			else if (shownReport.equals("spi")) {
				
			}
			else if (shownReport.equals("generalfull")) {
				if (format.equals("csv")) {
					getCostCenterReportToCsv(shiftClosureId, "");
				}
				else {
					getCostCenterReportToXlsx(shiftClosureId, "");
				}
			}
			else if (shownReport.equals("salesbyprice")) {
				
			}
			else if (shownReport.equals("notes")) {
				
			}
			else if (shownReport.equals("cancellations")) {
				
			}
			else {//export general report csv
				
			}
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
						ex.getMessage(), 
						Labels.getLabel("messageBoxTitle.Error"),
						Messagebox.ERROR);
		}
	}

	@Override
	public void setParameters(HashMap<String, Object> parameters) {
		super.setParameters(parameters);
	}
}
