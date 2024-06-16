package co.syscoop.soberano.printjobs;

import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPageable;
import org.cups4j.CupsClient;
import org.cups4j.CupsPrinter;
import org.cups4j.PrintJob;
import org.cups4j.PrintJob.Builder;
import org.cups4j.PrintRequestResult;
import org.zkoss.zul.Messagebox;
import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.output.PrinterOutputStream;
import co.syscoop.soberano.util.WSocketClient;
import co.syscoop.soberano.vocabulary.Labels;
import co.syscoop.soberano.domain.tracked.PrinterProfile;
import co.syscoop.soberano.domain.tracked.TrackedObject;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.printjobs.rawbt.AttributesString;
import co.syscoop.soberano.printjobs.rawbt.Constant;
import co.syscoop.soberano.printjobs.rawbt.RawbtPrintJob;

public class Printer {
	
	private PrinterProfile printerProfile = null;
	
	public Printer(PrinterProfile printerProfile) {
		this.setPrinterProfile(printerProfile);  
	}
	
	private CupsPrinter selectedPrinter;
	
	private void printCUPS(String textToPrint, String printJobName) throws Exception {
		PrintRequestResult printRequestResult;
		try { 
	        CupsClient client = new CupsClient(printerProfile.getPrintServer(), 631);
	        List<CupsPrinter> printers = client.getPrinters();
	        if (printers.size() == 0) {
	        	Messagebox.show(Labels.getLabel("message.print.TheresNoPrinterConfigured"), 
	  					Labels.getLabel("messageBoxTitle.Information"), 
						0, 
						Messagebox.EXCLAMATION);
	        }
	        else {
	        	for (CupsPrinter cupsPrinter : printers) {
		            if (cupsPrinter.getName().equals(printerProfile.getPrinterName())) {
		                selectedPrinter = cupsPrinter;
		            }
		        }
	        	Builder builder = new PrintJob.Builder(textToPrint.getBytes());
	        	builder.jobName(printJobName);
	        	HashMap<String, String> map = new HashMap<>();
	        	
	        	map.put("document-format", "application/vnd.cups-raw");
	        	map.put("document-name", printJobName);       	
	        	
	        	PrintJob printJob = new PrintJob.Builder(textToPrint.getBytes()).attributes(map).build();
	        	printRequestResult = selectedPrinter.print(printJob);
	        	if (!printRequestResult.isSuccessfulResult()) {
	        		Messagebox.show(Labels.getLabel("error.print.ErrorWhilePrintingDocument") + printJobName + ". DETAILS: " + printRequestResult.getResultDescription(), 
	      					Labels.getLabel("messageBoxTitle.Error"), 
	    					0, 
	    					Messagebox.ERROR);
	        	}
	        }	        
	    }
		catch (Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("error.print.ErrorWhilePrintingDocument") + " PRINT JOB: " + printJobName + ". DETAILS: " + ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
	    }
	}
	
	private void printThroughSocket(String textToPrint, String printerWS) throws URISyntaxException, Exception {
		
		WSocketClient c = new WSocketClient(new URI(printerWS));
		try {			
			if (c.connectBlocking(1, TimeUnit.SECONDS)) {
				RawbtPrintJob printJob = new RawbtPrintJob();
				
				AttributesString attrStr = new AttributesString();
		        attrStr.setPrinterFont(Constant.FONT_A);
		        attrStr.setFontsCpi(Constant.CPI_NORMAL);
		        attrStr.setAlignment(Constant.ALIGNMENT_LEFT);
		        attrStr.setBold(false);
		        attrStr.setUnderline(false);
		        attrStr.setDoubleHeight(false);
		        attrStr.setDoubleWidth(false);

		        printJob.setDefaultAttrString(attrStr);
		        
		        BufferedReader bufReader = new BufferedReader(new StringReader(textToPrint));
		        String line = null;
		        while( (line = bufReader.readLine()) != null )
		        {
		        	printJob.println(line);					
		        }
		        c.send(printJob.GSON());
			}
			else {
				Messagebox.show("Check the printer's connection.", 
	  					Labels.getLabel("messageBoxTitle.Error"), 
						0, 
						Messagebox.ERROR);
			}
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
					ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
		}
		finally {
			c.close();
		}		
	}
	
