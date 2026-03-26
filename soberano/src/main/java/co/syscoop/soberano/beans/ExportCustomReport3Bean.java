package co.syscoop.soberano.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

public class ExportCustomReport3Bean extends ExportBean implements IExportToFile {
	
private Workbook workbook = null;
	
	private Sheet reportSheet1 = null;
	private Sheet reportSheet2 = null;
	
	private Integer sheet1LastRowIndex = 0;
	
	private XSSFFont font = null;
	private XSSFFont boldFont = null;
	private XSSFFont totalFont = null;
	
	private CellStyle style = null;
	private CellStyle dateStyle = null;
	private CellStyle categoryHeaderStyle = null;
	private CellStyle moneyStyle = null;
	private CellStyle totalStyle = null;
	private CellStyle headerStyle = null;
	private CellStyle globalTotalStyle = null;
	
	private BigDecimal materialExpenses;
	private BigDecimal serviceExpenses;
	private BigDecimal payrollExpenses;
	
	private Row openingInventoryRow;
	private Row endingInventoryRow;
	private Row salesRow;
	
	private String relativePath = "";
	
	private WorkbookData wbd = null;
	
	private void initWorkbook() throws Exception {
		
		workbook = new XSSFWorkbook();
		
		reportSheet1 = workbook.createSheet("Costos, ventas e IPV");
		reportSheet2 = workbook.createSheet("Existencias y ganancia");
		
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
		
	public ExportCustomReport3Bean(SoberanoDatasource soberanoDatasource) {
		
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
	
	private void initWorkbookWithReportSheet1(Date from, Date until) {
		
		int productNameColumnWidth = 8000;
		int unitCostColumnWidth = 3000;
		int priceColumnWidth = 3000;
		int unitGainColumnWidth = 3000;
		int quantityColumnWidth = 3000;
		int totalGainColumnWidth = 3000;
		int salesColumnWidth = 3000;
//		int _1percentageColumnWidth = 3000;
//		int _2percentageColumnWidth = 3000;
//		int _3percentageColumnWidth = 3000;
//		int _4percentageColumnWidth = 3000;
//		int _5percentageColumnWidth = 3000;
		int openingColumnWidth = 3000;
		int inputsColumnWidth = 3000;
		int outputsStockColumnWidth = 3000;
		int movementsStockColumnWidth = 3000;
		int lossesStockColumnWidth = 3000;
		int currentStockColumnWidth = 3000;
		
		reportSheet1.setColumnWidth(0, productNameColumnWidth);
		reportSheet1.setColumnWidth(1, unitCostColumnWidth);
		reportSheet1.setColumnWidth(2, priceColumnWidth);
		reportSheet1.setColumnWidth(3, unitGainColumnWidth);
		reportSheet1.setColumnWidth(4, quantityColumnWidth);
		reportSheet1.setColumnWidth(5, totalGainColumnWidth);
		reportSheet1.setColumnWidth(6, salesColumnWidth);
//		reportSheet1.setColumnWidth(7, _1percentageColumnWidth);
//		reportSheet1.setColumnWidth(8, _2percentageColumnWidth);
//		reportSheet1.setColumnWidth(9, _3percentageColumnWidth);
//		reportSheet1.setColumnWidth(10, _4percentageColumnWidth);
//		reportSheet1.setColumnWidth(11, _5percentageColumnWidth);
		reportSheet1.setColumnWidth(7, openingColumnWidth);
		reportSheet1.setColumnWidth(8, inputsColumnWidth);
		reportSheet1.setColumnWidth(9, outputsStockColumnWidth);
		reportSheet1.setColumnWidth(10, movementsStockColumnWidth);
		reportSheet1.setColumnWidth(11, lossesStockColumnWidth);
		reportSheet1.setColumnWidth(12, currentStockColumnWidth);
		
		Row header = reportSheet1.createRow(0);
					
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Producto");
		headerCell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 0);
		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("Costo unitario");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 1, 1);
		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Precio");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 2, 2);
		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("Ganancia unitaria");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 3, 3);
		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(4);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 4, 4);
		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(5);
		headerCell.setCellValue("Ganancia total");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 5, 5);
		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(6);
		headerCell.setCellValue("Ventas");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 6, 6);
		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);
		
