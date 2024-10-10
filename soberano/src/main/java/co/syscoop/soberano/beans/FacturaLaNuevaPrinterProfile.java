package co.syscoop.soberano.beans;

import java.io.IOException;
import java.util.HashMap;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import co.syscoop.soberano.printjobs.Printer;

public class FacturaLaNuevaPrinterProfile implements IPDFDocumentToPrint {
	
	@Override
	public void setParameters(HashMap<String, Object> parameters) {		
	}

	@Override
	public void createPDFFile(Object objectToPrint, String fileToPrintFullPath) throws IOException {
		
		PDDocument document = new PDDocument();		
		PDRectangle mediaBox = new PDRectangle(612, 792);
		
		PDPage page = new PDPage(mediaBox);
		document.addPage(page);
		PDPageContentStream content = new PDPageContentStream(document,page);

		//generate data for first page

		content.close();

		//if number of results exceeds what can fit on the first page
		page = new PDPage(mediaBox);
		document.addPage(page);
		content = new PDPageContentStream(document,page);

		//generate data for second page

		content.close();
		
		document.save(fileToPrintFullPath);
		document.close();
		
		
		/*
		
		
		
		InputStream in = IOUtils.toInputStream(fileContent, "ISO-8859-1");
		Reader reader = new InputStreamReader(in, "ISO-8859-1");
		TextToPDF textToPDF = new TextToPDF();
		textToPDF.setFont(PDType1Font.COURIER_BOLD);
		textToPDF.setFontSize(fontSize);
		textToPDF.setLandscape(false);
		
		PDRectangle mediaBox = new PDRectangle(printerProfile.getPageWidth(), printerProfile.getPageHeight());
		PDDocument document = textToPDF.createPDFFromText(reader, mediaBox, printerProfile.getMargin());
		
		Integer numberOfPages = document.getNumberOfPages(); 
		if (numberOfPages > 1) {
			in = IOUtils.toInputStream(fileContent, "ISO-8859-1");
			reader = new InputStreamReader(in, "ISO-8859-1");
			mediaBox = new PDRectangle(printerProfile.getPageWidth(), printerProfile.getPageHeight() * numberOfPages);
			document = textToPDF.createPDFFromText(reader, mediaBox, printerProfile.getMargin());
		}
		
		PDPage page = new PDPage();
		
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		contentStream.close();
		
		document.save(fileToPrintFullPath);
		document.close();
		*/
	}
	
	@Override
	public void printPDFFile(String fileToPrintFullPath, String printerNameParam, String jobName) throws Exception {
		Printer.print(fileToPrintFullPath, printerNameParam, jobName);
	}
}
