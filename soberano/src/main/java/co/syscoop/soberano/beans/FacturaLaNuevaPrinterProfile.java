package co.syscoop.soberano.beans;

import java.io.IOException;
import java.util.HashMap;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDMMType1Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import co.syscoop.soberano.printjobs.Printer;

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
        
        //invoice number
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H1_FONT_TYPE, INVOICE_H1_FONT_SIZE);       
        contentStream.showText("NO. FACTURA:");
	}
	
	@Override
	public void createPDFFile(Object objectToPrint, String fileToPrintFullPath) throws IOException {
		
		PDDocument document = new PDDocument();		
		PDRectangle mediaBox = new PDRectangle(612, 792);
		
		PDPage page = new PDPage(mediaBox);
		document.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(document,page);
		contentStream.setLeading(LEADING);
		contentStream.beginText();

		insertInvoiceHeader(contentStream);
                
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
        contentStream.showText("FECHA DE EMISIÓN:");
                
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
        contentStream.showText("NOMBRE:");
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H2_FONT_TYPE, INVOICE_H2_FONT_SIZE); 
        contentStream.showText("DIRECCIÓN:");
                
        //blank lines
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.showText("");
                
        //items
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(INVOICE_H1_FONT_TYPE, INVOICE_H1_FONT_SIZE); 
        contentStream.showText("ARTÍCULOS:");
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(ITEMS_FONT_TYPE, ITEMS_FONT_SIZE); 
        contentStream.showText("CÓDIGO   NO     DESCRIPCIÓN                         UM   CANTIDAD   PRECIO       IMPORTE");
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(ITEMS_FONT_TYPE, ITEMS_FONT_SIZE); 
        contentStream.showText("________________________________________________________________________________________");
        
        float freeSpace = 750 - 22 * LEADING - 42;
        
        Integer pageCount = 1;
        	
        for (int i = 1; i < 55; i++) {
        	contentStream.newLineAtOffset(0, -LEADING);
            contentStream.setFont(ITEMS_FONT_TYPE, ITEMS_FONT_SIZE); 
            contentStream.showText("188888   11     descripción de artículo de lavand   U    12345678   111.11       1111.11");
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
        
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.setFont(ITEMS_FONT_TYPE, ITEMS_FONT_SIZE); 
        contentStream.showText("                                                                         TOTAL:");
        
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
		Printer.print(fileToPrintFullPath, printerNameParam, jobName);
	}
}