//		headerCell = header.createCell(7);
//		headerCell.setCellValue("1%");
//		headerCell.setCellStyle(headerStyle);
//		cellRangeAddress = new CellRangeAddress(0, 0, 7, 7);
//		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);
//		
//		headerCell = header.createCell(8);
//		headerCell.setCellValue("2%");
//		headerCell.setCellStyle(headerStyle);
//		cellRangeAddress = new CellRangeAddress(0, 0, 8, 8);
//		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);	
//		
//		headerCell = header.createCell(9);
//		headerCell.setCellValue("3%");
//		headerCell.setCellStyle(headerStyle);
//		cellRangeAddress = new CellRangeAddress(0, 0, 9, 9);
//		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);
//		
//		headerCell = header.createCell(10);
//		headerCell.setCellValue("4%");
//		headerCell.setCellStyle(headerStyle);
//		cellRangeAddress = new CellRangeAddress(0, 0, 10, 10);
//		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);
//		
//		headerCell = header.createCell(11);
//		headerCell.setCellValue("5%");
//		headerCell.setCellStyle(headerStyle);
//		cellRangeAddress = new CellRangeAddress(0, 0, 11, 11);
//		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(7);
		headerCell.setCellValue("Inicio");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 7, 7);
		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);	
		
		headerCell = header.createCell(8);
		headerCell.setCellValue("Entradas");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 8, 8);
		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);	
		
		headerCell = header.createCell(9);
		headerCell.setCellValue("Salidas");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 9, 9);
		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(10);
		headerCell.setCellValue("Movimientos");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 10, 10);
		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);	
		
		headerCell = header.createCell(11);
		headerCell.setCellValue("Pérdidas");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 11, 11);
		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);	
		
		headerCell = header.createCell(12);
		headerCell.setCellValue("Existencias");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 12, 12);
		setBordersToMergedCells(reportSheet1, cellRangeAddress, BorderStyle.MEDIUM);	
	}
	
	private void addWarehouseHeaderCells(String warehouseName, Sheet sheet, Row warehouseHeader, Row warehouseDetailsHeader, 
										CellStyle headerStyle, int warehouseColumnIndex, int quantityColumnWidth, int valueColumnWidth) {
		
		Cell headerCell = warehouseHeader.createCell(warehouseColumnIndex);
		headerCell.setCellValue(warehouseName);
		headerCell.setCellStyle(headerStyle);
		sheet.setColumnWidth(warehouseColumnIndex, quantityColumnWidth);
		sheet.setColumnWidth(warehouseColumnIndex + 1, valueColumnWidth);
		sheet.setColumnWidth(warehouseColumnIndex + 2, quantityColumnWidth);
		sheet.setColumnWidth(warehouseColumnIndex + 3, valueColumnWidth);
		sheet.setColumnWidth(warehouseColumnIndex + 4, quantityColumnWidth);
		sheet.setColumnWidth(warehouseColumnIndex + 5, valueColumnWidth);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(14, 14, warehouseColumnIndex, warehouseColumnIndex + 5);
		sheet.addMergedRegion(cellRangeAddress);
		setBordersToMergedCells(sheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = warehouseDetailsHeader.createCell(warehouseColumnIndex);
		headerCell.setCellValue("Cantidad inicial");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(15, 15, warehouseColumnIndex, warehouseColumnIndex);
		setBordersToMergedCells(sheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		
		headerCell = warehouseDetailsHeader.createCell(warehouseColumnIndex + 1);
		headerCell.setCellValue("Valor unitario inicial");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(15, 15, warehouseColumnIndex + 1, warehouseColumnIndex + 1);
		setBordersToMergedCells(sheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = warehouseDetailsHeader.createCell(warehouseColumnIndex + 2);
		headerCell.setCellValue("Valor total inicial");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(15, 15, warehouseColumnIndex + 2, warehouseColumnIndex + 2);
		setBordersToMergedCells(sheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = warehouseDetailsHeader.createCell(warehouseColumnIndex + 3);
		headerCell.setCellValue("Cantidad final");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(15, 15, warehouseColumnIndex + 3, warehouseColumnIndex + 3);
		setBordersToMergedCells(sheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = warehouseDetailsHeader.createCell(warehouseColumnIndex + 4);
		headerCell.setCellValue("Valor unitario final");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(15, 15, warehouseColumnIndex + 4, warehouseColumnIndex + 4);
		setBordersToMergedCells(sheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = warehouseDetailsHeader.createCell(warehouseColumnIndex + 5);
		headerCell.setCellValue("Valor total final");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(15, 15, warehouseColumnIndex + 5, warehouseColumnIndex + 5);
		setBordersToMergedCells(sheet, cellRangeAddress, BorderStyle.MEDIUM);
	}
	
	private void initWorkbookWithReportSheet2(Date from, Date until) {
		
		int productNameColumnWidth = 8000;
		int quantityColumnWidth = 4000;
		int valueColumnWidth = 4000;
		
		reportSheet2.setColumnWidth(0, productNameColumnWidth);
		
		Row materialExpensesRow = reportSheet2.createRow(0);
		Cell cell = materialExpensesRow.createCell(0);				
		cell.setCellValue("Gastos materiales");
		cell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 0);
		setBordersToMergedCells(reportSheet2, cellRangeAddress, BorderStyle.MEDIUM);
		
		cell = materialExpensesRow.createCell(1);
		cell.setCellValue(materialExpenses.doubleValue());
		cell.setCellStyle(moneyStyle);
		
		Row serviceExpensesRow = reportSheet2.createRow(1);
		cell = serviceExpensesRow.createCell(0);				
		cell.setCellValue("Gastos en servicios");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 0, 0);
		setBordersToMergedCells(reportSheet2, cellRangeAddress, BorderStyle.MEDIUM);
		
		cell = serviceExpensesRow.createCell(1);
		cell.setCellValue(serviceExpenses.doubleValue());
		cell.setCellStyle(moneyStyle);
		
		Row payrollExpensesRow = reportSheet2.createRow(2);
		cell = payrollExpensesRow.createCell(0);				
		cell.setCellValue("Gastos de nómina");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 0, 0);
		setBordersToMergedCells(reportSheet2, cellRangeAddress, BorderStyle.MEDIUM);
		
		cell = payrollExpensesRow.createCell(1);
		cell.setCellValue(payrollExpenses.doubleValue());
		cell.setCellStyle(moneyStyle);
		
		Row totalExpensesRow = reportSheet2.createRow(3);
		cell = totalExpensesRow.createCell(0);				
		cell.setCellValue("Total de gastos");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(3, 3, 0, 0);
		setBordersToMergedCells(reportSheet2, cellRangeAddress, BorderStyle.MEDIUM);
		
		cell = totalExpensesRow.createCell(1);
		cell.setCellStyle(totalStyle);
		String formula= "SUM(B1:B3)";
		cell.setCellFormula(formula);
		
		reportSheet2.createRow(4);
		
		openingInventoryRow = reportSheet2.createRow(5);
		cell = openingInventoryRow.createCell(0);				
		cell.setCellValue("Inventario inicial");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(5, 5, 0, 0);
		setBordersToMergedCells(reportSheet2, cellRangeAddress, BorderStyle.MEDIUM);
		
		endingInventoryRow = reportSheet2.createRow(6);
		cell = endingInventoryRow.createCell(0);				
		cell.setCellValue("Inventario final");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(6, 6, 0, 0);
		setBordersToMergedCells(reportSheet2, cellRangeAddress, BorderStyle.MEDIUM);
		
		reportSheet2.createRow(7);
		
		salesRow = reportSheet2.createRow(8);
		cell = salesRow.createCell(0);				
		cell.setCellValue("Ventas");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(8, 8, 0, 0);
		setBordersToMergedCells(reportSheet2, cellRangeAddress, BorderStyle.MEDIUM);
		
		reportSheet2.createRow(9);
		
		Row costRow = reportSheet2.createRow(10);
		cell = costRow.createCell(0);				
		cell.setCellValue("Costos");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(10, 10, 0, 0);
		setBordersToMergedCells(reportSheet2, cellRangeAddress, BorderStyle.MEDIUM);
		
		cell = costRow.createCell(1);
		cell.setCellStyle(totalStyle);
		formula= "B6+B4-B7";
		cell.setCellFormula(formula);
		
		reportSheet2.createRow(11);
		
		Row profitsRow = reportSheet2.createRow(12);
		cell = profitsRow.createCell(0);				
		cell.setCellValue("Ganancia");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(12, 12, 0, 0);
		setBordersToMergedCells(reportSheet2, cellRangeAddress, BorderStyle.MEDIUM);
		
		cell = profitsRow.createCell(1);
		cell.setCellStyle(totalStyle);
		formula= "B9-B11";
		cell.setCellFormula(formula);
		
		reportSheet2.createRow(13);
		
		Row warehouseHeader = reportSheet2.createRow(14);
		
		Row productRow = reportSheet2.createRow(15);
		cell = productRow.createCell(0);				
		cell.setCellValue("Producto");
		cell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(15, 15, 0, 0);
		setBordersToMergedCells(reportSheet2, cellRangeAddress, BorderStyle.MEDIUM);		
		
		addWarehouseHeaderCells("Almacén", reportSheet2, warehouseHeader, productRow, 
								headerStyle, 1, quantityColumnWidth, valueColumnWidth);
		
		addWarehouseHeaderCells("Mostrador", reportSheet2, warehouseHeader, productRow, 
								headerStyle, 7, quantityColumnWidth, valueColumnWidth);		
	}
	
	private void addSheet1TotalsRows(Sheet sheet, Integer rowCount) {
		Row row = sheet.createRow(rowCount);
		Cell cell = row.createCell(4);				
		cell.setCellValue("TOTAL");
		cell.setCellStyle(headerStyle);
		
		cell = row.createCell(5);
		cell.setCellStyle(totalStyle);
		String formula= "SUM(" + "F2:F" + Integer.valueOf(rowCount).toString() + ")";
		cell.setCellFormula(formula);
		
		cell = row.createCell(6);
		cell.setCellStyle(totalStyle);
		formula= "SUM(" + "G2:G" + Integer.valueOf(rowCount).toString() + ")";
		cell.setCellFormula(formula);
		
//		cell = row.createCell(7);
//		cell.setCellStyle(totalStyle);
//		formula= "SUM(" + "H2:H" + Integer.valueOf(rowCount).toString() + ")";
//		cell.setCellFormula(formula);
//		
//		cell = row.createCell(8);
//		cell.setCellStyle(totalStyle);
//		formula= "SUM(" + "I2:I" + Integer.valueOf(rowCount).toString() + ")";
//		cell.setCellFormula(formula);
//		
//		cell = row.createCell(9);
//		cell.setCellStyle(totalStyle);
//		formula= "SUM(" + "J2:J" + Integer.valueOf(rowCount).toString() + ")";
//		cell.setCellFormula(formula);
//		
//		cell = row.createCell(10);
//		cell.setCellStyle(totalStyle);
//		formula= "SUM(" + "K2:K" + Integer.valueOf(rowCount).toString() + ")";
//		cell.setCellFormula(formula);
//		
//		cell = row.createCell(11);
//		cell.setCellStyle(totalStyle);
//		formula= "SUM(" + "L2:L" + Integer.valueOf(rowCount).toString() + ")";
//		cell.setCellFormula(formula);
	}
	
	private final class ExportSheet1ToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithReportSheet1(from, until);
			
			Integer rowCount = 1;
			while (rs.next()) {
				
				Row row = reportSheet1.createRow(rowCount);
				
				Cell cell = row.createCell(0);				
				cell.setCellValue(rs.getString("iName"));
				cell.setCellStyle(style);
				
				cell = row.createCell(1);
				cell.setCellValue(rs.getDouble("unitCost"));
				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(2);
				cell.setCellValue(rs.getDouble("iPrice"));
				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(3);
				String formula = "C" + Integer.valueOf(rowCount + 1).toString() + "-" + "B" + Integer.valueOf(rowCount + 1).toString();
				cell.setCellFormula(formula);
				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(4);
				cell.setCellValue(rs.getDouble("soldQuantity"));
				cell.setCellStyle(style);
				
				cell = row.createCell(5);
				formula = "D" + Integer.valueOf(rowCount + 1).toString() + "*" + "E" + Integer.valueOf(rowCount + 1).toString();
				cell.setCellFormula(formula);
				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(6);
				formula = "C" + Integer.valueOf(rowCount + 1).toString() + "*" + "E" + Integer.valueOf(rowCount + 1).toString();
				cell.setCellFormula(formula);
				cell.setCellStyle(moneyStyle);
				
//				cell = row.createCell(7);
//				formula = "G" + Integer.valueOf(rowCount + 1).toString() + "*1%";
//				cell.setCellFormula(formula);
//				cell.setCellStyle(moneyStyle);
//				
//				cell = row.createCell(8);
//				formula = "G" + Integer.valueOf(rowCount + 1).toString() + "*2%";
//				cell.setCellFormula(formula);
//				cell.setCellStyle(moneyStyle);
//				
//				cell = row.createCell(9);
//				formula = "G" + Integer.valueOf(rowCount + 1).toString() + "*3%";
//				cell.setCellFormula(formula);
//				cell.setCellStyle(moneyStyle);
//				
//				cell = row.createCell(10);
//				formula = "G" + Integer.valueOf(rowCount + 1).toString() + "*4%";
//				cell.setCellFormula(formula);
//				cell.setCellStyle(moneyStyle);
//				
//				cell = row.createCell(11);
//				formula = "G" + Integer.valueOf(rowCount + 1).toString() + "*5%";
//				cell.setCellFormula(formula);
//				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(7);
				cell.setCellValue(rs.getDouble("opening"));
				cell.setCellStyle(style);
				
				cell = row.createCell(8);
				cell.setCellValue(rs.getDouble("inputs"));
				cell.setCellStyle(style);
				
				cell = row.createCell(9);
				cell.setCellValue(rs.getDouble("outputs"));
				cell.setCellStyle(style);
				
				cell = row.createCell(10);
				cell.setCellValue(rs.getDouble("movements"));
				cell.setCellStyle(style);
				
				cell = row.createCell(11);
				cell.setCellValue(rs.getDouble("losses"));
				cell.setCellStyle(style);
				
				cell = row.createCell(12);
				cell.setCellValue(rs.getDouble("ending"));
				cell.setCellStyle(style);
				
				rowCount++;
				
				if (rs.isLast()) {
					addSheet1TotalsRows(reportSheet1, rowCount);
				}
			}
			
			sheet1LastRowIndex = rowCount;
												
			return null;
		}
	}
	
	private void fillSheet2ItemCells(ResultSet rs, Row currentItemRow, Integer cellIndexOffset) throws SQLException {
		
		Cell cell = null;
		cell = currentItemRow.createCell(1 + cellIndexOffset);				
		cell.setCellValue(rs.getBigDecimal("openingQuantity").doubleValue());
		cell.setCellStyle(style);
		
		cell = currentItemRow.createCell(2 + cellIndexOffset);				
		cell.setCellValue(rs.getBigDecimal("openingUnitCost").doubleValue());
		cell.setCellStyle(moneyStyle);
		
		cell = currentItemRow.createCell(3 + cellIndexOffset);				
		cell.setCellStyle(moneyStyle);
		String formula = "";
		if (cellIndexOffset == 0) {
			formula= "B" + (currentItemRow.getRowNum() + 1) + "*C" + (currentItemRow.getRowNum() + 1);
		}
		else {
			formula= "H" + (currentItemRow.getRowNum() + 1) + "*I" + (currentItemRow.getRowNum() + 1);
		}
		cell.setCellFormula(formula);
		
		cell = null;
		cell = currentItemRow.createCell(4 + cellIndexOffset);				
		cell.setCellValue(rs.getBigDecimal("endingQuantity").doubleValue());
		cell.setCellStyle(style);
		
		cell = currentItemRow.createCell(5 + cellIndexOffset);				
		cell.setCellValue(rs.getBigDecimal("endingUnitCost").doubleValue());
		cell.setCellStyle(moneyStyle);
		
		cell = currentItemRow.createCell(6 + cellIndexOffset);				
		cell.setCellStyle(moneyStyle);
		if (cellIndexOffset == 0) {
			formula= "E" + (currentItemRow.getRowNum() + 1) + "*F" + (currentItemRow.getRowNum() + 1);
		}
		else {
			formula= "K" + (currentItemRow.getRowNum() + 1) + "*L" + (currentItemRow.getRowNum() + 1);
		}
		cell.setCellFormula(formula);	
	}
	
	private void addSheet2TotalsRows(Sheet sheet, Integer rowCount) {
		
		Row row = sheet.createRow(rowCount);
		Cell cell = row.createCell(2);				
		cell.setCellValue("TOTAL");
		cell.setCellStyle(headerStyle);
		
		cell = row.createCell(3);
		cell.setCellStyle(totalStyle);
		String formula= "SUM(" + "D17:D" + Integer.valueOf(rowCount).toString() + ")";
		cell.setCellFormula(formula);
		
		cell = row.createCell(6);
		cell.setCellStyle(totalStyle);
		formula= "SUM(" + "G17:G" + Integer.valueOf(rowCount).toString() + ")";
		cell.setCellFormula(formula);
		
		cell = row.createCell(9);
		cell.setCellStyle(totalStyle);
		formula= "SUM(" + "J17:J" + Integer.valueOf(rowCount).toString() + ")";
		cell.setCellFormula(formula);
		
		cell = row.createCell(12);
		cell.setCellStyle(totalStyle);
		formula= "SUM(" + "M17:M" + Integer.valueOf(rowCount).toString() + ")";
		cell.setCellFormula(formula);
	}
	
	private final class ExportSheet2ToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			runSheet2ExpensesQuery(from, until, null);
			
			initWorkbookWithReportSheet2(from, until);
			
			String previousItem = "";
			Row currentItemRow = null;
	
			Integer rowCount = 16;
			while (rs.next()) {
				
				if (previousItem.equals(rs.getString("inventoryItemName"))) {
					fillSheet2ItemCells(rs, currentItemRow, 6);
					rowCount++;
				}
				else {
					currentItemRow = reportSheet2.createRow(rowCount);
					
					Cell cell = currentItemRow.createCell(0);				
					cell.setCellValue(rs.getString("inventoryItemName"));
					cell.setCellStyle(style);
					
					fillSheet2ItemCells(rs, currentItemRow, 0);
				}
				
				previousItem = rs.getString("inventoryItemName");
				
				if (rs.isLast()) {
					addSheet2TotalsRows(reportSheet2, rowCount);
				}
			}
			
			//inventory total cells
			Cell cell = openingInventoryRow.createCell(1);
			cell.setCellStyle(totalStyle);
			String formula= "D" + Integer.valueOf(rowCount + 1).toString() + "+J" + Integer.valueOf(rowCount + 1).toString();
			cell.setCellFormula(formula);
			
			cell = endingInventoryRow.createCell(1);
			cell.setCellStyle(totalStyle);
			formula= "G" + Integer.valueOf(rowCount + 1).toString() + "+M" + Integer.valueOf(rowCount + 1).toString();
			cell.setCellFormula(formula);
			
			//sales cell
			cell = salesRow.createCell(1);
			cell.setCellStyle(totalStyle);
			formula= "'Costos, ventas e IPV'!G" + (sheet1LastRowIndex + 1);
			cell.setCellFormula(formula);
						
			return null;
		}
	}
	
	private final class ExpensesExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			rs.next();
			
			materialExpenses = rs.getBigDecimal("materialExpenses");
			serviceExpenses = rs.getBigDecimal("serviceExpenses");
			payrollExpenses = rs.getBigDecimal("payrollExpenses");
					
			return null;
		}
	}
	
	private Object runSheet1DBQuery(Date from, Date until, String costCenter) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport3_query\"(:lang, :fromD, :untilD, :ccenter, :loginname)";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("ccenter", costCenter);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportSheet1ToXLSExtractor());
	}
	
	private Object runSheet2DBQuery(Date from, Date until, String costCenter) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport3_stock\"(:fromD, :untilD, :loginname)";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportSheet2ToXLSExtractor());
	}
	
	private Object runSheet2ExpensesQuery(Date from, Date until, String costCenter) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport3_expenses\"(:fromD, :untilD, :loginname)";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExpensesExtractor());
	}
	
	private void getCustomReportToXlsx(Date from, Date until, String costCenter) throws SQLException, IOException {
		
		try {
			initWorkbook();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//first sheet
		runSheet1DBQuery(from, until, costCenter);
		
		//second sheet
		runSheet2DBQuery(from, until, costCenter);
		
		//workbook file creation//
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fromDateStr = "";
		String untilDateStr = "";
		try {
			fromDateStr = dateFormat.format(from);
			untilDateStr = dateFormat.format(until);
		} 
		catch(Exception ex) {};
		
		String xlsFileName = "custom_report_3_from_" + fromDateStr + "_to_" + untilDateStr + ".xlsx";
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
