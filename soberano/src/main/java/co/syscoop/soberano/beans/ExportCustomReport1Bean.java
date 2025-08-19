package co.syscoop.soberano.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
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

public class ExportCustomReport1Bean extends ExportBean implements IExportToFile {
	
	private String relativePath = "";
		
	public ExportCustomReport1Bean(SoberanoDatasource soberanoDatasource) throws Exception {
		super(soberanoDatasource);
		relativePath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath());
	}
	
	@SuppressWarnings("unused")
	private class WorkbookData {
		public String filePath;
		public String fileFullPath;
		public Workbook wb;
	}
	
	private String formatDate(Date date, String formatStr) {
		return (new SimpleDateFormat(formatStr)).format(date);
	}
	
	private void setBordersToMergedCells(Sheet sheet, CellRangeAddress rangeAddress) {
	    RegionUtil.setBorderTop(BorderStyle.MEDIUM, rangeAddress, sheet);
	    RegionUtil.setBorderLeft(BorderStyle.MEDIUM, rangeAddress, sheet);
	    RegionUtil.setBorderRight(BorderStyle.MEDIUM, rangeAddress, sheet);
	    RegionUtil.setBorderBottom(BorderStyle.MEDIUM, rangeAddress, sheet);
	}
	
	private void addDayHeaderCells(Date day, Sheet sheet, Row dayHeader, Row dayDetailsHeader, 
								CellStyle headerStyle, int dayColumnIndex, int quantityColumnWidth,
								int amountColumnWidth) {
		Cell headerCell = dayHeader.createCell(dayColumnIndex);
		headerCell.setCellValue(formatDate(day, "yyyy-MM-dd"));
		headerCell.setCellStyle(headerStyle);
		sheet.setColumnWidth(dayColumnIndex, quantityColumnWidth);
		sheet.setColumnWidth(dayColumnIndex + 1, amountColumnWidth);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, dayColumnIndex, dayColumnIndex + 1);
		sheet.addMergedRegion(cellRangeAddress);
		setBordersToMergedCells(sheet, cellRangeAddress);
		
		headerCell = dayDetailsHeader.createCell(dayColumnIndex);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, dayColumnIndex, dayColumnIndex);
		setBordersToMergedCells(sheet, cellRangeAddress);
		
		
		headerCell = dayDetailsHeader.createCell(dayColumnIndex + 1);
		headerCell.setCellValue("Importe");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, dayColumnIndex + 1, dayColumnIndex + 1);
		setBordersToMergedCells(sheet, cellRangeAddress);
	}
		
	private final class ExportCustomReportToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public WorkbookData extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			Workbook workbook = new XSSFWorkbook();
			
			int productNameColumnWidth = 8000;
			int quantityColumnWidth = 3000;
			int amountColumnWidth = 3000;
			
			Sheet sheet = workbook.createSheet("Ventas");
			sheet.setColumnWidth(0, productNameColumnWidth);
			sheet.setColumnWidth(1, quantityColumnWidth);
			sheet.setColumnWidth(2, amountColumnWidth);
			
			//header style
			CellStyle headerStyle = workbook.createCellStyle();
			headerStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			XSSFFont font = ((XSSFWorkbook) workbook).createFont();
			font.setFontName("Arial");
			font.setFontHeightInPoints((short) 10);
			font.setBold(true);
			headerStyle.setFont(font);
			headerStyle.setAlignment(HorizontalAlignment.CENTER);
			
			Row dayHeader = sheet.createRow(0);
			Row dayDetailsHeader = sheet.createRow(1);
						
			Cell headerCell = dayDetailsHeader.createCell(0);
			headerCell.setCellValue("Producto");
			headerCell.setCellStyle(headerStyle);
			CellRangeAddress cellRangeAddress = new CellRangeAddress(1, 1, 0, 0);
			setBordersToMergedCells(sheet, cellRangeAddress);

			headerCell = dayDetailsHeader.createCell(1);
			headerCell.setCellValue("Cantidad");
			headerCell.setCellStyle(headerStyle);
			cellRangeAddress = new CellRangeAddress(1, 1, 1, 1);
			setBordersToMergedCells(sheet, cellRangeAddress);
			
			headerCell = dayDetailsHeader.createCell(2);
			headerCell.setCellValue("Importe");
			headerCell.setCellStyle(headerStyle);
			cellRangeAddress = new CellRangeAddress(1, 1, 2, 2);
			setBordersToMergedCells(sheet, cellRangeAddress);		
			
			Date day = new Date();			
			int dayColumnIndex = 3;
			for (day = from; day.compareTo(until) <=  0;) {
				
				addDayHeaderCells(day, sheet, dayHeader, dayDetailsHeader, 
								headerStyle, dayColumnIndex, quantityColumnWidth, 
								amountColumnWidth);
				dayColumnIndex = dayColumnIndex + 2;
				
				//next day
				Calendar c = Calendar.getInstance(); 
				c.setTime(day); 
				c.add(Calendar.DATE, 1);
				day = c.getTime();
			}
			
			CellStyle style = workbook.createCellStyle();
			style.setWrapText(true);
			
			CellStyle categoryHeaderStyle = workbook.createCellStyle();
			categoryHeaderStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			categoryHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			categoryHeaderStyle.setFont(font);
			categoryHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
			
			CellStyle moneyStyle = workbook.createCellStyle();
			moneyStyle.setWrapText(true);
		    moneyStyle.setDataFormat((short)8);
			
			String category = "";
			String product = "";
			
			HashMap<Integer, Double> dailyQuantities = new HashMap<Integer, Double>();
			HashMap<Integer, Double> dailyAmountAmounts = new HashMap<Integer, Double>();
			
			Double totalProducQuantity = 0.0;
			Double totalProductAmount = 0.0;			
			Double totalDayAmount = 0.0;
			
			Integer rowCount = 2;
			while (rs.next()) {
				
				if (product.isEmpty() || product.equals(rs.getString("iName"))) {
					totalProducQuantity = totalProducQuantity + rs.getDouble("iQty");
					totalProductAmount = totalProductAmount + rs.getDouble("iAmount");
					
					if (product.isEmpty()) {
						category = rs.getString("iCategoryName");
						Row row = sheet.createRow(rowCount);
						Cell cell = row.createCell(0);
						cell.setCellValue(category);
						cell.setCellStyle(categoryHeaderStyle);
						rowCount++;
					}
				}
				else {															
					Row row = sheet.createRow(rowCount);
					Cell cell = row.createCell(0);
					cell.setCellValue(product);
					cell.setCellStyle(style);
					
					long diffInMillies = Math.abs(rs.getDate("orderdate").getTime() - from.getTime());
				    long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.DAYS);		    

					cell = row.createCell(1);
					cell.setCellValue(totalProducQuantity);
					cell.setCellStyle(style);
					
					cell = row.createCell(2);
					cell.setCellValue(totalProductAmount);
					cell.setCellStyle(moneyStyle);
					
					totalProducQuantity = rs.getDouble("iQty");
					totalProductAmount = rs.getDouble("iAmount");
					
		        	rowCount++;
		        	
		        	if (!category.equals(rs.getString("iCategoryName"))) {
						category = rs.getString("iCategoryName");
						row = sheet.createRow(rowCount);
						cell = row.createCell(0);
						cell.setCellValue(category);
						cell.setCellStyle(categoryHeaderStyle);
						rowCount++;
					}
				}
				
				product = rs.getString("iName");
				
				totalDayAmount = rs.getDouble("iAmount");
		    }
			
