package co.syscoop.soberano.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
	
private Workbook workbook = null;
	
	private Sheet salesSheet = null;
	private Sheet totalConsumptionSheet = null;
	private Sheet detailedConsumptionSheet = null;
	
	private XSSFFont font = null;
	private XSSFFont totalFont = null;
	
	private CellStyle style = null;
	private CellStyle categoryHeaderStyle = null;
	private CellStyle moneyStyle = null;
	private CellStyle totalStyle = null;
	private CellStyle headerStyle = null;
	private CellStyle globalTotalStyle = null;
	
	private String relativePath = "";
	
	private WorkbookData wbd = null;
	
	private void initWorkbook() throws Exception {
		
		workbook = new XSSFWorkbook();
		
		salesSheet = workbook.createSheet("Ventas");
		totalConsumptionSheet = workbook.createSheet("Consumo global");
		detailedConsumptionSheet = workbook.createSheet("Consumo por producto");
		
		font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 10);
		font.setBold(true);
		
		totalFont = ((XSSFWorkbook) workbook).createFont();
	    totalFont.setFontName("Arial");
	    totalFont.setFontHeightInPoints((short) 10);
	    totalFont.setBold(true);
		
		style = workbook.createCellStyle();
		style.setWrapText(true);
		
		categoryHeaderStyle = workbook.createCellStyle();
		categoryHeaderStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		categoryHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		categoryHeaderStyle.setFont(font);
		categoryHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
		
		moneyStyle = workbook.createCellStyle();
		moneyStyle.setWrapText(true);
	    moneyStyle.setDataFormat((short)8);
	    
	    totalStyle = workbook.createCellStyle();
	    totalStyle.setWrapText(true);
	    totalStyle.setDataFormat((short)8);
	    totalStyle.setFont(totalFont);
		
		headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setFont(font);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		
		globalTotalStyle = workbook.createCellStyle();
		globalTotalStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		globalTotalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		globalTotalStyle.setFont(font);
		globalTotalStyle.setAlignment(HorizontalAlignment.CENTER);
		globalTotalStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		globalTotalStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		globalTotalStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		globalTotalStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());		
		
		relativePath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath());
	}
		
	public ExportCustomReport1Bean(SoberanoDatasource soberanoDatasource) {
		
		super(soberanoDatasource);
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
	
	private void setBordersToMergedCells(Sheet sheet, CellRangeAddress rangeAddress, BorderStyle borderStyle) {
	    RegionUtil.setBorderTop(borderStyle, rangeAddress, sheet);
	    RegionUtil.setBorderLeft(borderStyle, rangeAddress, sheet);
	    RegionUtil.setBorderRight(borderStyle, rangeAddress, sheet);
	    RegionUtil.setBorderBottom(borderStyle, rangeAddress, sheet);
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
		setBordersToMergedCells(sheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = dayDetailsHeader.createCell(dayColumnIndex);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, dayColumnIndex, dayColumnIndex);
		setBordersToMergedCells(sheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		
		headerCell = dayDetailsHeader.createCell(dayColumnIndex + 1);
		headerCell.setCellValue("Importe");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, dayColumnIndex + 1, dayColumnIndex + 1);
		setBordersToMergedCells(sheet, cellRangeAddress, BorderStyle.MEDIUM);
	}
	
	private void initWorkbookWithSalesSheet(Date from, Date until) {
		
		int productNameColumnWidth = 8000;
		int quantityColumnWidth = 3000;
		int amountColumnWidth = 3000;
		
		salesSheet.setColumnWidth(0, productNameColumnWidth);
		salesSheet.setColumnWidth(1, quantityColumnWidth);
		salesSheet.setColumnWidth(2, amountColumnWidth);
		
		Row dayHeader = salesSheet.createRow(0);
		Row dayDetailsHeader = salesSheet.createRow(1);
					
		Cell headerCell = dayDetailsHeader.createCell(0);
		headerCell.setCellValue("Producto");
		headerCell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(1, 1, 0, 0);
		setBordersToMergedCells(salesSheet, cellRangeAddress, BorderStyle.MEDIUM);

		headerCell = dayDetailsHeader.createCell(1);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 1, 1);
		setBordersToMergedCells(salesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = dayDetailsHeader.createCell(2);
		headerCell.setCellValue("Importe");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 2, 2);
		setBordersToMergedCells(salesSheet, cellRangeAddress, BorderStyle.MEDIUM);		
		
		Date day = new Date();			
		int dayColumnIndex = 3;
		for (day = from; day.compareTo(until) <=  0;) {
			
			addDayHeaderCells(day, salesSheet, dayHeader, dayDetailsHeader, 
							headerStyle, dayColumnIndex, quantityColumnWidth, 
							amountColumnWidth);
			dayColumnIndex = dayColumnIndex + 2;
			
			//next day
			Calendar c = Calendar.getInstance(); 
			c.setTime(day); 
			c.add(Calendar.DATE, 1);
			day = c.getTime();
		}	
	}
			
	private final class ExportProductionAndSalesToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			try {
				initWorkbook();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithSalesSheet(from, until);
			
			String category = "";
			String product = "";
			
			class DayRecord {
				Double quantity = 0.0;
				Double amount = 0.0;
				int row = 0;
				int column = 0;
				public DayRecord(Double quantity, Double amount, int row, int column) {
					this.quantity = quantity;
					this.amount = amount;
					this.row = row;
					this.column = column;
				}
			};
			
			ArrayList<DayRecord> dayRecords = new ArrayList<DayRecord>();
						
			Double totalProducQuantity = 0.0;
			Double totalProductAmount = 0.0;			
			Double totalDayAmount = 0.0;
			
			Integer rowCount = 2;
			while (rs.next()) {
				
				int days = rs.getInt("days");
				
				if (product.isEmpty() || product.equals(rs.getString("iName"))) {
					totalProducQuantity = totalProducQuantity + rs.getDouble("iQty");
					totalProductAmount = totalProductAmount + rs.getDouble("iAmount");
					
					if (product.isEmpty()) {
						category = rs.getString("iCategoryName");
						Row row = salesSheet.createRow(rowCount);
						Cell cell = row.createCell(0);
						cell.setCellValue(category);
						cell.setCellStyle(categoryHeaderStyle);
						rowCount++;
					}
					
					dayRecords.add(new DayRecord(rs.getDouble("iQty"), rs.getDouble("iAmount"), rowCount, days * 2 + 3));
				}
				else {															
					Row row = salesSheet.createRow(rowCount);
					Cell cell = row.createCell(0);
					cell.setCellValue(product);
					cell.setCellStyle(style);
					
					cell = row.createCell(1);
					cell.setCellValue(totalProducQuantity);
					cell.setCellStyle(style);
					
					cell = row.createCell(2);
					cell.setCellValue(totalProductAmount);
					cell.setCellStyle(moneyStyle);
					
					rowCount++;
					
					totalProducQuantity = rs.getDouble("iQty");
					totalProductAmount = rs.getDouble("iAmount");
					
					if (!category.equals(rs.getString("iCategoryName"))) {
						category = rs.getString("iCategoryName");
						row = salesSheet.createRow(rowCount);
						cell = row.createCell(0);
						cell.setCellValue(category);
						cell.setCellStyle(categoryHeaderStyle);
						rowCount++;
					}
					
					dayRecords.add(new DayRecord(rs.getDouble("iQty"), rs.getDouble("iAmount"), rowCount, days * 2 + 3));
				}
				
				product = rs.getString("iName");
				
				totalDayAmount = totalDayAmount + rs.getDouble("iAmount");
				
				if (rs.isLast()) {
																				
					Row row = salesSheet.createRow(rowCount);
					Cell cell = row.createCell(0);
					cell.setCellValue(product);
					cell.setCellStyle(style);
					
					cell = row.createCell(1);
					cell.setCellValue(totalProducQuantity);
					cell.setCellStyle(style);
					
					cell = row.createCell(2);
					cell.setCellValue(totalProductAmount);
					cell.setCellStyle(moneyStyle);
					
					rowCount++;
				}
		    }
			
			for (DayRecord dayRecord : dayRecords) {
				Cell cell = (salesSheet.getRow(dayRecord.row)).createCell(dayRecord.column);
				cell.setCellValue(dayRecord.quantity);
				cell.setCellStyle(style);
				
				cell = (salesSheet.getRow(dayRecord.row)).createCell(dayRecord.column + 1);
				cell.setCellValue(dayRecord.amount);
				cell.setCellStyle(moneyStyle);
			}

			Row row = salesSheet.createRow(rowCount);
			
			Cell cell = row.createCell(1);
			cell.setCellValue("TOTAL:");
			cell.setCellStyle(globalTotalStyle);
			CellRangeAddress cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 1, 1);
			setBordersToMergedCells(salesSheet, cellRangeAddress, BorderStyle.THIN);
			
			cell = row.createCell(2);
			cell.setCellValue(totalDayAmount);
			cell.setCellStyle(totalStyle);
