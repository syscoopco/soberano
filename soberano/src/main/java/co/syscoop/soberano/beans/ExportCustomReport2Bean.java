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

public class ExportCustomReport2Bean extends ExportBean implements IExportToFile {
	
private Workbook workbook = null;
	
	private Sheet dailySalesSheet = null;
	private Sheet dailyMaterialExpensesSheet = null;
	private Sheet dailyServiceExpensesSheet = null;
	private Sheet dailyPaymentsSheet = null;
	private Sheet warehouseStockSheet = null;
	private Sheet monthlyMaterialExpensesSheet = null;
	private Sheet monthlyServiceExpensesSheet = null;
	private Sheet catalogSheet = null;
	
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
	
	private String relativePath = "";
	
	private WorkbookData wbd = null;
	
	private void initWorkbook() throws Exception {
		
		workbook = new XSSFWorkbook();
		
		dailySalesSheet = workbook.createSheet("Ventas diarias");
		dailyMaterialExpensesSheet = workbook.createSheet("Gastos materiales diarios");
		dailyServiceExpensesSheet = workbook.createSheet("Gastos en servicios diarios");
		monthlyMaterialExpensesSheet = workbook.createSheet("Gastos materiales mensuales");
		monthlyServiceExpensesSheet = workbook.createSheet("Gastos en servicios mensuales");
		dailyPaymentsSheet = workbook.createSheet("Cobros diarios");
		warehouseStockSheet = workbook.createSheet("Existencias");
		catalogSheet = workbook.createSheet("Catálogo");
		
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
	    totalStyle.setFont(totalFont);
		
		headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setFont(boldFont);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		
		globalTotalStyle = workbook.createCellStyle();
		globalTotalStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		globalTotalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		globalTotalStyle.setFont(boldFont);
		globalTotalStyle.setAlignment(HorizontalAlignment.CENTER);
		globalTotalStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		globalTotalStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		globalTotalStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		globalTotalStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());		
		
