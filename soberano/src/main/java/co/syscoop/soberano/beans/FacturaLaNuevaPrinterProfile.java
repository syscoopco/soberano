package co.syscoop.soberano.beans;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDMMType1Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import co.syscoop.soberano.printjobs.Printer;
import co.syscoop.soberano.util.rowdata.InvoiceDataRowData;
import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.SoberanoException;

public class FacturaLaNuevaPrinterProfile implements IPDFDocumentToPrint {
	
	private final static PDType1Font BUSINESS_NAME_FONT_TYPE = PDType1Font.TIMES_BOLD;
	private final static float BUSINESS_NAME_FONT_SIZE = 15;
	
	private final static PDType1Font BUSINESS_SLOGAN_FONT_TYPE = PDMMType1Font.HELVETICA_BOLD;
	private final static float BUSINESS_SLOGAN_FONT_SIZE = 8.5f;
	
	private final static PDType1Font BUSINESS_ADDRESS_FONT_TYPE = PDType1Font.TIMES_BOLD;
	private final static float BUSINESS_ADDRESS_FONT_SIZE = 10;
	
	private final static PDType1Font BUSINESS_CONTACT_DATA_FONT_TYPE = PDType1Font.COURIER;
	private final static float BUSINESS_CONTACT_DATA_FONT_SIZE = 10;
	
	private final static PDType1Font INVOICE_H1_FONT_TYPE = PDType1Font.HELVETICA_BOLD;
	private final static float INVOICE_H1_FONT_SIZE = 10;	
	
	private final static PDType1Font INVOICE_H2_FONT_TYPE = PDType1Font.HELVETICA_BOLD;
	private final static float INVOICE_H2_FONT_SIZE = 8;
	
	private final static PDType1Font ITEMS_FONT_TYPE = PDType1Font.COURIER;
	private final static float ITEMS_FONT_SIZE = 10;
	
    private final static float LEADING = 1.5f * 12;
	
	@Override
	public void setParameters(HashMap<String, Object> parameters) {		
	}
	
	private void insertInvoiceHeader(PDPageContentStream contentStream) throws IOException {
		
		//business name
		contentStream.newLineAtOffset(235, 750);
		contentStream.setFont(BUSINESS_NAME_FONT_TYPE, BUSINESS_NAME_FONT_SIZE);        
        contentStream.showText("Lavandería La Nueva");
                                
        //business slogan
        contentStream.newLineAtOffset(-64, -LEADING);
        contentStream.setFont(BUSINESS_SLOGAN_FONT_TYPE, BUSINESS_SLOGAN_FONT_SIZE);       
        contentStream.showText("PRODUCTORA Y COMERCIALIZADORA DE PRODUCTOS DE ASEO");
                
        //business address
        contentStream.newLineAtOffset(53.5f, -LEADING);
        contentStream.setFont(BUSINESS_ADDRESS_FONT_TYPE, BUSINESS_ADDRESS_FONT_SIZE);       
        contentStream.showText("AVE. 51, #5015, % 60 y 74, Marianao");
                
        //business contact data
        contentStream.newLineAtOffset(-180, -LEADING);
        contentStream.setFont(BUSINESS_CONTACT_DATA_FONT_TYPE, BUSINESS_CONTACT_DATA_FONT_SIZE);       
        contentStream.showText("Email: lavanderia1lanueva@gmail.com, Tel: +53 5 2896736 - +53 7 2022118 - +53 5 1963531");
                
        //blank lines
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.showText("");
	}
	
	@Override
	public void createPDFFile(Object objectToPrint, String fileToPrintFullPath) throws IOException, SQLException, SoberanoException {
		
		PDDocument document = new PDDocument();		
		PDRectangle mediaBox = new PDRectangle(612, 792);
		
		PDPage page = new PDPage(mediaBox);
		document.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(document,page);
		contentStream.setLeading(LEADING);
		contentStream.beginText();

		insertInvoiceHeader(contentStream);
		
		List<Object> invoiceData = null;
		try {
			invoiceData = ((Order) objectToPrint).getInvoiceData();		
		}
		catch(Exception ex) {
			throw new NotEnoughRightsException();
		}		
		
		//invoice number
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H1_FONT_TYPE, INVOICE_H1_FONT_SIZE);       
        contentStream.showText("NO. FACTURA: " + (invoiceData == null || invoiceData.size() == 0 ? "" : ((InvoiceDataRowData) invoiceData.get(0)).getOrderId()));
                