/*
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
			
			String category = "";
			
			Integer rowCount = 1;
			while (rs.next()) {
				
				if (!rs.getString("iCategory").equals(category)) {
					category = rs.getString("iCategory");
					Row row = sheet.createRow(rowCount);
					Cell cell = row.createCell(0);
					cell.setCellValue(rs.getString("iCategory"));
					cell.setCellStyle(headerStyle);
					rowCount++;
				}
				
				Row row = sheet.createRow(rowCount);
				Cell cell = row.createCell(0);
				cell.setCellValue(rs.getString("iName"));
				cell.setCellStyle(style);

				cell = row.createCell(1);
				cell.setCellValue(rs.getDouble("iQty"));
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(rs.getDouble("iAmount"));
				cell.setCellStyle(style);				
				
	        	rowCount++;
		    }
*/			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String fromDateStr = "";
			String untilDateStr = "";
			try {
				fromDateStr = dateFormat.format(from);
				untilDateStr = dateFormat.format(until);
			} 
			catch(Exception ex) {};
			
			String xlsFileName = "custom_report_1_from_" + fromDateStr + "_to_" + untilDateStr + ".xlsx";
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
	
	private Object runCustomReportDBQuery(Date from, Date until, String costCenter) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport1_productionAndSales\"(:lang, :fromD, :untilD, :ccenter, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("ccenter", costCenter);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportCustomReportToXLSExtractor());
	}
	
	private void getCustomReportToXlsx(Date from, Date until, String costCenter) throws SQLException, IOException {
		
		WorkbookData wbd = (WorkbookData) runCustomReportDBQuery(from, until, costCenter);
					
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
			getCustomReportToXlsx((Date) getParameters().get("from"), (Date) getParameters().get("until"), (String) getParameters().get("costCenter"));
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
