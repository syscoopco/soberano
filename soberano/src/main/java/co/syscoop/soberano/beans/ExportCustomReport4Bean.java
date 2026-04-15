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

public class ExportCustomReport4Bean extends ExportBean implements IExportToFile {
	
private Workbook workbook = null;
	
	private Sheet procurementSheet = null;
	private Sheet inventorySheet = null;
	private Sheet salesSheet = null;
	
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
		
		procurementSheet = workbook.createSheet("Compras");
		inventorySheet = workbook.createSheet("Inventario");
		salesSheet = workbook.createSheet("Ventas");
		
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
		
		Row exchangeRateRow = procurementSheet.createRow(0);
		Cell cell = exchangeRateRow.createCell(1);				
		cell.setCellValue("Tasa de cambio");
		cell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 0);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		cell = exchangeRateRow.createCell(2);				
		cell.setCellValue("1.0");
		cell.setCellStyle(style);
		
		procurementSheet.createRow(1);
		Row header = procurementSheet.createRow(2);
					
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("No.");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 0, 0);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("Fecha");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 1, 1);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Proveedor");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 2, 2);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("Referencia");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 3, 3);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(4);
		headerCell.setCellValue("Producto");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 4, 4);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(5);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 5, 5);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(6);
		headerCell.setCellValue("Unidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 6, 6);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(7);
		headerCell.setCellValue("Importe CUP");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 7, 7);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(8);
		headerCell.setCellValue("Importe USD");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 8, 8);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(9);
		headerCell.setCellValue("Importe USD a CUP");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 9, 9);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(10);
		headerCell.setCellValue("Importe total");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 10, 10);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
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
	
	private void initWorkbookWithInventorySheet(Date from, Date until) {
		
		int numberColumnWidth = 1000;
		int dateColumnWidth = 3000;
		int productNameColumnWidth = 8000;
		int quantityColumnWidth = 4000;
		int valueColumnWidth = 4000;
		
		inventorySheet.setColumnWidth(0, numberColumnWidth);
		inventorySheet.setColumnWidth(1, dateColumnWidth);
		inventorySheet.setColumnWidth(3, productNameColumnWidth);
		
		Row warehouseHeader = inventorySheet.createRow(0);
		Row header = inventorySheet.createRow(1);
		
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("No.");
		headerCell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(1, 1, 0, 0);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("Fecha");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 1, 1);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);		
				
		headerCell = header.createCell(2);
		headerCell.setCellValue("Producto");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(2, 2, 2, 2);
		setBordersToMergedCells(procurementSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		addWarehouseHeaderCells("Almacén", procurementSheet, warehouseHeader, header, 
								headerStyle, 1, quantityColumnWidth, valueColumnWidth);
		
		addWarehouseHeaderCells("Mostrador", procurementSheet, warehouseHeader, header, 
								headerStyle, 7, quantityColumnWidth, valueColumnWidth);	
	}
	
	private final class ExportProcurementSheetToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithProcurementSheet(from, until);
			
			Integer rowCount = 3;
			while (rs.next()) {
				
				Row row = procurementSheet.createRow(rowCount);
				
				Cell cell = row.createCell(0);							//A				
				cell.setCellValue(rowCount - 1);
				cell.setCellStyle(style);
				
				cell = row.createCell(1);
				cell.setCellValue(rs.getString("expenseDate"));			//B
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
				Integer columnIndex = Integer.valueOf(rowCount + 1);
				String formula = "C1" + "*I" + Integer.valueOf(rowCount + 1).toString();
				cell.setCellFormula(formula);
				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(10);				
				formula = "F" + columnIndex.toString() + "*(H" + columnIndex.toString() + "+J" + columnIndex.toString() + ")";
				cell.setCellFormula(formula);
				cell.setCellStyle(moneyStyle);
			
				rowCount++;
				
//				if (rs.isLast()) {
//					addSheet1TotalsRows(reportSheet1, rowCount);
//				}
			}
												
			return null;
		}
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
	
	private void getCustomReportToXlsx(Date from, Date until, String costCenter) throws SQLException, IOException {
		
		try {
			initWorkbook();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//first sheet
		runProcurementSheetDBQuery(from, until, costCenter);
		
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