        //invoice type
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H1_FONT_TYPE, INVOICE_H1_FONT_SIZE);       
        contentStream.showText("OFERTA: _____   FACTURA: _____   CONCILIACIÓN: _____   FACTURA EN CONSIGNACIÓN: _____");
                
        //blank lines
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.showText("");
                
        //date
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H1_FONT_TYPE, INVOICE_H1_FONT_SIZE); 
        contentStream.showText("FECHA DE EMISIÓN: " + (invoiceData == null || invoiceData.size() == 0 ? "" : ((InvoiceDataRowData) invoiceData.get(0)).getInvoiceDate()));
                
        //blank lines
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.showText("");
               
        //provider data
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H1_FONT_TYPE, INVOICE_H1_FONT_SIZE); 
        contentStream.showText("DATOS DEL PROVEEDOR:");
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H2_FONT_TYPE, INVOICE_H2_FONT_SIZE); 
        contentStream.showText("CÓDIGO: 17822   CUENTA: 0598770018319510   NIT: 79060617822");
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H2_FONT_TYPE, INVOICE_H2_FONT_SIZE); 
        contentStream.showText("FORMA DE PAGO: TRANSFERENCIA _____   CHEQUE _____   EFECTIVO _____");
                
        //blank lines
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.showText("");
                
        //customer data
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H1_FONT_TYPE, INVOICE_H1_FONT_SIZE); 
        contentStream.showText("DATOS DEL CLIENTE:                                         NO: __________");
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H2_FONT_TYPE, INVOICE_H2_FONT_SIZE); 
        contentStream.showText("EMPRESA _____   TCP _____   MIPYME _____   CNA _____   ONG _____");
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H2_FONT_TYPE, INVOICE_H2_FONT_SIZE); 
        contentStream.showText("CÓDIGO:");
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H2_FONT_TYPE, INVOICE_H2_FONT_SIZE); 
        contentStream.showText("NOMBRE: " + (invoiceData == null || invoiceData.size() == 0 ? "" : ((InvoiceDataRowData) invoiceData.get(0)).getCustomerName()));
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H2_FONT_TYPE, INVOICE_H2_FONT_SIZE); 
        String contactData = "CONTACTO: " + (invoiceData == null || invoiceData.size() == 0 ? "" : ((InvoiceDataRowData) invoiceData.get(0)).getContactData());
        contentStream.showText(contactData.replace("\r", " "));
                
        //blank lines
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.showText("");
                
        //items
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H1_FONT_TYPE, INVOICE_H1_FONT_SIZE); 
        contentStream.showText("ARTÍCULOS:");
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(ITEMS_FONT_TYPE, ITEMS_FONT_SIZE); 
        contentStream.showText(StringUtils.leftPad("CÓDIGO", 7, " ") + "  "
        						+ StringUtils.leftPad("NO", 4, " ")  + "  "
        						+ StringUtils.rightPad("DESCRIPCIÓN", 33, " ") + "  "
        						+ StringUtils.leftPad("UM", 3, " ") + "  "
        						+ StringUtils.leftPad("CANTIDAD", 8, " ") + "  "
        						+ StringUtils.leftPad("PRECIO", 9, " ") + "  " 
        						+ StringUtils.leftPad("IMPORTE", 10, " "));
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(ITEMS_FONT_TYPE, ITEMS_FONT_SIZE); 
        contentStream.showText("________________________________________________________________________________________");
        
        float freeSpace = 750 - 22 * LEADING - 42;        
        Integer pageCount = 1;        
        BigDecimal amountToPay = new BigDecimal(0);
        if (!(invoiceData == null || invoiceData.size() == 0)) {
        	
        	Integer i = 1;
        	for (Object invoiceDataElement : invoiceData) {
        		
        		InvoiceDataRowData invoiceDataRowData = (InvoiceDataRowData) invoiceDataElement;
        		
        		contentStream.newLineAtOffset(0, -LEADING);
                contentStream.setFont(ITEMS_FONT_TYPE, ITEMS_FONT_SIZE); 
                contentStream.showText(StringUtils.leftPad(invoiceDataRowData.getInventoryItemCode(), 7, " ") + "  "
                						+ StringUtils.leftPad(i.toString(), 4, " ") + "  "
                						+ StringUtils.rightPad(invoiceDataRowData.getInventoryItemName(), 33, " ") + "  "
                						+ StringUtils.leftPad(invoiceDataRowData.getUnit(), 3, " ") + "  "
                						+ StringUtils.leftPad(invoiceDataRowData.getQuantity().toPlainString(), 8, " ") + "  "
                						+ StringUtils.leftPad(invoiceDataRowData.getItemPrice().toPlainString(), 9, " ") + "  "
                						+ StringUtils.leftPad(invoiceDataRowData.getItemAmount().toPlainString(), 10, " "));
                i++;
                amountToPay = amountToPay.add(invoiceDataRowData.getItemAmount());
                freeSpace = freeSpace - LEADING;
                
                if (freeSpace < 151) {
                	
                	//blank lines
                    contentStream.newLineAtOffset(0, -LEADING);
                    contentStream.showText("");
                    
                    //page number
                    contentStream.newLineAtOffset(0, -LEADING);
                    contentStream.setFont(INVOICE_H1_FONT_TYPE, INVOICE_H1_FONT_SIZE); 
                    contentStream.showText("Página " + pageCount.toString());
                	
                	mediaBox = new PDRectangle(612, 792);        		
            		page = new PDPage(mediaBox);
            		document.addPage(page);
            		contentStream.close();
            		contentStream = new PDPageContentStream(document,page);
            		contentStream.setLeading(LEADING);
            		contentStream.beginText();
            		insertInvoiceHeader(contentStream);
            		freeSpace = 750 - 42;
            		pageCount++;
                }
            }
        }
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H1_FONT_TYPE, INVOICE_H1_FONT_SIZE); 
        contentStream.showText("DESCUENTO: " 
        						+  (invoiceData == null || invoiceData.size() == 0 ? "" : ((InvoiceDataRowData) invoiceData.get(0)).getOrderDiscount().toString()) 
        						+ "%                                                                                                              A PAGAR: "
        						+ amountToPay.toPlainString());
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(ITEMS_FONT_TYPE, ITEMS_FONT_SIZE); 
        contentStream.showText("________________________________________________________________________________________");
                
        //blank lines
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.showText("");
                
        //payee
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H2_FONT_TYPE, INVOICE_H2_FONT_SIZE); 
        contentStream.showText("PÁGUESE A: TCP WILBER PÉREZ PEÑA. CUENTA: 0598770018319510");
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H2_FONT_TYPE, INVOICE_H2_FONT_SIZE); 
        contentStream.showText("CUENTA: 0598770018319510. SUCURSAL: BANCO METROPOLITANO AVE.49, 12002, % 120 y 122, MARIANAO");
                
        //blank lines
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.showText("");
                
        //footer
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H1_FONT_TYPE, INVOICE_H1_FONT_SIZE); 
        contentStream.showText("REALIZADA POR:                              TRANSPORTADO POR:                             RECIBIDA POR:");
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H1_FONT_TYPE, INVOICE_H1_FONT_SIZE); 
        contentStream.showText("WILBER PEÑA PÉREZ:                      ____________________                            ____________________");
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H1_FONT_TYPE, INVOICE_H1_FONT_SIZE); 
        contentStream.showText("CARGO: GERENTE GENERAL          CARGO: ____________                            CARGO: ____________");
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H1_FONT_TYPE, INVOICE_H1_FONT_SIZE); 
        contentStream.showText("FIRMA: _____________                      FIRMA: _____________                            FIRMA: _____________");
        
        //blank lines
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.showText("");
        
        //page number
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H1_FONT_TYPE, INVOICE_H1_FONT_SIZE); 
        contentStream.showText("Página " + pageCount.toString());
        
        contentStream.endText();
        contentStream.close();
		document.save(fileToPrintFullPath);
		document.close();
	}
	
	@Override
	public void printPDFFile(String fileToPrintFullPath, String printerNameParam, String jobName) throws Exception {
		Printer.print(fileToPrintFullPath, printerNameParam, jobName, false);
	}
}
