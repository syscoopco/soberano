package co.syscoop.soberano.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
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
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
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

public class ExportCustomReport4Bean extends ExportBean implements IExportToFile {
	
private Workbook workbook = null;
	
	private Sheet procurementSheet = null;
	//private Sheet inventorySheet = null;
	private Sheet salesSheet = null;
	private Sheet salesTrendSheet = null;
	
	private XSSFFont font = null;
	private XSSFFont boldFont = null;
	private XSSFFont totalFont = null;
	
	private CellStyle style = null;
	private CellStyle dateStyle = null;
	private CellStyle categoryHeaderStyle = null;
	private CellStyle moneyStyle = null;
	private CellStyle subtotalStyle = null;
	private CellStyle totalStyle = null;
	private CellStyle headerStyle = null;
	private CellStyle globalTotalStyle = null;
	
	private String relativePath = "";
	
	private WorkbookData wbd = null;
	
	private void initWorkbook() throws Exception {
		
		workbook = new XSSFWorkbook();
		
		procurementSheet = workbook.createSheet("Compras");
		//inventorySheet = workbook.createSheet("Inventario");
		salesSheet = workbook.createSheet("Ventas");
		salesTrendSheet = workbook.createSheet("Tendencia de ventas");
		
		font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 10);
		font.setBold(false);
		
		boldFont = ((XSSFWorkbook) workbook).createFont();
		boldFont.setFontName("Arial");
		boldFont.setFontHeightInPoints((short) 10);
		boldFont.setBold(true);
		
		totalFont = ((XSSFWorkbook) workbook).createFont();
	    totalFont.setFontName("Arial");
	    totalFont.setFontHeightInPoints((short) 10);
	    totalFont.setBold(true);
		
		style = workbook.createCellStyle();
		style.setFont(font);
		style.setWrapText(true);
		
		dateStyle = workbook.createCellStyle();
		dateStyle.setFont(font);
		dateStyle.setWrapText(true);
		dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		categoryHeaderStyle = workbook.createCellStyle();
		categoryHeaderStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		categoryHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		categoryHeaderStyle.setFont(boldFont);
		categoryHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
		
		moneyStyle = workbook.createCellStyle();
		moneyStyle.setFont(font);
		moneyStyle.setWrapText(true);
	    moneyStyle.setDataFormat((short)8);
	    
	    subtotalStyle = workbook.createCellStyle();
	    subtotalStyle.setWrapText(true);
	    subtotalStyle.setDataFormat((short)8);
	    subtotalStyle.setFont(boldFont);
	    
	    totalStyle = workbook.createCellStyle();
	    totalStyle.setWrapText(true);
	    totalStyle.setDataFormat((short)8);
	    totalStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	    totalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    totalStyle.setFont(totalFont);
		
		headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setFont(boldFont);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		
		globalTotalStyle = workbook.createCellStyle();
		globalTotalStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		globalTotalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		globalTotalStyle.setFont(boldFont);
		globalTotalStyle.setAlignment(HorizontalAlignment.CENTER);
		globalTotalStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		globalTotalStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		globalTotalStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		globalTotalStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());		
		
		relativePath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath());
	}
		
	public ExportCustomReport4Bean(SoberanoDatasource soberanoDatasource) {
		
		super(soberanoDatasource);
	}
	
	@SuppressWarnings("unused")
	private class WorkbookData {
		public String filePath;
		public String fileFullPath;
		public Workbook wb;
	}
	
	private void setBordersToMergedCells(Sheet sheet, CellRangeAddress rangeAddress, BorderStyle borderStyle) {
	    RegionUtil.setBorderTop(borderStyle, rangeAddress, sheet);
	    RegionUtil.setBorderLeft(borderStyle, rangeAddress, sheet);
	    RegionUtil.setBorderRight(borderStyle, rangeAddress, sheet);
	    RegionUtil.setBorderBottom(borderStyle, rangeAddress, sheet);
	}
	
	private int daysBetweenInclusiveUtc(Date from, Date until) {
	    
		LocalDate fromDate = from.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
	    LocalDate untilDate = until.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
	    
	    long daysExclusive = ChronoUnit.DAYS.between(fromDate, untilDate);
	    long daysInclusive = daysExclusive + 1;
	    
	    return Math.toIntExact(daysInclusive);
	}
	
	private void initWorkbookWithProcurementSheet(Date from, Date until) {
		
		int numberColumnWidth = 1000;
		int dateColumnWidth = 3000;
		int providerColumnWidth = 3000;
		int referenceColumnWidth = 3000;
		int productNameColumnWidth = 8000;
		int quantityColumnWidth = 3000;
		int unitColumnWidth = 3000;
		int valueInCUPColumnWidth = 3000;
		int valueInUSDColumnWidth = 3000;
		int valueInUSDConvertedToCUPColumnWidth = 3000;
		int totalAmountColumnWidth = 3000;
		
		procurementSheet.setColumnWidth(0, numberColumnWidth);
		procurementSheet.setColumnWidth(1, dateColumnWidth);
		procurementSheet.setColumnWidth(2, providerColumnWidth);
		procurementSheet.setColumnWidth(3, referenceColumnWidth);
		procurementSheet.setColumnWidth(4, productNameColumnWidth);
		procurementSheet.setColumnWidth(5, quantityColumnWidth);
		procurementSheet.setColumnWidth(6, unitColumnWidth);
		procurementSheet.setColumnWidth(7, valueInCUPColumnWidth);
		procurementSheet.setColumnWidth(8, valueInUSDColumnWidth);
		procurementSheet.setColumnWidth(9, valueInUSDConvertedToCUPColumnWidth);
		procurementSheet.setColumnWidth(10, totalAmountColumnWidth);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Row fromRow = procurementSheet.createRow(0);
		Cell cell = fromRow.createCell(1);				
		cell.setCellValue("Desde");
		cell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 1, 1);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		cell = fromRow.createCell(2);
		cell.setCellValue(sdf.format(from));
		cell.setCellStyle(style);
		
		Row untilRow = procurementSheet.createRow(1);
		cell = untilRow.createCell(1);				
		cell.setCellValue("Hasta");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 1, 1);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		cell = untilRow.createCell(2);
		cell.setCellValue(sdf.format(until));
		cell.setCellStyle(style);
		
		Row daysRow = procurementSheet.createRow(2);
		cell = daysRow.createCell(1);				
		cell.setCellValue("Días");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 1, 1);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		cell = daysRow.createCell(2);
		cell.setCellValue(daysBetweenInclusiveUtc(from, until));
		cell.setCellStyle(style);
		
		Row exchangeRateRow = procurementSheet.createRow(3);		
		cell = exchangeRateRow.createCell(1);				
		cell.setCellValue("Tasa de cambio");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(3, 3, 1, 1);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);		
		cell = exchangeRateRow.createCell(2);				
		cell.setCellValue("1.0");
		cell.setCellStyle(style);
	    CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);
		
		procurementSheet.createRow(4);
		
		Row header = procurementSheet.createRow(5);
					
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("No.");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(5, 5, 0, 0);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("Fecha");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(5, 5, 1, 1);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Proveedor");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(5, 5, 2, 2);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("Referencia");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(5, 5, 3, 3);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(4);
		headerCell.setCellValue("Producto");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(5, 5, 4, 4);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(5);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(5, 5, 5, 5);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(6);
		headerCell.setCellValue("Unidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(5, 5, 6, 6);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(7);
		headerCell.setCellValue("Importe CUP");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(5, 5, 7, 7);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(8);
		headerCell.setCellValue("Importe USD");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(5, 5, 8, 8);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(9);
		headerCell.setCellValue("Importe USD a CUP");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(5, 5, 9, 9);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(10);
		headerCell.setCellValue("Importe total");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(5, 5, 10, 10);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
	}
	
	private void initWorkbookWithSalesSheet(Date from, Date until) {
		
		int dateColumnWidth = 3000;
		int productNameColumnWidth = 8000;
		int quantityColumnWidth = 3000;
		int priceColumnWidth = 3000;
		int amountColumnWidth = 3000;
		
		salesSheet.setColumnWidth(0, dateColumnWidth);
		salesSheet.setColumnWidth(1, productNameColumnWidth);
		salesSheet.setColumnWidth(2, quantityColumnWidth);
		salesSheet.setColumnWidth(3, priceColumnWidth);
		salesSheet.setColumnWidth(4, amountColumnWidth);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Row fromRow = salesSheet.createRow(0);
		Cell cell = fromRow.createCell(1);				
		cell.setCellValue("Desde");
		cell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 1, 1);
		setBordersToMergedCells(salesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		cell = fromRow.createCell(2);
		cell.setCellValue(sdf.format(from));
		cell.setCellStyle(style);
		
		Row untilRow = salesSheet.createRow(1);
		cell = untilRow.createCell(1);				
		cell.setCellValue("Hasta");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 1, 1);
		setBordersToMergedCells(salesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		cell = untilRow.createCell(2);
		cell.setCellValue(sdf.format(until));
		cell.setCellStyle(style);
		
		Row daysRow = salesSheet.createRow(2);
		cell = daysRow.createCell(1);				
		cell.setCellValue("Días");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 1, 1);
		setBordersToMergedCells(salesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		cell = daysRow.createCell(2);
		cell.setCellValue(daysBetweenInclusiveUtc(from, until));
		cell.setCellStyle(style);
		CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);
		
		salesSheet.createRow(3);
		
		Row header = salesSheet.createRow(4);
					
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Fecha");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(4, 4, 0, 0);
		setBordersToMergedCells(salesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("Producto");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(4, 4, 1, 1);
		setBordersToMergedCells(salesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(4, 4, 2, 2);
		setBordersToMergedCells(salesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("Precio");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(4, 4, 3, 3);
		setBordersToMergedCells(salesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(4);
		headerCell.setCellValue("Importe");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(4, 4, 4, 4);
		setBordersToMergedCells(salesSheet, cellRangeAddress, BorderStyle.MEDIUM);		
	}
	
	private void initWorkbookWithSalesTrendSheet(Date from, Date until) {
		
		int productNameColumnWidth = 8000;
		int priceColumnWidth = 3000;
		int quantityColumnWidth = 3000;
		int dailyAverageColumnWidth = 3000;
		int dailyPlanColumnWidth = 3000;
		
		salesTrendSheet.setColumnWidth(0, productNameColumnWidth);
		salesTrendSheet.setColumnWidth(1, priceColumnWidth);
		salesTrendSheet.setColumnWidth(2, quantityColumnWidth);
		salesTrendSheet.setColumnWidth(3, dailyAverageColumnWidth);
		salesTrendSheet.setColumnWidth(4, dailyPlanColumnWidth);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Row fromRow = salesTrendSheet.createRow(0);
		Cell cell = fromRow.createCell(1);				
		cell.setCellValue("Desde");
		cell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 1, 1);
		setBordersToMergedCells(salesTrendSheet, cellRangeAddress, BorderStyle.MEDIUM);
		cell = fromRow.createCell(2);
		cell.setCellValue(sdf.format(from));
		cell.setCellStyle(style);
		
		Row untilRow = salesTrendSheet.createRow(1);
		cell = untilRow.createCell(1);				
		cell.setCellValue("Hasta");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 1, 1);
		setBordersToMergedCells(salesTrendSheet, cellRangeAddress, BorderStyle.MEDIUM);
		cell = untilRow.createCell(2);
		cell.setCellValue(sdf.format(until));
		cell.setCellStyle(style);
		
		Row daysRow = salesTrendSheet.createRow(2);
		cell = daysRow.createCell(1);				
		cell.setCellValue("Días");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 1, 1);
		setBordersToMergedCells(salesTrendSheet, cellRangeAddress, BorderStyle.MEDIUM);
		cell = daysRow.createCell(2);
		cell.setCellValue(daysBetweenInclusiveUtc(from, until));
		cell.setCellStyle(style);
		CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);
		
		salesTrendSheet.createRow(3);
		
		Row header = salesTrendSheet.createRow(4);
					
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Producto");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(4, 4, 0, 0);
		setBordersToMergedCells(salesTrendSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("Precio");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(4, 4, 1, 1);
		setBordersToMergedCells(salesTrendSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(4, 4, 2, 2);
		setBordersToMergedCells(salesTrendSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("Promedio diario");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(4, 4, 3, 3);
		setBordersToMergedCells(salesTrendSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(4);
		headerCell.setCellValue("Plan diario");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(4, 4, 4, 4);
		setBordersToMergedCells(salesTrendSheet, cellRangeAddress, BorderStyle.MEDIUM);	
	}
	
	private String addMonthSubtotalRow(int month, int rowCount, int lastMonthSubtotalRow) {
		
		Row row = procurementSheet.createRow(rowCount);
		String formula = "SUM(" + "K" + (lastMonthSubtotalRow + Integer.valueOf(2)) + ":K" + rowCount  + ")";
		
		
		Cell cell = row.createCell(9);
		cell.setCellValue("Subtotal mes " + month);
		cell.setCellStyle(headerStyle);
		
		cell = row.createCell(10);
		cell.setCellFormula(formula);
		cell.setCellStyle(totalStyle);
		
		return formula;
	}
	
	private final class ExportProcurementSheetToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithProcurementSheet(from, until);
			
			int currentMonth = 0;			
			String totalFormula = "0";
			Integer rowCount = 6;
			Integer lastMonthSubtotalRow = 5;
			Integer rowNumber = rowCount - 2;
			
			while (rs.next()) {
				
				Row row;
				String formula;
				Cell cell;
				
				String expenseDate = rs.getString("expenseDate");
				int month = LocalDate.parse(expenseDate).getMonthValue();
				
				if (currentMonth > 0 &&
					month != currentMonth) {
					totalFormula = addMonthSubtotalRow(currentMonth, rowCount, lastMonthSubtotalRow) + "+" + totalFormula;
					lastMonthSubtotalRow = rowCount;
					rowCount++;
				}
				currentMonth = month;
				
				row = procurementSheet.createRow(rowCount);
				
				cell = row.createCell(0);											
				cell.setCellValue(rowNumber);						//A
				rowNumber++;
				cell.setCellStyle(style);
				
				cell = row.createCell(1);								
				cell.setCellValue(expenseDate);							//B
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(rs.getString("expenseProvider"));		//C
				cell.setCellStyle(style);
				
				cell = row.createCell(3);
				cell.setCellValue(rs.getString("expenseReference"));	//D
				cell.setCellStyle(style);
				
				cell = row.createCell(4);
				cell.setCellValue(rs.getString("expenseProduct"));		//E
				cell.setCellStyle(style);
				
				cell = row.createCell(5);
				cell.setCellValue(rs.getDouble("productQuantity"));		//F
				cell.setCellStyle(style);
				
				cell = row.createCell(6);
				cell.setCellValue(rs.getString("productUnit"));			//G
				cell.setCellStyle(style);
				
				if (rs.getString("currency").equals("CUP")) {
					cell = row.createCell(7);
					cell.setCellValue(rs.getDouble("unitValue"));		//H
					cell.setCellStyle(moneyStyle);
					
					cell = row.createCell(8);						
					cell.setCellValue(0.0);
					cell.setCellStyle(moneyStyle);
				}
				else if (rs.getString("currency").equals("USD")) {
					cell = row.createCell(7);
					cell.setCellValue(0.0);
					cell.setCellStyle(moneyStyle);
					
					cell = row.createCell(8);
					cell.setCellValue(rs.getDouble("unitValue"));		//I
					cell.setCellStyle(moneyStyle);
				} else {
					cell = row.createCell(7);
					cell.setCellValue(0.0);
					cell.setCellStyle(moneyStyle);
					
					cell = row.createCell(8);
					cell.setCellValue(0.0);
					cell.setCellStyle(moneyStyle);
				}
				
				cell = row.createCell(9);
				Integer rowIndex = Integer.valueOf(rowCount + 1);
				formula = "C4" + "*I" + Integer.valueOf(rowCount + 1).toString();
				cell.setCellFormula(formula);
				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(10);				
				formula = "F" + rowIndex.toString() + "*(H" + rowIndex.toString() + "+J" + rowIndex.toString() + ")";
				cell.setCellFormula(formula);
				cell.setCellStyle(moneyStyle);
				
				rowCount++;
				
				if (rs.isLast()) {
					totalFormula = addMonthSubtotalRow(currentMonth, rowCount, lastMonthSubtotalRow) + "+" + totalFormula;
					
					row = procurementSheet.createRow(++rowCount + 1);
					
					cell = row.createCell(9);
					cell.setCellValue("TOTAL");
					cell.setCellStyle(headerStyle);
					
					cell = row.createCell(10);
					cell.setCellFormula(totalFormula);
					cell.setCellStyle(totalStyle);
				}
			}
			
			if (rowCount > 1) {
				CellRangeAddress cellRangeAddress = new CellRangeAddress(6, rowCount - 1, 0, 10);
				setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
			}
												
			return null;
		}
	}
	
	private String formatDate(Date date, String formatStr) {
		return (new SimpleDateFormat(formatStr)).format(date);
	}
	
	private void addDayTotalAmount(Sheet sheet, Integer rowCount, Integer initDayRow, Integer totalColumn, String totalColumnLetter) {
		Row row = sheet.createRow(rowCount);
		Cell cell = row.createCell(0);				
		cell.setCellValue("TOTAL");
		cell.setCellStyle(headerStyle);
		
		cell = row.createCell(totalColumn);
		cell.setCellStyle(totalStyle);
		String formula= "SUM(" + totalColumnLetter + initDayRow.toString() + ":" + totalColumnLetter + Integer.valueOf(rowCount).toString() + ")";
		cell.setCellFormula(formula);
		
		CellRangeAddress cellRangeAddress = new CellRangeAddress(initDayRow - 1, rowCount - 1, 0, 0);
		if (initDayRow - 1 < rowCount - 1) {sheet.addMergedRegion(cellRangeAddress);}		
		setBordersToMergedCells(sheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 0, totalColumn);
		setBordersToMergedCells(sheet, cellRangeAddress, BorderStyle.MEDIUM);
	}
	
	private final class ExportSalesSheetToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithSalesSheet(from, until);
			
			Integer rowCount = 5;
			Date previousDate = null;
			Integer initDayRow = 6;
			while (rs.next()) {
				
				Row row = salesSheet.createRow(rowCount);
				
				if (previousDate != null && previousDate.compareTo(rs.getDate("orderdate")) != 0) {
					addDayTotalAmount(salesSheet, rowCount, initDayRow, 4, "E");					
					initDayRow = rowCount + 2;					
					rowCount++;
				}
				
				previousDate = rs.getDate("orderdate");
				
				row = salesSheet.createRow(rowCount);
				Cell cell = row.createCell(0);				
				cell.setCellValue(formatDate(rs.getDate("orderdate"), "yyyy-MM-dd"));
				cell.setCellStyle(dateStyle);
				
				cell = row.createCell(1);
				cell.setCellValue(rs.getString("iName"));
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(rs.getDouble("iQty"));
				cell.setCellStyle(style);
				
				cell = row.createCell(3);
				cell.setCellValue(rs.getDouble("iPrice"));
				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(4);
				String formula= "C" + Integer.valueOf(rowCount + 1).toString() + "*" + "D" + Integer.valueOf(rowCount + 1).toString();
				cell.setCellFormula(formula);
				cell.setCellStyle(moneyStyle);
				
				rowCount++;
				
				if (rs.isLast()) {
					addDayTotalAmount(salesSheet, rowCount, initDayRow, 4, "E");					
					initDayRow = rowCount + 2;					
					rowCount++;
				}
			}
			
			if (rowCount > 1) {
				CellRangeAddress cellRangeAddress = new CellRangeAddress(4, rowCount - 1, 0, 4);
				setBordersToMergedCells(salesSheet, cellRangeAddress, BorderStyle.MEDIUM);
			}
												
			return null;
		}
	}
	
	private final class ExportSalesTrendSheetToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithSalesTrendSheet(from, until);
			
			Integer rowCount = 5;
			while (rs.next()) {
				
				Row row = salesTrendSheet.createRow(rowCount);
				
				Cell cell = row.createCell(0);				
				cell.setCellValue(rs.getString("iName"));
				cell.setCellStyle(style);
				
				cell = row.createCell(1);
				cell.setCellValue(rs.getDouble("iPrice"));
				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(2);
				cell.setCellValue(rs.getDouble("iQty"));
				cell.setCellStyle(style);
				
				cell = row.createCell(3);
				String formula= "ROUND(C" + Integer.valueOf(rowCount + 1) + "/C3, 2)";
				cell.setCellFormula(formula);
				cell.setCellStyle(style);
				
				rowCount++;
			}
			
			if (rowCount > 1) {
				CellRangeAddress cellRangeAddress = new CellRangeAddress(5, rowCount - 1, 0, 4);
				setBordersToMergedCells(salesTrendSheet, cellRangeAddress, BorderStyle.MEDIUM);
			}
												
			return null;
		}
	}
		
	private Object runProcurementSheetDBQuery(Date from, Date until, String costCenter) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport4_procurements\"(:lang, :fromD, :untilD, :loginname)";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportProcurementSheetToXLSExtractor());
	}
	
	private Object runSalesSheetDBQuery(Date from, Date until, String costCenter) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport2_dailySales\"(:lang, :fromD, :untilD, :ccenter, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("ccenter", costCenter);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportSalesSheetToXLSExtractor());
	}
	
	private Object runSalesTrendSheetDBQuery(Date from, Date until, String costCenter) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport4_salesTrend\"(:lang, :fromD, :untilD, :ccenter, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("ccenter", costCenter);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportSalesTrendSheetToXLSExtractor());
	}
	
	private void getCustomReportToXlsx(Date from, Date until, String costCenter) throws SQLException, IOException {
		
		try {
			initWorkbook();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//first sheet
		runProcurementSheetDBQuery(from, until, costCenter);
		
		//third sheet
		runSalesSheetDBQuery(from, until, costCenter);
		
		//fourth sheet
		runSalesTrendSheetDBQuery(from, until, costCenter);
		
		//workbook file creation//
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fromDateStr = "";
		String untilDateStr = "";
		try {
			fromDateStr = dateFormat.format(from);
			untilDateStr = dateFormat.format(until);
		} 
		catch(Exception ex) {};
		
		String xlsFileName = "custom_report_4_from_" + fromDateStr + "_to_" + untilDateStr + ".xlsx";
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
				
		//workbook file download
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
