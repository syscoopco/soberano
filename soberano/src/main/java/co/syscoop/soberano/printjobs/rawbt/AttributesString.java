//taken and adapted from 
//https://github.com/402d/RawbtAPI/

package co.syscoop.soberano.printjobs.rawbt;

public class AttributesString {

    // type: string

    private int fontsCpi = Constant.CPI_DEFAULT;
    private int internationalChars = 0;

    // if text printed as image
    private int truetypeFontSize = 21;

    // свойства для строки
    private int printerFont = Constant.FONT_DEFAULT ;
    private String alignment = Constant.ALIGNMENT_LEFT;
    private boolean bold = false;
    private boolean doubleHeight = false;
    private boolean doubleWidth = false;
    private boolean underline = false;

    private String lang = "default";

    // ============= constructor ====================

    public AttributesString() {
    }


    // ===================

    public boolean isFontA(){
        return getPrinterFont()==Constant.FONT_A;
    }

    public boolean isFontB(){
        return getPrinterFont()==Constant.FONT_B;
    }

    public boolean isFontC(){
        return getPrinterFont()==Constant.FONT_C;
    }

    // ============ get & set ==========


    public int getPrinterFont() {
        return printerFont;
    }
    public int getFontsCpi() {
        return fontsCpi;
    }
    public int getInternationalChars() {
        return internationalChars;
    }
    public int getTruetypeFontSize() {
        return truetypeFontSize;
    }
    public String getAlignment() {
        return alignment;
    }
    public boolean isBold() {
        return bold;
    }
    public boolean isDoubleHeight() {
        return doubleHeight;
    }
    public boolean isDoubleWidth() {
        return doubleWidth;
    }
    public boolean isUnderline() {
        return underline;
    }
    public String getLang() {
        return lang;
    }

    public AttributesString setPrinterFont(int printerFont) {
        this.printerFont = printerFont;
        return this;
    }

    public AttributesString setFontsCpi(int fontsCpi) {
        this.fontsCpi = fontsCpi;
        return this;
    }

    public AttributesString setInternationalChars(int internationalChars) {
        this.internationalChars = internationalChars;
        return this;
    }

    public AttributesString setTruetypeFontSize(int truetypeFontSize) {
        this.truetypeFontSize = truetypeFontSize;
        return this;
    }

    public AttributesString setAlignment(String alignment) {
        this.alignment = alignment;
        return this;
    }

    public AttributesString setBold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public AttributesString setDoubleHeight(boolean doubleHigh) {
        this.doubleHeight = doubleHigh;
        return this;
    }

    public AttributesString setDoubleWidth(boolean doubleWidth) {
        this.doubleWidth = doubleWidth;
        return this;
    }

    public AttributesString setUnderline(boolean underline) {
        this.underline = underline;
        return this;
    }

    public AttributesString setLang(String lang) {
        this.lang = lang;
        return this;
    }

    public AttributesString build(){
        return new AttributesString()
                .setFontsCpi(fontsCpi)
                .setInternationalChars(internationalChars)
                .setTruetypeFontSize(truetypeFontSize)
                .setPrinterFont(printerFont)
                .setAlignment(alignment)
                .setBold(bold)
                .setDoubleWidth(doubleWidth)
                .setDoubleHeight(doubleHeight)
                .setUnderline(underline)
                .setLang(lang);

    }
}