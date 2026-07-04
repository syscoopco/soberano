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

import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
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

public class ExportCustomReport5Bean extends ExportBean implements IExportToFile {
	
private Workbook workbook = null;
	
	private Sheet ipvSheet = null;
	
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
		
		ipvSheet = workbook.createSheet("IPV");
		
		// 1. Retrieve the PrintSetup object for the worksheet
        PrintSetup printSetup = ipvSheet.getPrintSetup();

        // 2. Set the Page Orientation (true = Landscape, false = Portrait)
        printSetup.setLandscape(true);

        // 3. Set the Page Size using built-in XSSFPrintSetup/PrintSetup constants
        printSetup.setPaperSize(PrintSetup.LETTER_PAPERSIZE);
        
        // lock column headers
        ipvSheet.setRepeatingRows(CellRangeAddress.valueOf("2:2"));
        
        // 1. Get the footer object for the current worksheet
        Footer footer = ipvSheet.getFooter();

        // 2. Set the dynamic page variables into the center slot
        // Expected output format: "Page 1 of 5"
        footer.setCenter(HeaderFooter.page() + " / " + HeaderFooter.numPages());
		
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
		
	public ExportCustomReport5Bean(SoberanoDatasource soberanoDatasource) {
		
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
		
	private void initWorkbookWithIPVSheet(Date from, Date until) {
		
		//title row
		Row titleRow = ipvSheet.createRow(0);
		Cell auxCell = titleRow.createCell(0);
		auxCell.setCellValue("FECHA:");
		auxCell.setCellStyle(headerStyle);
		auxCell = titleRow.createCell(3);
		auxCell.setCellValue("TRABAJADOR:");
		auxCell.setCellStyle(headerStyle);	
		
		//blank row
		ipvSheet.createRow(1);
				
		//ipv table
		int productNameColumnWidth = 7000;
		int inicioColumnWidth = 1900;
		int entradaColumnWidth = 1900;
		int movimientoColumnWidth = 3000;
		int mermaColumnWidth = 1900;
		int enVentaColumnWidth = 1900;
		int finalColumnWidth = 1900;
		int vendidoColumnWidth = 1900;
		int precioColumnWidth = 1900;
		int totalColumnWidth = 1900;
		
		ipvSheet.setColumnWidth(0, productNameColumnWidth);
		ipvSheet.setColumnWidth(1, inicioColumnWidth);
		ipvSheet.setColumnWidth(2, entradaColumnWidth);
		ipvSheet.setColumnWidth(3, movimientoColumnWidth);
		ipvSheet.setColumnWidth(4, mermaColumnWidth);
		ipvSheet.setColumnWidth(5, enVentaColumnWidth);
		ipvSheet.setColumnWidth(6, finalColumnWidth);
		ipvSheet.setColumnWidth(7, vendidoColumnWidth);
		ipvSheet.setColumnWidth(8, precioColumnWidth);
		ipvSheet.setColumnWidth(9, totalColumnWidth);
		
		Row columnHeaders = ipvSheet.createRow(1);
					
		Cell headerCell = columnHeaders.createCell(0);
		headerCell.setCellValue("Producto");
		headerCell.setCellStyle(headerStyle);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(1, 1, 0, 0);
		setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.MEDIUM);

		headerCell = columnHeaders.createCell(1);
		headerCell.setCellValue("Inicio");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 1, 1);
		setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = columnHeaders.createCell(2);
		headerCell.setCellValue("Entrada");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 2, 2);
		setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = columnHeaders.createCell(3);
		headerCell.setCellValue("Movimiento");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 3, 3);
		setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = columnHeaders.createCell(4);
		headerCell.setCellValue("Merma");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 4, 4);
		setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = columnHeaders.createCell(5);
		headerCell.setCellValue("En venta");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 5, 5);
		setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = columnHeaders.createCell(6);
		headerCell.setCellValue("Final");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 6, 6);
		setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = columnHeaders.createCell(7);
		headerCell.setCellValue("Vendido");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 7, 7);
		setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = columnHeaders.createCell(8);
		headerCell.setCellValue("Precio");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 8, 8);
		setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.MEDIUM);
		
		headerCell = columnHeaders.createCell(9);
		headerCell.setCellValue("Total");
		headerCell.setCellStyle(headerStyle);
		cellRangeAddress = new CellRangeAddress(1, 1, 9, 9);
		setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.MEDIUM);		
	}
			
	private final class ExportIPVToXLSExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			
			try {
				initWorkbook();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Date from = (Date) getParameters().get("from");
			Date until = (Date) getParameters().get("until");
			
			initWorkbookWithIPVSheet(from, until);
			
			Integer rowCount = 2;
			while (rs.next()) {
				
				Row row = ipvSheet.createRow(rowCount);
				
				Cell cell = row.createCell(0);
				cell.setCellValue(rs.getString("pName"));
				cell.setCellStyle(style);
				
				CellRangeAddress cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 0, 0);
				setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);

				cell = row.createCell(1);
				//cell.setCellValue(rs.getDouble("pStart"));
				cell.setCellStyle(style);
				
				cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 1, 1);
				setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
				
				cell = row.createCell(2);
				//cell.setCellValue(rs.getDouble("pInput"));
				cell.setCellStyle(style);
				
				cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 2, 2);
				setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
				
				cell = row.createCell(3);
				//cell.setCellValue(rs.getDouble("pMovement"));
				cell.setCellStyle(style);
				
				cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 3, 3);
				setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
				
				cell = row.createCell(4);
				//cell.setCellValue(rs.getDouble("pLoss"));
				cell.setCellStyle(style);
				
				cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 4, 4);
				setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
				
				cell = row.createCell(5);
				//cell.setCellValue(rs.getDouble("pOnSale"));
				cell.setCellStyle(style);
				
				cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 5, 5);
				setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
				
				cell = row.createCell(6);
				//cell.setCellValue(rs.getDouble("pFinal"));
				cell.setCellStyle(style);
				
				cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 6, 6);
				setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
				
				cell = row.createCell(7);
				//cell.setCellValue(rs.getDouble("pSold"));
				cell.setCellStyle(style);
				
				cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 7, 7);
				setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
				
				cell = row.createCell(8);
				//cell.setCellValue(rs.getDouble("pPrice"));
				cell.setCellStyle(style);
				
				cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 8, 8);
				setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
				
				cell = row.createCell(9);
				//cell.setCellValue(rs.getDouble("pAmount"));
				cell.setCellStyle(style);
				
				cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 9, 9);
				setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
				
				rowCount++;
				
				if (rs.isLast()) {
					
					for (int i = 0; i < 10; i++) {
						
						row = ipvSheet.createRow(rowCount);
						
						cell = row.createCell(0);
						cell.setCellStyle(style);
						
						cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 0, 0);
						setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);

						cell = row.createCell(1);
						cell.setCellStyle(style);
						
						cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 1, 1);
						setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
						
						cell = row.createCell(2);
						cell.setCellStyle(style);
						
						cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 2, 2);
						setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
						
						cell = row.createCell(3);
						cell.setCellStyle(style);
						
						cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 3, 3);
						setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
						
						cell = row.createCell(4);
						cell.setCellStyle(style);
						
						cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 4, 4);
						setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
						
						cell = row.createCell(5);
						cell.setCellStyle(style);
						
						cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 5, 5);
						setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
						
						cell = row.createCell(6);
						cell.setCellStyle(style);
						
						cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 6, 6);
						setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
						
						cell = row.createCell(7);
						cell.setCellStyle(style);
						
						cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 7, 7);
						setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
						
						cell = row.createCell(8);
						cell.setCellStyle(style);
						
						cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 8, 8);
						setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
						
						cell = row.createCell(9);
						cell.setCellStyle(style);
						
						cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 9, 9);
						setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.THIN);
						
						rowCount++;
					}
					
					cellRangeAddress = new CellRangeAddress(2, rowCount - 1, 0, 9);
					setBordersToMergedCells(ipvSheet, cellRangeAddress, BorderStyle.MEDIUM);
				}
		    }

			return null;
		}
	}
	
	private Object runIPVDBQuery(Date from, Date until, String costCenter) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"z-fn_ReportData_customReport5_IPV\"(:lang, :fromD, :untilD, :ccenter, :loginname) AS queryresult";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());		
		qryParameters.put("fromD", from);
		qryParameters.put("untilD", until);
		qryParameters.put("ccenter", costCenter);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(query, qryParameters, new ExportIPVToXLSExtractor());
	}
	
	private void getCustomReportToXlsx(Date from, Date until, String costCenter) throws SQLException, IOException {
		
		//first sheet
		runIPVDBQuery(from, until, costCenter);
		
		//workbook file creation//
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fromDateStr = "";
		String untilDateStr = "";
		try {
			fromDateStr = dateFormat.format(from);
			untilDateStr = dateFormat.format(until);
		} 
		catch(Exception ex) {};
		
		String xlsFileName = "custom_report_5_from_" + fromDateStr + "_to_" + untilDateStr + ".xlsx";
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

	public Sheet getIpvSheet() {
		return ipvSheet;
	}

	public void setIpvSheet(Sheet ipvSheet) {
		this.ipvSheet = ipvSheet;
	}
}