/*						
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
			
			wbd = new WorkbookData();
			wbd.filePath = filePath;
			wbd.fileFullPath = fileFullPath;
			wbd.wb = workbook;
*/			
			return null;
		}
	}
	
	private void initWorkbookWithTotalConsumptionSheet(Date from, Date until) {
		
		int productNameColumnWidth = 8000;
		int quantityColumnWidth = 3000;
		int unitColumnWidth = 3000;
		int costColumnWidth = 3000;
		
		totalConsumptionSheet.setColumnWidth(0, productNameColumnWidth);
		totalConsumptionSheet.setColumnWidth(1, quantityColumnWidth);
		totalConsumptionSheet.setColumnWidth(2, unitColumnWidth);
		totalConsumptionSheet.setColumnWidth(3, costColumnWidth);
		
		Row dayHeader = totalConsumptionSheet.createRow(0);
		Row dayDetailsHeader = totalConsumptionSheet.createRow(1);
					
		Cell headerCell = dayDetailsHeader.createCell(0);
		headerCell.setCellValue("Producto");
		headerCell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(1, 1, 0, 0);
		setBordersToMergedCells(totalConsumptionSheet, cellRangeAddress, BorderStyle.MEDIUM);

		headerCell = dayDetailsHeader.createCell(1);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 1, 1);
		setBordersToMergedCells(totalConsumptionSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = dayDetailsHeader.createCell(2);
		headerCell.setCellValue("Unidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 2, 2);
		setBordersToMergedCells(totalConsumptionSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = dayDetailsHeader.createCell(3);
		headerCell.setCellValue("Costo");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 3, 3);
		setBordersToMergedCells(totalConsumptionSheet, cellRangeAddress, BorderStyle.MEDIUM);		
		
		Date day = new Date();			
		int dayColumnIndex = 4;
		for (day = from; day.compareTo(until) <=  0;) {
			
			addDayHeaderCells(day, totalConsumptionSheet, dayHeader, dayDetailsHeader, 
							headerStyle, dayColumnIndex, quantityColumnWidth, costColumnWidth);
			dayColumnIndex = dayColumnIndex + 2;
			
			//next day
			Calendar c = Calendar.getInstance(); 
			c.setTime(day); 
			c.add(Calendar.DATE, 1);
			day = c.getTime();
		}	
	}
	
	private void initWorkbookWithDetailedConsumptionSheet(Date from, Date until) {
		
		int productNameColumnWidth = 8000;
		int quantityColumnWidth = 3000;
		int unitColumnWidth = 3000;
		int costColumnWidth = 3000;
		
		detailedConsumptionSheet.setColumnWidth(0, productNameColumnWidth);
		detailedConsumptionSheet.setColumnWidth(1, quantityColumnWidth);
		detailedConsumptionSheet.setColumnWidth(2, unitColumnWidth);
		detailedConsumptionSheet.setColumnWidth(3, costColumnWidth);
		
		Row dayHeader = detailedConsumptionSheet.createRow(0);
		Row dayDetailsHeader = detailedConsumptionSheet.createRow(1);
					
		Cell headerCell = dayDetailsHeader.createCell(0);
		headerCell.setCellValue("Producto");
		headerCell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(1, 1, 0, 0);
		setBordersToMergedCells(detailedConsumptionSheet, cellRangeAddress, BorderStyle.MEDIUM);

		headerCell = dayDetailsHeader.createCell(1);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 1, 1);
		setBordersToMergedCells(detailedConsumptionSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = dayDetailsHeader.createCell(2);
		headerCell.setCellValue("Unidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 2, 2);
		setBordersToMergedCells(detailedConsumptionSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = dayDetailsHeader.createCell(3);
		headerCell.setCellValue("Costo");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 3, 3);
		setBordersToMergedCells(detailedConsumptionSheet, cellRangeAddress, BorderStyle.MEDIUM);		
		
		Date day = new Date();			
		int dayColumnIndex = 4;
		for (day = from; day.compareTo(until) <=  0;) {
			
			addDayHeaderCells(day, detailedConsumptionSheet, dayHeader, dayDetailsHeader, 
							headerStyle, dayColumnIndex, quantityColumnWidth, costColumnWidth);
			dayColumnIndex = dayColumnIndex + 2;
			
			//next day
			Calendar c = Calendar.getInstance(); 
			c.setTime(day); 
			c.add(Calendar.DATE, 1);
			day = c.getTime();
		}	
	}
	
	private void extract(Date from, Date until, ResultSet rs, Sheet sheet, String categoryColumnName) throws SQLException {
		
		String costCenter = "";
		String product = "";
		String unit = "";
		
		class DayRecord {
			Double quantity = 0.0;
			Double cost = 0.0;
			int row = 0;
			int column = 0;
			public DayRecord(Double quantity, Double cost, int row, int column) {
				this.quantity = quantity;
				this.cost = cost;
				this.row = row;
				this.column = column;
			}
		};
		
		ArrayList<DayRecord> dayRecords = new ArrayList<DayRecord>();
					
		Double totalProducQuantity = 0.0;
		Double totalProductCost = 0.0;			
		Double totalDayCost = 0.0;
		
		Integer rowCount = 2;
		while (rs.next()) {
			
			int days = rs.getInt("days");
			
			if (product.isEmpty() || product.equals(rs.getString("iName"))) {
				totalProducQuantity = totalProducQuantity + rs.getDouble("iQty");
				totalProductCost = totalProductCost + rs.getDouble("iAmount");
				
				if (product.isEmpty()) {
					costCenter = rs.getString(categoryColumnName);
					Row row = sheet.createRow(rowCount);
					Cell cell = row.createCell(0);
					cell.setCellValue(costCenter);
					cell.setCellStyle(categoryHeaderStyle);
					rowCount++;
				}
				
				dayRecords.add(new DayRecord(rs.getDouble("iQty"), rs.getDouble("iAmount"), rowCount, days * 2 + 4));
			}
			else {															
				Row row = sheet.createRow(rowCount);
				Cell cell = row.createCell(0);
				cell.setCellValue(product);
				cell.setCellStyle(style);
				
				cell = row.createCell(1);
				cell.setCellValue(totalProducQuantity);
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(unit);
				cell.setCellStyle(style);
				
				cell = row.createCell(3);
				cell.setCellValue(totalProductCost);
				cell.setCellStyle(moneyStyle);
				
				rowCount++;
				
				totalProducQuantity = rs.getDouble("iQty");
				totalProductCost = rs.getDouble("iAmount");
				
				if (!costCenter.equals(rs.getString(categoryColumnName))) {
					costCenter = rs.getString(categoryColumnName);
					row = sheet.createRow(rowCount);
					cell = row.createCell(0);
					cell.setCellValue(costCenter);
					cell.setCellStyle(categoryHeaderStyle);
					rowCount++;
				}
				
				dayRecords.add(new DayRecord(rs.getDouble("iQty"), rs.getDouble("iAmount"), rowCount, days * 2 + 4));
			}
			
			product = rs.getString("iName");
			unit = rs.getString("iUnit");
			
			totalDayCost = totalDayCost + rs.getDouble("iAmount");
			
			if (rs.isLast()) {
																			
				Row row = sheet.createRow(rowCount);
				Cell cell = row.createCell(0);
				cell.setCellValue(product);
				cell.setCellStyle(style);
				
				cell = row.createCell(1);
				cell.setCellValue(totalProducQuantity);
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(unit);
				cell.setCellStyle(style);
				
				cell = row.createCell(3);
				cell.setCellValue(totalProductCost);
				cell.setCellStyle(moneyStyle);
				
				rowCount++;
			}
	    }
		
		for (DayRecord dayRecord : dayRecords) {
			Cell cell = (sheet.getRow(dayRecord.row)).createCell(dayRecord.column);
			cell.setCellValue(dayRecord.quantity);
			cell.setCellStyle(style);
			
			cell = (sheet.getRow(dayRecord.row)).createCell(dayRecord.column + 1);
			cell.setCellValue(dayRecord.cost);
			cell.setCellStyle(moneyStyle);
		}

		Row row = sheet.createRow(rowCount);
		
		Cell cell = row.createCell(2);
		cell.setCellValue("TOTAL:");
		cell.setCellStyle(globalTotalStyle);	
		CellRangeAddress cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 1, 1);
		setBordersToMergedCells(sheet, cellRangeAddress, BorderStyle.THIN);
		
		cell = row.createCell(3);
		cell.setCellValue(totalDayCost);
		cell.setCellStyle(totalStyle);
	}
	
	private final class ExportTotalConsumptionToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithTotalConsumptionSheet(from, until);
			
			extract(from, until, rs, totalConsumptionSheet, "iCostCenterName");
			
			return null;
		}
	}
	
	private final class ExportDetailedConsumptionToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithDetailedConsumptionSheet(from, until);
			
			extract(from, until, rs, detailedConsumptionSheet, "processName");
						
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
			
			wbd = new WorkbookData();
			wbd.filePath = filePath;
			wbd.fileFullPath = fileFullPath;
			wbd.wb = workbook;
			
			return null;
		}
	}
	
	private Object runProductionAndSalesDBQuery(Date from, Date until, String costCenter) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport1_productionAndSales\"(:lang, :fromD, :untilD, :ccenter, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("ccenter", costCenter);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportProductionAndSalesToXLSExtractor());
	}
	
	private Object runTotalConsumptionDBQuery(Date from, Date until, String costCenter) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport1_totalConsumption\"(:lang, :fromD, :untilD, :ccenter, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("ccenter", costCenter);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportTotalConsumptionToXLSExtractor());
	}
	
	private Object runDetailedConsumptionDBQuery(Date from, Date until, String costCenter) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport1_detailedConsumption\"(:lang, :fromD, :untilD, :ccenter, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("ccenter", costCenter);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportDetailedConsumptionToXLSExtractor());
	}
	
	private void getCustomReportToXlsx(Date from, Date until, String costCenter) throws SQLException, IOException {
		
		//first sheet
		runProductionAndSalesDBQuery(from, until, costCenter);
				
		//second sheet
		runTotalConsumptionDBQuery(from, until, costCenter);
		
		//third sheet
		runDetailedConsumptionDBQuery(from, until, costCenter);
					
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
