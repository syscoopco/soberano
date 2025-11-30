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

public class ExportCustomReport3Bean extends ExportBean implements IExportToFile {
	
private Workbook workbook = null;
	
	private Sheet reportSheet = null;
	
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
		
		reportSheet = workbook.createSheet("Hoja 1");
		
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
	
	private void initWorkbookWithReportSheet(Date from, Date until) {
		
		int productNameColumnWidth = 8000;
		int unitCostColumnWidth = 3000;
		int priceColumnWidth = 3000;
		int unitGainColumnWidth = 3000;
		int quantityColumnWidth = 3000;
		int totalGainColumnWidth = 3000;
		int salesColumnWidth = 3000;
		int _1percentageColumnWidth = 3000;
		int _2percentageColumnWidth = 3000;
		int _3percentageColumnWidth = 3000;
		int _4percentageColumnWidth = 3000;
		int _5percentageColumnWidth = 3000;
		int openingColumnWidth = 3000;
		int inputsColumnWidth = 3000;
		int outputsStockColumnWidth = 3000;
		int currentStockColumnWidth = 3000;
		
		reportSheet.setColumnWidth(0, productNameColumnWidth);
		reportSheet.setColumnWidth(1, unitCostColumnWidth);
		reportSheet.setColumnWidth(2, priceColumnWidth);
		reportSheet.setColumnWidth(3, unitGainColumnWidth);
		reportSheet.setColumnWidth(4, quantityColumnWidth);
		reportSheet.setColumnWidth(5, totalGainColumnWidth);
		reportSheet.setColumnWidth(6, salesColumnWidth);
		reportSheet.setColumnWidth(7, _1percentageColumnWidth);
		reportSheet.setColumnWidth(8, _2percentageColumnWidth);
		reportSheet.setColumnWidth(9, _3percentageColumnWidth);
		reportSheet.setColumnWidth(10, _4percentageColumnWidth);
		reportSheet.setColumnWidth(11, _5percentageColumnWidth);
		reportSheet.setColumnWidth(12, openingColumnWidth);
		reportSheet.setColumnWidth(13, inputsColumnWidth);
		reportSheet.setColumnWidth(14, outputsStockColumnWidth);
		reportSheet.setColumnWidth(15, currentStockColumnWidth);
		
		Row header = reportSheet.createRow(0);
					
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Producto");
		headerCell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 0);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("Costo unitario");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 1, 1);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Precio");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 2, 2);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("Ganancia unitaria");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 3, 3);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(4);
		headerCell.setCellValue("Cantidad");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 4, 4);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(5);
		headerCell.setCellValue("Ganancia total");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 5, 5);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(6);
		headerCell.setCellValue("Ventas");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 6, 6);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(7);
		headerCell.setCellValue("1%");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 7, 7);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(8);
		headerCell.setCellValue("2%");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 8, 8);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);	
		
		headerCell = header.createCell(9);
		headerCell.setCellValue("3%");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 9, 9);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(10);
		headerCell.setCellValue("4%");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 10, 10);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(11);
		headerCell.setCellValue("5%");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 11, 11);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = header.createCell(12);
		headerCell.setCellValue("Inicio");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 12, 12);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);	
		
		headerCell = header.createCell(13);
		headerCell.setCellValue("Entradas");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 12, 13);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);	
		
		headerCell = header.createCell(14);
		headerCell.setCellValue("Salidas");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 12, 14);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);	
		
		headerCell = header.createCell(15);
		headerCell.setCellValue("Existencias");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(0, 0, 12, 15);
		setBordersToMergedCells(reportSheet, cellRangeAddress, BorderStyle.MEDIUM);	
	}
	
	private void addDayTotalsRows(Sheet sheet, Integer rowCount) {
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
		
		cell = row.createCell(7);
		cell.setCellStyle(totalStyle);
		formula= "SUM(" + "H2:H" + Integer.valueOf(rowCount).toString() + ")";
		cell.setCellFormula(formula);
		
		cell = row.createCell(8);
		cell.setCellStyle(totalStyle);
		formula= "SUM(" + "I2:I" + Integer.valueOf(rowCount).toString() + ")";
		cell.setCellFormula(formula);
		
		cell = row.createCell(9);
		cell.setCellStyle(totalStyle);
		formula= "SUM(" + "J2:J" + Integer.valueOf(rowCount).toString() + ")";
		cell.setCellFormula(formula);
		
		cell = row.createCell(10);
		cell.setCellStyle(totalStyle);
		formula= "SUM(" + "K2:K" + Integer.valueOf(rowCount).toString() + ")";
		cell.setCellFormula(formula);
		
		cell = row.createCell(11);
		cell.setCellStyle(totalStyle);
		formula= "SUM(" + "L2:L" + Integer.valueOf(rowCount).toString() + ")";
		cell.setCellFormula(formula);
	}
	
	private final class ExportSheet1ToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithReportSheet(from, until);
			
			Integer rowCount = 1;
			while (rs.next()) {
				
				Row row = reportSheet.createRow(rowCount);
				
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
				
				cell = row.createCell(7);
				formula = "G" + Integer.valueOf(rowCount + 1).toString() + "*1%";
				cell.setCellFormula(formula);
				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(8);
				formula = "G" + Integer.valueOf(rowCount + 1).toString() + "*2%";
				cell.setCellFormula(formula);
				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(9);
				formula = "G" + Integer.valueOf(rowCount + 1).toString() + "*3%";
				cell.setCellFormula(formula);
				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(10);
				formula = "G" + Integer.valueOf(rowCount + 1).toString() + "*4%";
				cell.setCellFormula(formula);
				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(11);
				formula = "G" + Integer.valueOf(rowCount + 1).toString() + "*5%";
				cell.setCellFormula(formula);
				cell.setCellStyle(moneyStyle);
				
				cell = row.createCell(12);
				cell.setCellValue(rs.getDouble("opening"));
				cell.setCellStyle(style);
				
				cell = row.createCell(13);
				cell.setCellValue(rs.getDouble("inputs"));
				cell.setCellStyle(style);
				
				cell = row.createCell(14);
				cell.setCellValue(rs.getDouble("outputs"));
				cell.setCellStyle(style);
				
				cell = row.createCell(15);
				cell.setCellValue(rs.getDouble("ending"));
				cell.setCellStyle(style);
				
				rowCount++;
				
				if (rs.isLast()) {
					addDayTotalsRows(reportSheet, rowCount);
				}
			}
												
			return null;
		}
	}
	
	private Object runFirstSheetDBQuery(Date from, Date until, String costCenter) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport3_query\"(:lang, :fromD, :untilD, :ccenter, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("ccenter", costCenter);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportSheet1ToXLSExtractor());
	}
	
	private void getCustomReportToXlsx(Date from, Date until, String costCenter) throws SQLException, IOException {
		
		try {
			initWorkbook();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//first sheet
		runFirstSheetDBQuery(from, until, costCenter);
		
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