	private void print(String textToPrint, String fileToPrintFullPath, String printerNameParam, String jobName) throws UnsupportedEncodingException, IOException, Exception {
		String printJobName = "";
		try {
			if (!(printerNameParam.indexOf("ws://") == -1)) {
				printThroughSocket(textToPrint, printerNameParam);
			}
			else {
				PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
				if (printServices.length == 0) {
					printCUPS(textToPrint, printJobName);
				}
				else {
					for (PrintService printService : printServices) {					
						if (printService.getName().replace("\\", "").trim().toLowerCase().equals(printerNameParam.replace("\\", "").trim().toLowerCase())) {
							PDDocument document = PDDocument.load(new File(fileToPrintFullPath));
					        PrinterJob job = PrinterJob.getPrinterJob();
					        job.setJobName(jobName);
					        job.setPageable(new PDFPageable(document));
					        job.setPrintService(printService);
					        job.print();
					        document.close();
					        
					        PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService);
					        EscPos escPos = new EscPos(printerOutputStream);
					        escPos.feed(1).cut(EscPos.CutMode.FULL);
					        escPos.close();
			            	break;
			            }
					}		        
				}
			}
		}
		catch (Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("error.print.ErrorWhilePrintingDocument") + " PRINT JOB: " + printJobName + ". DETAILS: " + ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
	    }
	}
	
	private void createPDFFile(String fileContent, String fileToPrintFullPath, int fontSize) throws UnsupportedEncodingException, IOException {
		
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
	}
	
	public void createPDFFile(String fileContent, String fileFullPath) throws UnsupportedEncodingException, IOException {
		createPDFFile(fileContent, fileFullPath, printerProfile.getFontSize());
	}
	
	public void createPDFFile3LF(String fileContent, String fileFullPath) throws UnsupportedEncodingException, IOException {
		createPDFFile(fileContent + "--\n--\n--\n", fileFullPath, printerProfile.getFontSize());
	}
	
	public void printPDFFile(String textToPrint, String fileFullPath, String jobName) throws UnsupportedEncodingException, IOException, Exception {
		print(textToPrint, fileFullPath, printerProfile.getPrinterName(), jobName);
	}
	
	public static void print(String textToPrint,
							TrackedObject trackedObject,
							String fileToPrintFullPath,
							Boolean _3LF) throws Exception {
		Integer objectId = trackedObject.getId();
		String printJobName = trackedObject.getClass().getSimpleName() + "_" + objectId;
		PrinterProfile printerProfile = new PrinterProfile(trackedObject.getPrinterProfile());
		printerProfile.get();
		Printer printer = new Printer(printerProfile);
		if (_3LF) {
			printer.createPDFFile3LF(textToPrint, fileToPrintFullPath);
		}
		else {
			printer.createPDFFile(textToPrint, fileToPrintFullPath);
		}		
		printer.printPDFFile(textToPrint, fileToPrintFullPath, printJobName);
	}
	
	public static void print(String textToPrint,
							Integer printerProfileId,
							String fileToPrintFullPath,
							String printJobName,
							Boolean _3LF) throws Exception {
		PrinterProfile printerProfile = new PrinterProfile(printerProfileId);
		printerProfile.get();
		Printer printer = new Printer(printerProfile);
		if (_3LF) {
			printer.createPDFFile3LF(textToPrint, fileToPrintFullPath);
		}
		else {
			printer.createPDFFile(textToPrint, fileToPrintFullPath);
		}		
		printer.printPDFFile(textToPrint, fileToPrintFullPath, printJobName);
	}

	public PrinterProfile getPrinterProfile() {
		return printerProfile;
	}

	public void setPrinterProfile(PrinterProfile printerProfile) {
		this.printerProfile = printerProfile;
	}
}
