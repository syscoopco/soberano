package co.syscoop.soberano.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.util.SpringUtility;

public class ExportCustomReport2Bean extends ExportBean implements IExportToFile {
	
	private String relativePath = "";
		
	public ExportCustomReport2Bean(SoberanoDatasource soberanoDatasource) throws Exception {
		super(soberanoDatasource);
		relativePath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath());
	}
	
	@SuppressWarnings("unused")
	private class WorkbookData {
		public String filePath;
		public String fileFullPath;
		public Workbook wb;
	}
	
	private final class ExportCustomReportToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public WorkbookData extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			Workbook workbook = new XSSFWorkbook();
			
			Sheet sheet = workbook.createSheet("Custom report 1");
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
				cell.setCellValue(rs.getString("iItem"));
				cell.setCellStyle(style);

				cell = row.createCell(1);
				cell.setCellValue(rs.getDouble("iQty"));
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(rs.getDouble("iAmount"));
				cell.setCellStyle(style);				
				
	        	rowCount++;
		    }
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String fromDateStr = "";
			String untilDateStr = "";
			try {
				fromDateStr = dateFormat.format(from);
				untilDateStr = dateFormat.format(until);
			} 
			catch(Exception ex) {};
			
			String xlsFileName = "custom_report_1_from_" + fromDateStr + "_to_" + untilDateStr + ".xls";
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
	
	private Object runCustomReportDBQuery(Date from, Date until) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport1\"(:lang, :fromD, :untilD, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());	
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportCustomReportToXLSExtractor());
	}
	
	private void getCustomReportToXlsx(Date from, Date until) throws SQLException, IOException {
		
		WorkbookData wbd = (WorkbookData) runCustomReportDBQuery(from, until);
					
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
			getCustomReportToXlsx((Date) getParameters().get("from"), (Date) getParameters().get("until"));
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
