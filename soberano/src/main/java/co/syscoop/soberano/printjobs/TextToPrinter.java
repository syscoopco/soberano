package co.syscoop.soberano.printjobs;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class TextToPrinter {

    /**
     * 
     * DeepSeek-generated code
     * 
     * Renders plain text into a monochrome image with given width, font, margins.
     * Height is calculated automatically based on content.
     *
     * @param text         plain text to render (may contain newlines)
     * @param widthPixels  total paper width in pixels (e.g., 384 for 58mm)
     * @param leftMargin   left margin in pixels
     * @param rightMargin  right margin in pixels
     * @param topMargin    top margin in pixels
     * @param bottomMargin bottom margin in pixels
     * @param fontName     font family name (e.g., "Courier New")
     * @param fontSize     font size in points
     * @param bold         true for bold, false for normal
     * @return monochrome image ready to be converted to ESC/POS
     */
	public static BufferedImage TextToImage(
	        String text,
	        int widthPixels,
	        int leftMargin,
	        int rightMargin,
	        int topMargin,
	        int bottomMargin,
	        String fontName,
	        int fontSize,
	        boolean bold) {

	    int usableWidth = widthPixels - leftMargin - rightMargin;
	    if (usableWidth <= 0) {
	        throw new IllegalArgumentException("Margins exceed paper width");
	    }
	    
	    // Convert points to pixels at target DPI
	    // 1 point = 1/72 inch, so pixels = points * DPI / 72
	    Integer targetDpi = 203;
	    double scale = targetDpi / 72.0;
	    int fontSizePixels = (int) Math.round(fontSize * scale);

	    // Temporary image to get font metrics
	    BufferedImage dummy = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_BINARY);
	    Graphics2D g2d = dummy.createGraphics();
	    Font font = new Font(fontName, bold ? Font.BOLD : Font.PLAIN, fontSizePixels);
	    g2d.setFont(font);
	    FontRenderContext frc = g2d.getFontRenderContext();

	    int lineHeight = (int) Math.ceil(font.getLineMetrics("Test", frc).getHeight());

	    // Split into original lines, preserving empty lines
	    String[] originalLines = text.split("\\R", -1);
	    List<String> lines = new ArrayList<>();

	    for (String line : originalLines) {
	        lines.add(line); // keep exactly as is
	    }

	    // Calculate total height
	    int totalHeight = topMargin + (lines.size() * lineHeight) + bottomMargin;

	    // Create image
	    BufferedImage image = new BufferedImage(widthPixels, totalHeight, BufferedImage.TYPE_BYTE_BINARY);
	    Graphics2D g = image.createGraphics();
	    g.setColor(Color.WHITE);
	    g.fillRect(0, 0, widthPixels, totalHeight);
	    g.setColor(Color.BLACK);
	    g.setFont(font);

	    int y = topMargin + lineHeight;
	    for (String line : lines) {
	        g.drawString(line, leftMargin, y);
	        y += lineHeight;
	    }

	    g.dispose();
	    g2d.dispose();
	    return image;
	}
	
	public static BufferedImage PDFToImage(File fileToPrintFullPath, int pageIndex, int dpi) throws Exception, IOException {
        
		try (PDDocument document = PDDocument.load(fileToPrintFullPath)) {
            PDFRenderer renderer = new PDFRenderer(document);
            
            // PDFBox default is 72 DPI, so scale = desiredDPI / 72
            float scale = dpi / 72f;
            return renderer.renderImage(pageIndex, scale);
        }
    }
    
    public static void saveImageToFile(BufferedImage image, String filePath) throws IOException {
       
    	// Get the first available PNG writer
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
        if (!writers.hasNext()) {
            throw new IOException("No PNG writer available");
        }
        
        ImageWriter writer = writers.next();
        File outputFile = new File(filePath);
        
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
            writer.setOutput(ios);
            writer.write(image);
        } finally {
            writer.dispose();
        }
    }
}