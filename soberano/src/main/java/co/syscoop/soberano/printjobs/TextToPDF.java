package co.syscoop.soberano.printjobs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class TextToPDF extends org.apache.pdfbox.tools.TextToPDF {

	public TextToPDF() {
		super();
	}
	
	public PDDocument createPDFFromText(Reader text, PDRectangle mediaBox, final int margin) throws IOException
    {
        PDDocument doc = new PDDocument();
        createPDFFromText(doc, text, mediaBox, margin);
        return doc;
    }
	
	@SuppressWarnings({ "deprecation", "resource" })
	public void createPDFFromText(PDDocument doc, Reader text, PDRectangle mediaBox, final int margin) throws IOException
    {
        try
        {
            float height = super.getFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
            if (super.isLandscape())
            {
                mediaBox = new PDRectangle(mediaBox.getHeight(), mediaBox.getWidth());
            }
            
            //calculate font height and increase by 5 percent.
            height = height * super.getFontSize() * 1.05f;
            BufferedReader data = new BufferedReader(text);
            String nextLine = null;
            PDPage page = new PDPage(mediaBox);
            PDPageContentStream contentStream = null;
            float y = -1;
            float maxStringLength = page.getMediaBox().getWidth() - 2 * margin;
            
            // There is a special case of creating a PDF document from an empty string.
            boolean textIsEmpty = true;
            while((nextLine = data.readLine()) != null)
            {
                // The input text is nonEmpty. New pages will be created and added
                // to the PDF document as they are needed, depending on the length of
                // the text.
                textIsEmpty = false;
                String[] lineWords = nextLine.replaceAll("[\\n\\r]+$", "").split(" ");
                int lineIndex = 0;
                while(lineIndex < lineWords.length)
                {
                    StringBuilder nextLineToDraw = new StringBuilder();
                    float lengthIfUsingNextWord = 0;
                    boolean ff = false;
                    do
                    {
                        String word1, word2 = "";
                        int indexFF = lineWords[lineIndex].indexOf('\f');
                        if (indexFF == -1)
                        {
                            word1 = lineWords[lineIndex];
                        }
                        else
                        {
                            ff = true;
                            word1 = lineWords[lineIndex].substring(0, indexFF);
                            if (indexFF < lineWords[lineIndex].length())
                            {
                                word2 = lineWords[lineIndex].substring(indexFF + 1);
                            }
                        }
                        // word1 is the part before ff, word2 after
                        // both can be empty
                        // word1 can also be empty without ff, if a line has many spaces
                        if (word1.length() > 0 || !ff)
                        {
                            nextLineToDraw.append(word1);
                            nextLineToDraw.append(" ");
                        }
                        if (!ff || word2.length() == 0)
                        {
                            lineIndex++;
                        }
                        else
                        {
                            lineWords[lineIndex] = word2;
                        }
                        if (ff)
                        {
                            break;
                        }
                        if(lineIndex < lineWords.length)
                        {
                            // need cut off at \f in next word to avoid IllegalArgumentException
                            String nextWord = lineWords[lineIndex];
                            indexFF = nextWord.indexOf('\f');
                            if (indexFF != -1)
                            {
                                nextWord = nextWord.substring(0, indexFF);
                            }
                            
                            String lineWithNextWord = nextLineToDraw.toString() + " " + nextWord;
                            lengthIfUsingNextWord =
                                (super.getFont().getStringWidth(lineWithNextWord)/1000) * super.getFontSize();
                        }
                    }
                    while(lineIndex < lineWords.length &&
                           lengthIfUsingNextWord < maxStringLength);
                    if(y < margin)
                    {
                        // We have crossed the end-of-page boundary and need to extend the
                        // document by another page.
                        page = new PDPage(mediaBox);
                        doc.addPage(page);
                        if(contentStream != null)
                        {
                            contentStream.endText();
                            contentStream.close();
                        }
                        contentStream = new PDPageContentStream(doc, page);
                        contentStream.setFont(super.getFont(), super.getFontSize());
                        contentStream.beginText();
                        y = page.getMediaBox().getHeight() - margin + height;
                        contentStream.moveTextPositionByAmount(margin, y);
                    }
                    //System.out.println("Drawing string at " + x + "," + y);
                    if(contentStream == null)
                    {
                        throw new IOException("Error:Expected non-null content stream.");
                    }
                    contentStream.moveTextPositionByAmount(0, -height);
                    y -= height;
                    contentStream.drawString(nextLineToDraw.toString());
                    if (ff)
                    {
                        page = new PDPage(mediaBox);
                        doc.addPage(page);
                        contentStream.endText();
                        contentStream.close();
                        contentStream = new PDPageContentStream(doc, page);
                        contentStream.setFont(super.getFont(), super.getFontSize());
                        contentStream.beginText();
                        y = page.getMediaBox().getHeight() - margin + height;
                        contentStream.moveTextPositionByAmount(margin, y);
                    }
                }
            }
            // If the input text was the empty string, then the above while loop will have short-circuited
            // and we will not have added any PDPages to the document.
            // So in order to make the resultant PDF document readable by Adobe Reader etc, we'll add an empty page.
            if (textIsEmpty)
            {
                doc.addPage(page);
            }
            if(contentStream != null)
            {
                contentStream.endText();
                contentStream.close();
            }
        }
        catch(IOException io)
        {
            if(doc != null)
            {
                doc.close();
            }
            throw io;
        }
    }
}