		relativePath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath());
	}
		
	public ExportCustomReport2Bean(SoberanoDatasource soberanoDatasource) {
		
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
	
	private void initWorkbookWithDailySalesSheet(Date from, Date until) {
		
		int dateColumnWidth = 3000;
		int productNameColumnWidth = 8000;
		int quantityColumnWidth = 3000;
		int priceColumnWidth = 3000;
		int amountColumnWidth = 3000;
		
		dailySalesSheet.setColumnWidth(0, dateColumnWidth);
		dailySalesSheet.setColumnWidth(1, productNameColumnWidth);
		dailySalesSheet.setColumnWidth(2, quantityColumnWidth);
		dailySalesSheet.setColumnWidth(3, priceColumnWidth);
		dailySalesSheet.setColumnWidth(4, amountColumnWidth);
		
		Row header = dailySalesSheet.createRow(0);
					
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Fecha");
		headerCell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 0);
		setBordersToMergedCells(dailySalesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("Producto");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 1, 1);
		setBordersToMergedCells(dailySalesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 2, 2);
		setBordersToMergedCells(dailySalesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("Precio");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 3, 3);
		setBordersToMergedCells(dailySalesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(4);
		headerCell.setCellValue("Importe");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 4, 4);
		setBordersToMergedCells(dailySalesSheet, cellRangeAddress, BorderStyle.MEDIUM);		
	}
	
	private void initWorkbookWithDailyMaterialExpensesSheet(Date from, Date until) {
		
		int dateColumnWidth = 3000;
		int itemNameColumnWidth = 8000;
		int quantityColumnWidth = 3000;
		int unitColumnWidth = 3000;
		int priceColumnWidth = 3000;
		int amountColumnWidth = 3000;
		
		dailyMaterialExpensesSheet.setColumnWidth(0, dateColumnWidth);
		dailyMaterialExpensesSheet.setColumnWidth(1, itemNameColumnWidth);
		dailyMaterialExpensesSheet.setColumnWidth(2, quantityColumnWidth);
		dailyMaterialExpensesSheet.setColumnWidth(3, unitColumnWidth);
		dailyMaterialExpensesSheet.setColumnWidth(4, priceColumnWidth);
		dailyMaterialExpensesSheet.setColumnWidth(5, amountColumnWidth);
		
		Row header = dailyMaterialExpensesSheet.createRow(0);
		
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Fecha");
		headerCell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 0);
		setBordersToMergedCells(dailyMaterialExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("Producto");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 1, 1);
		setBordersToMergedCells(dailyMaterialExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 2, 2);
		setBordersToMergedCells(dailyMaterialExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("Unidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 3, 3);
		setBordersToMergedCells(dailyMaterialExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(4);
		headerCell.setCellValue("Precio");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 4, 4);
		setBordersToMergedCells(dailyMaterialExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(5);
		headerCell.setCellValue("Importe");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 5, 5);
		setBordersToMergedCells(dailyMaterialExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);	
	}
	
	private void initWorkbookWithDailyServiceExpensesSheet(Date from, Date until) {
		
		int dateColumnWidth = 3000;
		int itemNameColumnWidth = 8000;
		int quantityColumnWidth = 3000;
		int unitColumnWidth = 3000;
		int priceColumnWidth = 3000;
		int amountColumnWidth = 3000;
		
		dailyServiceExpensesSheet.setColumnWidth(0, dateColumnWidth);
		dailyServiceExpensesSheet.setColumnWidth(1, itemNameColumnWidth);
		dailyServiceExpensesSheet.setColumnWidth(2, quantityColumnWidth);
		dailyServiceExpensesSheet.setColumnWidth(3, unitColumnWidth);
		dailyServiceExpensesSheet.setColumnWidth(4, priceColumnWidth);
		dailyServiceExpensesSheet.setColumnWidth(5, amountColumnWidth);
		
		Row header = dailyServiceExpensesSheet.createRow(0);
		
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Fecha");
		headerCell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 0);
		setBordersToMergedCells(dailyServiceExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("Servicio");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 1, 1);
		setBordersToMergedCells(dailyServiceExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 2, 2);
		setBordersToMergedCells(dailyServiceExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("Unidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 3, 3);
		setBordersToMergedCells(dailyServiceExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(4);
		headerCell.setCellValue("Precio");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 4, 4);
		setBordersToMergedCells(dailyServiceExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(5);
		headerCell.setCellValue("Importe");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 5, 5);
		setBordersToMergedCells(dailyServiceExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);	
	}
	
	private void initWorkbookWithMonthlyMaterialExpensesSheet(Date from, Date until) {
		
		int dateColumnWidth = 3000;
		int itemNameColumnWidth = 8000;
		int quantityColumnWidth = 3000;
		int unitColumnWidth = 3000;
		int amountColumnWidth = 3000;
		
		monthlyMaterialExpensesSheet.setColumnWidth(0, dateColumnWidth);
		monthlyMaterialExpensesSheet.setColumnWidth(1, itemNameColumnWidth);
		monthlyMaterialExpensesSheet.setColumnWidth(2, quantityColumnWidth);
		monthlyMaterialExpensesSheet.setColumnWidth(3, unitColumnWidth);
		monthlyMaterialExpensesSheet.setColumnWidth(4, amountColumnWidth);
		
		Row header = monthlyMaterialExpensesSheet.createRow(0);
		
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Fecha");
		headerCell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 0);
		setBordersToMergedCells(monthlyMaterialExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("Producto");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 1, 1);
		setBordersToMergedCells(monthlyMaterialExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 2, 2);
		setBordersToMergedCells(monthlyMaterialExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("Unidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 3, 3);
		setBordersToMergedCells(monthlyMaterialExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(4);
		headerCell.setCellValue("Importe");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 4, 4);
		setBordersToMergedCells(monthlyMaterialExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);	
	}
	
	private void initWorkbookWithMonthlyServiceExpensesSheet(Date from, Date until) {
		
		int dateColumnWidth = 3000;
		int itemNameColumnWidth = 8000;
		int quantityColumnWidth = 3000;
		int unitColumnWidth = 3000;
		int amountColumnWidth = 3000;
		
		monthlyServiceExpensesSheet.setColumnWidth(0, dateColumnWidth);
		monthlyServiceExpensesSheet.setColumnWidth(1, itemNameColumnWidth);
		monthlyServiceExpensesSheet.setColumnWidth(2, quantityColumnWidth);
		monthlyServiceExpensesSheet.setColumnWidth(3, unitColumnWidth);
		monthlyServiceExpensesSheet.setColumnWidth(4, amountColumnWidth);
		
		Row header = monthlyServiceExpensesSheet.createRow(0);
		
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Fecha");
		headerCell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 0);
		setBordersToMergedCells(monthlyServiceExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("Servicio");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 1, 1);
		setBordersToMergedCells(monthlyServiceExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 2, 2);
		setBordersToMergedCells(monthlyServiceExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("Unidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 3, 3);
		setBordersToMergedCells(monthlyServiceExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(4);
		headerCell.setCellValue("Importe");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 4, 4);
		setBordersToMergedCells(monthlyServiceExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
	}
	
	private void initWorkbookWithDailyPaymentsSheet(Date from, Date until) {
		
		int dateColumnWidth = 3000;
		int currencyColumnWidth = 3000;
		int amountColumnWidth = 3000;		
		
		dailyPaymentsSheet.setColumnWidth(0, dateColumnWidth);
		dailyPaymentsSheet.setColumnWidth(1, currencyColumnWidth);
		dailyPaymentsSheet.setColumnWidth(2, amountColumnWidth);
		
		Row header = dailyPaymentsSheet.createRow(0);
		
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Fecha");
		headerCell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 0);
		setBordersToMergedCells(dailyPaymentsSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("Moneda");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 1, 1);
		setBordersToMergedCells(dailyPaymentsSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Importe");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 2, 2);
		setBordersToMergedCells(dailyPaymentsSheet, cellRangeAddress, BorderStyle.MEDIUM);	
	}
	
	private void initWorkbookWithWarehouseStockSheet() {
		
		int warehouseColumnWidth = 3000;
		int itemColumnWidth = 8000;
		int quantityColumnWidth = 3000;	
		int unitColumnWidth = 3000;	
		int totalValueColumnWidth = 3000;	
		
		warehouseStockSheet.setColumnWidth(0, warehouseColumnWidth);
		warehouseStockSheet.setColumnWidth(1, itemColumnWidth);
		warehouseStockSheet.setColumnWidth(2, quantityColumnWidth);
		warehouseStockSheet.setColumnWidth(3, unitColumnWidth);
		warehouseStockSheet.setColumnWidth(4, totalValueColumnWidth);
		
		Row header = warehouseStockSheet.createRow(0);
		
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Almacén");
		headerCell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 0);
		setBordersToMergedCells(warehouseStockSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("Artículo");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 1, 1);
		setBordersToMergedCells(warehouseStockSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 2, 2);
		setBordersToMergedCells(warehouseStockSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("Valor unitario");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 3, 3);
		setBordersToMergedCells(warehouseStockSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(4);
		headerCell.setCellValue("Valor total");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 4, 4);
		setBordersToMergedCells(warehouseStockSheet, cellRangeAddress, BorderStyle.MEDIUM);
	}
	
	private void initWorkbookWithCatalogSheet() {
		
		int itemColumnWidth = 8000;
		int priceColumnWidth = 3000;
		
		catalogSheet.setColumnWidth(0, itemColumnWidth);
		catalogSheet.setColumnWidth(1, priceColumnWidth);
		
		Row header = catalogSheet.createRow(0);
		
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Producto");
		headerCell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 0);
		setBordersToMergedCells(catalogSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("Precio");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 1, 1);
		setBordersToMergedCells(catalogSheet, cellRangeAddress, BorderStyle.MEDIUM);
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
	
	private final class ExportDailySalesToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithDailySalesSheet(from, until);
			
			Integer rowCount = 1;
			Date previousDate = null;
			Integer initDayRow = 2;
			while (rs.next()) {
				
				Row row = dailySalesSheet.createRow(rowCount);
				
				if (previousDate != null && previousDate.compareTo(rs.getDate("orderdate")) != 0) {
					addDayTotalAmount(dailySalesSheet, rowCount, initDayRow, 4, "E");					
					initDayRow = rowCount + 2;					
					rowCount++;
				}
				
				previousDate = rs.getDate("orderdate");
				
				row = dailySalesSheet.createRow(rowCount);
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
					addDayTotalAmount(dailySalesSheet, rowCount, initDayRow, 4, "E");					
					initDayRow = rowCount + 2;					
					rowCount++;
				}
			}
			
			if (rowCount > 1) {
				CellRangeAddress cellRangeAddress = new CellRangeAddress(1, rowCount - 1, 0, 4);
				setBordersToMergedCells(dailySalesSheet, cellRangeAddress, BorderStyle.MEDIUM);
			}
												
			return null;
		}
	}
	
	private final class ExportDailyMaterialExpensesToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithDailyMaterialExpensesSheet(from, until);
			
			Integer rowCount = 1;
			Date previousDate = null;
			Integer initDayRow = 2;
			while (rs.next()) {
				
				Row row = dailyMaterialExpensesSheet.createRow(rowCount);
				
				if (previousDate != null && previousDate.compareTo(rs.getDate("expensedate")) != 0) {
					addDayTotalAmount(dailyMaterialExpensesSheet, rowCount, initDayRow, 5, "F");					
					initDayRow = rowCount + 2;					
					rowCount++;
				}
				
				previousDate = rs.getDate("expensedate");
				
				row = dailyMaterialExpensesSheet.createRow(rowCount);
				Cell cell = row.createCell(0);				
				cell.setCellValue(formatDate(rs.getDate("expensedate"), "yyyy-MM-dd"));
				cell.setCellStyle(dateStyle);
				
				cell = row.createCell(1);
				cell.setCellValue(rs.getString("iName"));
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(rs.getDouble("iQty"));
				cell.setCellStyle(style);
				
				cell = row.createCell(3);
				cell.setCellValue(rs.getString("iUnit"));
				cell.setCellStyle(style);
				
				cell = row.createCell(4);
				cell.setCellValue(rs.getDouble("iPrice"));
				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(5);
				String formula= "C" + Integer.valueOf(rowCount + 1).toString() + "*" + "E" + Integer.valueOf(rowCount + 1).toString();
				cell.setCellFormula(formula);
				cell.setCellStyle(moneyStyle);
				
				rowCount++;
				
				if (rs.isLast()) {
					addDayTotalAmount(dailyMaterialExpensesSheet, rowCount, initDayRow, 5, "F");					
					initDayRow = rowCount + 2;					
					rowCount++;
				}
			}
			
			if (rowCount > 1) {
				CellRangeAddress cellRangeAddress = new CellRangeAddress(1, rowCount - 1, 0, 5);
				setBordersToMergedCells(dailyMaterialExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
			}		
									
			return null;
		}
	}
	
	private final class ExportDailyServiceExpensesToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithDailyServiceExpensesSheet(from, until);
			
			Integer rowCount = 1;
			Date previousDate = null;
			Integer initDayRow = 2;
			while (rs.next()) {
				
				Row row = dailyServiceExpensesSheet.createRow(rowCount);
				
				if (previousDate != null && previousDate.compareTo(rs.getDate("expensedate")) != 0) {
					addDayTotalAmount(dailyServiceExpensesSheet, rowCount, initDayRow, 5, "F");					
					initDayRow = rowCount + 2;					
					rowCount++;
				}
				
				previousDate = rs.getDate("expensedate");
				
				row = dailyServiceExpensesSheet.createRow(rowCount);
				Cell cell = row.createCell(0);				
				cell.setCellValue(formatDate(rs.getDate("expensedate"), "yyyy-MM-dd"));
				cell.setCellStyle(dateStyle);
				
				cell = row.createCell(1);
				cell.setCellValue(rs.getString("iName"));
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(rs.getDouble("iQty"));
				cell.setCellStyle(style);
				
				cell = row.createCell(3);
				cell.setCellValue(rs.getString("iUnit"));
				cell.setCellStyle(style);
				
				cell = row.createCell(4);
				cell.setCellValue(rs.getDouble("iPrice"));
				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(5);
				String formula= "C" + Integer.valueOf(rowCount + 1).toString() + "*" + "E" + Integer.valueOf(rowCount + 1).toString();
				cell.setCellFormula(formula);
				cell.setCellStyle(moneyStyle);
				
				rowCount++;
				
				if (rs.isLast()) {
					addDayTotalAmount(dailyServiceExpensesSheet, rowCount, initDayRow, 5, "F");					
					initDayRow = rowCount + 2;					
					rowCount++;
				}
			}
			
			if (rowCount > 1) {
				CellRangeAddress cellRangeAddress = new CellRangeAddress(1, rowCount - 1, 0, 5);
				setBordersToMergedCells(dailyServiceExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
			}
									
			return null;
		}
	}
	
	private final class ExportMonthlyMaterialExpensesToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithMonthlyMaterialExpensesSheet(from, until);
			
			Integer rowCount = 1;
			String previousDate = null;
			Integer initDayRow = 2;
			while (rs.next()) {
				
				Row row = monthlyMaterialExpensesSheet.createRow(rowCount);
				
				if (previousDate != null && !previousDate.equals(rs.getString("expensedate"))) {
					addDayTotalAmount(monthlyMaterialExpensesSheet, rowCount, initDayRow, 4, "E");					
					initDayRow = rowCount + 2;					
					rowCount++;
				}
				
				previousDate = rs.getString("expensedate");
				
				row = monthlyMaterialExpensesSheet.createRow(rowCount);
				Cell cell = row.createCell(0);				
				cell.setCellValue(rs.getString("expensedate"));
				cell.setCellStyle(dateStyle);
				
				cell = row.createCell(1);
				cell.setCellValue(rs.getString("iName"));
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(rs.getDouble("iQty"));
				cell.setCellStyle(style);
				
				cell = row.createCell(3);
				cell.setCellValue(rs.getString("iUnit"));
				cell.setCellStyle(style);
				
				cell = row.createCell(4);
				cell.setCellValue(rs.getDouble("amount"));
				cell.setCellStyle(moneyStyle);
				
				rowCount++;
				
				if (rs.isLast()) {
					addDayTotalAmount(monthlyMaterialExpensesSheet, rowCount, initDayRow, 4, "E");					
					initDayRow = rowCount + 2;					
					rowCount++;
				}
			}
			
			if (rowCount > 1) {
				CellRangeAddress cellRangeAddress = new CellRangeAddress(1, rowCount - 1, 0, 4);
				setBordersToMergedCells(monthlyMaterialExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
			}
												
			return null;
		}
	}
	
	private final class ExportMonthlyServiceExpensesToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithMonthlyServiceExpensesSheet(from, until);
			
			Integer rowCount = 1;
			String previousDate = null;
			Integer initDayRow = 2;
			while (rs.next()) {
				
				Row row = monthlyServiceExpensesSheet.createRow(rowCount);
				
				if (previousDate != null && !previousDate.equals(rs.getString("expensedate"))) {
					addDayTotalAmount(monthlyServiceExpensesSheet, rowCount, initDayRow, 4, "E");					
					initDayRow = rowCount + 2;					
					rowCount++;
				}
				
				previousDate = rs.getString("expensedate");
				
				row = monthlyServiceExpensesSheet.createRow(rowCount);
				Cell cell = row.createCell(0);				
				cell.setCellValue((rs.getString("expensedate")));
				cell.setCellStyle(dateStyle);
				
				cell = row.createCell(1);
				cell.setCellValue(rs.getString("iName"));
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(rs.getDouble("iQty"));
				cell.setCellStyle(style);
				
				cell = row.createCell(3);
				cell.setCellValue(rs.getString("iUnit"));
				cell.setCellStyle(style);
				
				cell = row.createCell(4);
				cell.setCellValue(rs.getDouble("amount"));
				cell.setCellStyle(moneyStyle);
				
				rowCount++;
				
				if (rs.isLast()) {
					addDayTotalAmount(monthlyServiceExpensesSheet, rowCount, initDayRow, 4, "E");					
					initDayRow = rowCount + 2;					
					rowCount++;
				}
			}
			
			if (rowCount > 1) {
				CellRangeAddress cellRangeAddress = new CellRangeAddress(1, rowCount - 1, 0, 4);
				setBordersToMergedCells(monthlyServiceExpensesSheet, cellRangeAddress, BorderStyle.MEDIUM);
			}
			
									
			return null;
		}
	}
	
	private final class ExportDailyPaymentsToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithDailyPaymentsSheet(from, until);
			
			Integer rowCount = 1;
			while (rs.next()) {
				
				Row row = dailyPaymentsSheet.createRow(rowCount);
				
				row = dailyPaymentsSheet.createRow(rowCount);
				Cell cell = row.createCell(0);				
				cell.setCellValue(formatDate(rs.getDate("orderdate"), "yyyy-MM-dd"));
				cell.setCellStyle(dateStyle);
				
				cell = row.createCell(1);
				cell.setCellValue(rs.getString("curr"));
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(rs.getDouble("amount"));
				cell.setCellStyle(moneyStyle);
				
				rowCount++;
			}
			
			if (rowCount > 1) {
				CellRangeAddress cellRangeAddress = new CellRangeAddress(1, rowCount - 1, 0, 2);
				setBordersToMergedCells(dailyPaymentsSheet, cellRangeAddress, BorderStyle.MEDIUM);
			}
				
			return null;
		}
	}
	
	private final class ExportWarehouseStockToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			initWorkbookWithWarehouseStockSheet();
			
			Integer rowCount = 1;
			while (rs.next()) {
				
				Row row = warehouseStockSheet.createRow(rowCount);				
								
				row = warehouseStockSheet.createRow(rowCount);
				Cell cell = row.createCell(0);				
				cell.setCellValue(rs.getString("whouse"));
				cell.setCellStyle(style);
				
				cell = row.createCell(1);
				cell.setCellValue(rs.getString("iName"));
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(rs.getDouble("iQty"));
				cell.setCellStyle(style);
				
				cell = row.createCell(3);
				cell.setCellValue(rs.getDouble("iUnitValue"));
				cell.setCellStyle(style);
				
				cell = row.createCell(4);
				cell.setCellValue(rs.getDouble("iTotalValue"));
				cell.setCellStyle(moneyStyle);
				
				rowCount++;
			}
			
			if (rowCount > 1) {
				CellRangeAddress cellRangeAddress = new CellRangeAddress(1, rowCount - 1, 0, 4);
				setBordersToMergedCells(warehouseStockSheet, cellRangeAddress, BorderStyle.MEDIUM);
			}
			
			return null;
		}
	}
	
	private void addProductCategoryRow(Integer rowCount, String categoryName) {
		Row row = catalogSheet.createRow(rowCount);
		Cell cell = row.createCell(0);				
		cell.setCellValue(categoryName);
		cell.setCellStyle(headerStyle);
		
		CellRangeAddress cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 0, 1);
		setBordersToMergedCells(catalogSheet, cellRangeAddress, BorderStyle.MEDIUM);
	}
	
	private final class ExportCatalogToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			initWorkbookWithCatalogSheet();
			
			Integer rowCount = 1;
			String previousCategory = "";
			while (rs.next()) {
				
				Row row = catalogSheet.createRow(rowCount);
				
				if (previousCategory.isEmpty() || !previousCategory.equals(rs.getString("categoryName"))) {
					addProductCategoryRow(rowCount, rs.getString("categoryName"));	
					rowCount++;
				}
				
				previousCategory = rs.getString("categoryName");
				
				row = catalogSheet.createRow(rowCount);
				Cell cell = row.createCell(0);				
				cell.setCellValue(rs.getString("productName"));
				cell.setCellStyle(style);
				
				cell = row.createCell(1);
				cell.setCellValue(rs.getDouble("productPrice"));
				cell.setCellStyle(moneyStyle);
				
				rowCount++;
			}
			
			if (rowCount > 1) {
				CellRangeAddress cellRangeAddress = new CellRangeAddress(1, rowCount - 1, 0, 1);
				setBordersToMergedCells(catalogSheet, cellRangeAddress, BorderStyle.MEDIUM);
			}
									
			return null;
		}
	}
	
	private Object runDailySalesDBQuery(Date from, Date until, String costCenter) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport2_dailySales\"(:lang, :fromD, :untilD, :ccenter, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("ccenter", costCenter);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportDailySalesToXLSExtractor());
	}
	
	private Object runDailyMaterialExpensesDBQuery(Date from, Date until) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport2_dailyMaterialExpenses\"(:lang, :fromD, :untilD, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportDailyMaterialExpensesToXLSExtractor());
	}
	
	private Object runDailyServiceExpensesDBQuery(Date from, Date until) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport2_dailyServiceExpenses\"(:lang, :fromD, :untilD, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportDailyServiceExpensesToXLSExtractor());
	}
	
	private Object runMonthlyMaterialExpensesDBQuery(Date from, Date until) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport2_monthlyMaterialExpenses\"(:lang, :fromD, :untilD, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportMonthlyMaterialExpensesToXLSExtractor());
	}
	
	private Object runMonthlyServiceExpensesDBQuery(Date from, Date until) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport2_monthlyServiceExpenses\"(:lang, :fromD, :untilD, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportMonthlyServiceExpensesToXLSExtractor());
	}
	
	private Object runDailyPaymentsDBQuery(Date from, Date until) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport2_dailyPayments\"(:lang, :fromD, :untilD, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportDailyPaymentsToXLSExtractor());
	}
	
	private Object runWarehouseStockDBQuery() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport2_Stock\"(:lang, :warehouse, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());	
		qryParameters.put("warehouse", "");
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportWarehouseStockToXLSExtractor());
	}
	
	private Object runCatalogDBQuery() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport2_Catalog\"(:lang, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportCatalogToXLSExtractor());
	}
	
	private void getCustomReportToXlsx(Date from, Date until, String costCenter) throws SQLException, IOException {
		
		try {
			initWorkbook();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//first sheet
		runDailySalesDBQuery(from, until, costCenter);
				
		//second sheet
		runDailyMaterialExpensesDBQuery(from, until);
				
		//third sheet
		runDailyServiceExpensesDBQuery(from, until);
		
		//fourth
		runMonthlyMaterialExpensesDBQuery(from, until);
		
		//fifth
		runMonthlyServiceExpensesDBQuery(from, until);
		
		//sixth
		runDailyPaymentsDBQuery(from, until);
		
		//seventh
		runWarehouseStockDBQuery();
		
		//eighth
		runCatalogDBQuery();
		
		//workbook file creation//
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fromDateStr = "";
		String untilDateStr = "";
		try {
			fromDateStr = dateFormat.format(from);
			untilDateStr = dateFormat.format(until);
		} 
		catch(Exception ex) {};
		
		String xlsFileName = "custom_report_2_from_" + fromDateStr + "_to_" + untilDateStr + ".xlsx";
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
