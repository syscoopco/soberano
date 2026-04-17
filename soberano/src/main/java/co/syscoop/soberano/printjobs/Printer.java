package co.syscoop.soberano.printjobs;

import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.print.attribute.*;
import javax.print.attribute.standard.JobName;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.zkoss.zul.Messagebox;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.output.PrinterOutputStream;

import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.WSocketClient;
import co.syscoop.soberano.vocabulary.Labels;
import co.syscoop.soberano.vocabulary.Translator;
import co.syscoop.soberano.beans.IDocumentToPrint;
import co.syscoop.soberano.domain.tracked.PrinterProfile;
import co.syscoop.soberano.domain.tracked.TrackedObject;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.printjobs.rawbt.AttributesString;
import co.syscoop.soberano.printjobs.rawbt.Constant;
import co.syscoop.soberano.printjobs.rawbt.RawbtPrintJob;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class Printer {
	
	private PrinterProfile printerProfile = null;
	
	public Printer(PrinterProfile printerProfile) {
		this.setPrinterProfile(printerProfile);  
	}
	
	public Printer() {};
	
	private void printRawToUsb(String textToPrint, String printerName) throws Exception {
		
	    // Prepare the byte array with all necessary commands
	    byte[] data = buildPrintData(textToPrint);
	    
	    // Write directly to the USB device
	    try (OutputStream out = new FileOutputStream(printerName)) {
	        out.write(data);
	        out.flush(); // Ensure all data is sent
	        // The try-with-resources will close the stream, which also flushes
	    }
	    
	    // Small delay to let the printer finish processing
	    Thread.sleep(200);
	}

	private byte[] buildPrintData(String textToPrint) throws IOException {
		
	    String fixedText = textToPrint.replace('\r', '\n');
	    // Use CP850 for Spanish characters (adjust as needed)
	    byte[] textBytes = fixedText.getBytes(Charset.forName("CP850"));
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    
	    // Reset printer
	    baos.write(27); baos.write(64); // ESC @
	    
	    // Select code page PC850
	    baos.write(27); baos.write(116); baos.write(2); // ESC t 2
	    
	    // Select font
	    //baos.write(27); baos.write(33); baos.write(0);  //Font A, normal
	    //baos.write(27); baos.write(33); baos.write(1);  //Font B, normal
	    //baos.write(27); baos.write(33); baos.write(32); //Font A, double‑width
	    //baos.write(27); baos.write(33); baos.write(16); //Font A, double‑height
	    //baos.write(27); baos.write(33); baos.write(49); //Font B, double‑width + double‑height
	    
	    // Feed 4 lines to skip top margin
	    // for (int i = 0; i < 4; i++) baos.write(10);
	    
	    // Receipt content
	    baos.write(textBytes);
	    
	    // Feed 5 lines before cut
	    for (int i = 0; i < 2; i++) baos.write(10);
	    
	    // Form feed (eject paper)
	    baos.write(0x0C);
	    
	    // Full cut
	    baos.write(29); baos.write(86); baos.write(0); // GS V 0
	    
	    return baos.toByteArray();
	}
	
	private void printRAW(String textToPrint, String printJobName) throws Exception {
		
		try {
				printRawToUsb(textToPrint, printerProfile.getPrinterName());
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
			if (c.connectBlocking(60, TimeUnit.SECONDS)) {
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
		        
		        //cut
		        printJob.println(new String(new char[] {0x1D, 0x56, 0x41, 0x10}));
		        
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
	
	private static void printPDF(PrintService[] pss, String fileToPrintFullPath, String printerNameParam, String jobName, Boolean openCashDrawer) throws SoberanoException {
		
		try {		
			PrintService[] printServices = null;
			if (pss == null) {
				printServices = PrintServiceLookup.lookupPrintServices(null, null);
			}
			else {
				printServices = pss;
			}			
			for (PrintService printService : printServices) {					
				if (printService.getName().replace("\\", "").trim().toLowerCase().equals(printerNameParam.replace("\\", "").trim().toLowerCase())) {
					PDDocument document = PDDocument.load(new File(fileToPrintFullPath));
			        PrinterJob job = PrinterJob.getPrinterJob();
			        job.setJobName(jobName);
			        job.setPageable(new PDFPageable(document));
			        job.setPrintService(printService);
			        job.print();
			        document.close();
			        
//			        PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService);
//			        EscPos escPos = new EscPos(printerOutputStream);
//			        escPos.feed(1).cut(EscPos.CutMode.FULL);
//			        escPos.close();
			        
			        if (openCashDrawer) {
			        	openCashDrawer(printService);
			        }
			        
	            	break;
	            }
			}
		}
		catch (Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("error.print.ErrorWhilePrintingDocument") + " PRINT JOB: " + jobName + ". DETAILS: " + ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
	    }
	}
	
	private static void printImage(PrintService[] pss, byte[] data, String printerNameParam, String jobName, Boolean openCashDrawer) throws SoberanoException {
        
		try {
			PrintService[] printServices = null;
			if (pss == null) {
				printServices = PrintServiceLookup.lookupPrintServices(null, null);
			}
			else {
				printServices = pss;
			}
			for (PrintService printService : printServices) {					
				if (printService.getName().replace("\\", "").trim().toLowerCase().equals(printerNameParam.replace("\\", "").trim().toLowerCase())) {
					
					//add job name
					PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
					aset.add(new JobName(jobName, null));
					
					DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
			        Doc doc = new SimpleDoc(data, flavor, null);
			        printService.createPrintJob().print(doc, aset);				
			        
//			        PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService);
//			        EscPos escPos = new EscPos(printerOutputStream);
//			        escPos.feed(1).cut(EscPos.CutMode.FULL);
//			        escPos.close();
	            			        
			        if (openCashDrawer) {
			        	openCashDrawer(printService);
			        }
			        
			        break;
	            }
			}
		}
		catch (Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("error.print.ErrorWhilePrintingDocument") + " PRINT JOB: " + jobName + ". DETAILS: " + ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
	    }
    }
	
	private static void openCashDrawer(PrintService printService) throws IOException {	
		
		PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService);
		EscPos escPos = new EscPos(printerOutputStream);

		// Send the raw ESC/POS command to open the cash drawer (drawer 1)
		byte[] command = {0x1B, 0x70, 0, 50, (byte) 250};
		escPos.write(command, 0, command.length);   // offset 0, length 5

		// Close to flush and release resources
		escPos.close();
	}
	
	public static void openCashDrawer(PrintService[] pss, String printerNameParam) throws IOException {
		
		PrintService[] printServices = null;
		if (pss == null) {
			printServices = PrintServiceLookup.lookupPrintServices(null, null);
		}
		else {
			printServices = pss;
		}			
		for (PrintService printService : printServices) {					
			if (printService.getName().replace("\\", "").trim().toLowerCase().equals(printerNameParam.replace("\\", "").trim().toLowerCase())) {
				openCashDrawer(printService);
            }
		}
	}
	
		
	public void print(String textToPrint, String fileToPrintFullPath, String printerNameParam, String jobName, Boolean openCashDrawer, PrintMethod printMethod) throws UnsupportedEncodingException, IOException, Exception {
		
		try {
			if (!(printerNameParam.indexOf("ws://") == -1)) {
				printThroughSocket(textToPrint, printerNameParam);
			}
			else {				
				if (printMethod == PrintMethod.PDF) {
					printPDF(PrintServiceLookup.lookupPrintServices(null, null), fileToPrintFullPath, printerNameParam, jobName, openCashDrawer);  
				}
				else if (printMethod == PrintMethod.IMAGE) {
					printImage(PrintServiceLookup.lookupPrintServices(null, null), Files.readAllBytes(Paths.get(fileToPrintFullPath + ".png")), printerNameParam, jobName, openCashDrawer);
				}
				//RAW
				else {
					printRAW(textToPrint, jobName);
				}
			}
		}
		catch (Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("error.print.ErrorWhilePrintingDocument") + " PRINT JOB: " + jobName + ". DETAILS: " + ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
	    }
	}
	
	private void createPDFFile(String fileContent, String fileToPrintFullPath) throws UnsupportedEncodingException, IOException {
		
		InputStream in = IOUtils.toInputStream(fileContent, "ISO-8859-1");
		Reader reader = new InputStreamReader(in, "ISO-8859-1");
		TextToPDF textToPDF = new TextToPDF();
		textToPDF.setFont(PDType1Font.COURIER_BOLD);
		textToPDF.setFontSize(printerProfile.getFontSize());
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
	
	public void createFile(String fileContent, String fileToPrintFullPath) throws Exception {
		
		if(printerProfile.getPrintMethod() == PrintMethod.PDF) {
			createPDFFile(fileContent, fileToPrintFullPath);
		}
		else if (printerProfile.getPrintMethod() == PrintMethod.IMAGE) {
			createPDFFile(fileContent, fileToPrintFullPath);
			BufferedImage image = TextToPrinter.PDFToImage(new File(fileToPrintFullPath), 0, 203);  
									/*TextToPrinter.TextToImage(fileContent,
													            printerProfile.getPageWidth(),
													            printerProfile.getMargin(),
													            printerProfile.getMargin(),
													            printerProfile.getMargin(),
													            printerProfile.getMargin(),
													            printerProfile.getFontName(),
													            printerProfile.getFontSize(),
													            true);*/
			try {				
				TextToPrinter.saveImageToFile(image, fileToPrintFullPath + ".png");
			}
			finally {
				image.flush();
			}			
		}
		//PrintMethod.ESCPOS_RAW
		else {
			
		}
	}
	
	public void createFile3LF(String fileContent, String fileToPrintFullPath) throws Exception {
		
		if (printerProfile.getPrintMethod() == PrintMethod.PDF) {
			createPDFFile(fileContent + "--\n--\n--\n", fileToPrintFullPath);
		}
		else if (printerProfile.getPrintMethod() == PrintMethod.IMAGE) {
			createPDFFile(fileContent + "--\n--\n--\n", fileToPrintFullPath);
			BufferedImage image = TextToPrinter.PDFToImage(new File(fileToPrintFullPath), 0, 203);
									/*TextToPrinter.TextToImage(fileContent + "--\n--\n--\n",
													            printerProfile.getPageWidth(),
													            printerProfile.getMargin(),
													            printerProfile.getMargin(),
													            printerProfile.getMargin(),
													            printerProfile.getMargin(),
													            printerProfile.getFontName(),
													            printerProfile.getFontSize(),
													            true);*/
			try {
				TextToPrinter.saveImageToFile(image, fileToPrintFullPath + ".png");
			}
			finally {
				image.flush();
			}
		}
		//PrintMethod.ESCPOS_RAW
		else {
			
		}
	}
	
	public void printFile(String textToPrint, String fileFullPath, String jobName, Boolean openCashDrawer) throws UnsupportedEncodingException, IOException, Exception {
		print(textToPrint, fileFullPath, printerProfile.getPrinterName(), jobName, openCashDrawer, printerProfile.getPrintMethod());
	}
	
	public static void print(String textToPrint,
							TrackedObject trackedObject,
							String fileToPrintFullPath,
							Boolean _3LF,
							Boolean openCashDrawer) throws Exception {
		Integer objectId = trackedObject.getId();
		String printJobName = trackedObject.getClass().getSimpleName() + "_" + objectId;
		PrinterProfile printerProfile = new PrinterProfile(trackedObject.getPrinterProfile());
		printerProfile.get();
		Printer printer = new Printer(printerProfile);
		if (_3LF) {
			printer.createFile3LF(textToPrint, fileToPrintFullPath);
		}
		else {
			printer.createFile(textToPrint, fileToPrintFullPath);
		}		
		printer.printFile(textToPrint, fileToPrintFullPath, printJobName, openCashDrawer);
	}
	
	public static void createFile(Printer printer,
								String textToPrint,
								Integer printerProfileId,
								String fileToPrintFullPath,
								Boolean _3LF) throws Exception {
		if (_3LF) {
			printer.createFile3LF(textToPrint, fileToPrintFullPath);
		}
		else {
			printer.createFile(textToPrint, fileToPrintFullPath);
		}	
	}
	
	public static void print(String textToPrint,
							Integer printerProfileId,
							String fileToPrintFullPath,
							String printJobName,
							Boolean _3LF,
							Object objectToPrint,
							Boolean openCashDrawer) throws Exception {
		PrinterProfile printerProfile = new PrinterProfile(printerProfileId);
		printerProfile.get();
		Printer printer = new Printer(printerProfile);
		
		//there is a bean for more printing customization
		IDocumentToPrint pp = null;
		try {
			pp = (IDocumentToPrint) SpringUtility.applicationContext().getBean(printerProfile.getName().toLowerCase());
			pp.createFile(objectToPrint, fileToPrintFullPath);
			pp.printFile(fileToPrintFullPath, printerProfile.getPrinterName(), printJobName);
		}
		catch(NoSuchBeanDefinitionException nsbdex) {			
			createFile(printer, textToPrint, printerProfileId, fileToPrintFullPath, _3LF);
			printer.printFile(textToPrint, fileToPrintFullPath, printJobName, openCashDrawer);
		}
		catch(Exception ex) {
			throw ex;
		}
	}
	
	public static void printReport(TrackedObject trackedObject, 
									String fileToPrintFullPath, 
									String printJobPrefix, 
									Boolean _3LF,
									Boolean translate,
									Boolean minimal,
									Boolean openCashDrawer) throws SoberanoException, SQLException {
		
		PrintableData pd = null;
		if (!minimal) {
			pd = trackedObject.getReportFull();
		}
		else {
			pd = trackedObject.getReportMinimal();
		}
		if (!pd.getTextToPrint().isEmpty()) {			
			try {
				Printer.print(translate ? Translator.translate(pd.getTextToPrint()) : pd.getTextToPrint(), 
							pd.getPrinterProfile(), fileToPrintFullPath, 
							"printJobPrefix" + trackedObject.getId(), _3LF, null, openCashDrawer);
			}
			catch(Exception ex) {
				ExceptionTreatment.logAndShow(ex, 
						Labels.getLabel("message.error.ConfigurePrinterProfile"), 
						Labels.getLabel("messageBoxTitle.Error"),
						Messagebox.ERROR);
			}				
		}
		else {
			throw new NotEnoughRightsException();
		}
	}

	public PrinterProfile getPrinterProfile() {
		return printerProfile;
	}

	public void setPrinterProfile(PrinterProfile printerProfile) {
		this.printerProfile = printerProfile;
	}
}
