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
		dailyPaymentsSheet = workbook.createSheet("Cobros diarios");
		warehouseStockSheet = workbook.createSheet("Existencias");
		monthlyMaterialExpensesSheet = workbook.createSheet("Gastos materiales mensuales");
		monthlyServiceExpensesSheet = workbook.createSheet("Gastos en servicios mensuales");
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
		
		dailySalesSheet.setColumnWidth(0, dateColumnWidth);
		dailySalesSheet.setColumnWidth(1, itemNameColumnWidth);
		dailySalesSheet.setColumnWidth(2, quantityColumnWidth);
		dailySalesSheet.setColumnWidth(3, unitColumnWidth);
		dailySalesSheet.setColumnWidth(4, priceColumnWidth);
		dailySalesSheet.setColumnWidth(5, amountColumnWidth);		
	}
	
	private void initWorkbookWithDailyServiceExpensesSheet(Date from, Date until) {
		
		int dateColumnWidth = 3000;
		int itemNameColumnWidth = 8000;
		int amountColumnWidth = 3000;
		
		dailySalesSheet.setColumnWidth(0, dateColumnWidth);
		dailySalesSheet.setColumnWidth(1, itemNameColumnWidth);
		dailySalesSheet.setColumnWidth(2, amountColumnWidth);		
	}
	
	private void initWorkbookWithDailyPaymentsSheet(Date from, Date until) {
		
		int dateColumnWidth = 3000;
		int currencyColumnWidth = 3000;
		int orderColumnWidth = 3000;
		int amountColumnWidth = 3000;		
		
		dailySalesSheet.setColumnWidth(0, dateColumnWidth);
		dailySalesSheet.setColumnWidth(1, currencyColumnWidth);
		dailySalesSheet.setColumnWidth(2, orderColumnWidth);
		dailySalesSheet.setColumnWidth(3, amountColumnWidth);		
	}
	
	private void initWorkbookWithWarehouseStockSheet() {
		
		int itemCodeColumnWidth = 3000;
		int itemNameColumnWidth = 8000;
		int unitColumnWidth = 3000;
		int quantityColumnWidth = 3000;
		int unitCostColumnWidth = 3000;
		int valueColumnWidth = 3000;
		
		dailySalesSheet.setColumnWidth(0, itemCodeColumnWidth);
		dailySalesSheet.setColumnWidth(1, itemNameColumnWidth);
		dailySalesSheet.setColumnWidth(2, unitColumnWidth);
		dailySalesSheet.setColumnWidth(3, quantityColumnWidth);
		dailySalesSheet.setColumnWidth(4, unitCostColumnWidth);
		dailySalesSheet.setColumnWidth(5, valueColumnWidth);	
	}
	
	private void initWorkbookWithMonthlyMaterialExpensesSheet(Date from, Date until) {
		
		int itemNameColumnWidth = 8000;
		int quantityColumnWidth = 3000;
		int unitColumnWidth = 3000;
		int priceColumnWidth = 3000;
		int amountColumnWidth = 3000;
		
		dailySalesSheet.setColumnWidth(0, itemNameColumnWidth);
		dailySalesSheet.setColumnWidth(1, quantityColumnWidth);
		dailySalesSheet.setColumnWidth(2, unitColumnWidth);
		dailySalesSheet.setColumnWidth(3, priceColumnWidth);
		dailySalesSheet.setColumnWidth(4, amountColumnWidth);		
	}
	
	private void initWorkbookWithMonthlyServiceExpensesSheet(Date from, Date until) {
		
		int itemNameColumnWidth = 8000;
		int amountColumnWidth = 3000;
		
		dailySalesSheet.setColumnWidth(0, itemNameColumnWidth);
		dailySalesSheet.setColumnWidth(1, amountColumnWidth);		
	}
	
	private void initWorkbookWithCatalogSheet() {
		
		int itemNameColumnWidth = 8000;
		int priceColumnWidth = 3000;
		
		dailySalesSheet.setColumnWidth(0, itemNameColumnWidth);
		dailySalesSheet.setColumnWidth(1, priceColumnWidth);		
	}
	
	private void addDayTotalAmount(Integer rowCount, Integer initDayRow) {
		Row row = dailySalesSheet.createRow(rowCount);
		Cell cell = row.createCell(0);				
		cell.setCellValue("TOTAL");
		cell.setCellStyle(headerStyle);
		
		cell = row.createCell(4);
		cell.setCellStyle(totalStyle);
		String formula= "SUM(E" + initDayRow.toString() + ":E" + Integer.valueOf(rowCount).toString() + ")";
		cell.setCellFormula(formula);
		
		CellRangeAddress cellRangeAddress = new CellRangeAddress(initDayRow - 1, rowCount - 1, 0, 0);
		row.getSheet().addMergedRegion(cellRangeAddress);
		setBordersToMergedCells(row.getSheet(), cellRangeAddress, BorderStyle.MEDIUM);
		
		cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 0, 4);
		setBordersToMergedCells(row.getSheet(), cellRangeAddress, BorderStyle.MEDIUM);
	}
	
	private final class ExportDailySalesToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			try {
				initWorkbook();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithDailySalesSheet(from, until);
			
			Integer rowCount = 1;
			Date previousDate = null;
			Integer initDayRow = 2;
			while (rs.next()) {
				
				Row row = dailySalesSheet.createRow(rowCount);
				
				if (previousDate != null && previousDate.compareTo(rs.getDate("orderdate")) != 0) {
					addDayTotalAmount(rowCount, initDayRow);					
					initDayRow = rowCount + 2;					
					rowCount++;
				}
				
				previousDate = rs.getDate("orderdate");
				
				row = dailySalesSheet.createRow(rowCount);
				Cell cell = row.createCell(0);				
				cell.setCellValue(formatDate(rs.getDate("orderdate"), "yyyy-MM-dd"));
				cell.setCellValue(rs.getString("orderdate"));
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
					addDayTotalAmount(rowCount, initDayRow);					
					initDayRow = rowCount + 2;					
					rowCount++;
				}
			}
			
			CellRangeAddress cellRangeAddress = new CellRangeAddress(1, rowCount - 1, 0, 4);
			setBordersToMergedCells(dailySalesSheet, cellRangeAddress, BorderStyle.MEDIUM);
									
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
	
	private void getCustomReportToXlsx(Date from, Date until, String costCenter) throws SQLException, IOException {
		
		//first sheet
		runDailySalesDBQuery(from, until, costCenter);
				
		//second sheet
				
		//third sheet
		
		//fourth
		
		//fifth
		
		//sixth
		
		//seventh
		
		//eighth
		
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
