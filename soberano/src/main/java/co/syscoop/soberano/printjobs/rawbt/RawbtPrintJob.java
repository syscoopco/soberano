//taken and adapted from 
//https://github.com/402d/RawbtAPI/

package co.syscoop.soberano.printjobs.rawbt;

import com.google.gson.Gson;
import java.util.ArrayList;

public class RawbtPrintJob {
    public static final String ACTION_PRINT_JOB = "rawbt.action.PRINT";
    public static final String EXTRA_JOB = "rawbt.action.extra.JOB_JSON";

    String idJob = null;

    ArrayList<RawbtCommand> commands = new ArrayList<>();

    int copies = 1;

    public final static String TEMPLATE_NONE = "none";
    public final static String TEMPLATE_DEFAULT = "default";
    public final static String TEMPLATE_SIMPLE = "simple";

    String template = TEMPLATE_DEFAULT;

    public final static String PRINTER_CURRENT = "current";
    public final static String PRINTER_VIRTUAL = "virtual";
    public final static String PRINTER_RAW_TRANSFER = "raw_transfer";
    public final static String PRINTER_GALLERY = "save_into_gallery";

    String printer = PRINTER_CURRENT;

    // ------------------
    AttributesString defaultAttrString = null;
    AttributesImage defaultAttrImage = null;
    AttributesPdf defaultAttrPdf = null;
    // -----------------

    boolean premium = false;

    // ============= get & set ==================

    public String getIdJob() {
        return idJob;
    }

    public void setIdJob(String idJob) {
        this.idJob = idJob;
    }

    public void add(RawbtCommand command){
        commands.add(command);
    }

    public ArrayList<RawbtCommand> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<RawbtCommand> commands) {
        this.commands = commands;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate( String template) {
        this.template = template;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter( String printer) {
        this.printer = printer;
    }

    public AttributesString getDefaultAttrString() {
        return defaultAttrString == null? new AttributesString():defaultAttrString;
    }

    public void setDefaultAttrString( AttributesString defaultAttrString) {
        this.defaultAttrString = defaultAttrString;
    }
    public boolean isNullDefaultAttrString(){
        return  defaultAttrString == null;
    }
    public AttributesImage getDefaultAttrImage() {
        return defaultAttrImage == null?new AttributesImage():defaultAttrImage;
    }

    public void setDefaultAttrImage( AttributesImage defaultAttrImage) {
        this.defaultAttrImage = defaultAttrImage;
    }

    public boolean isNullDefaultAttrImage(){
        return defaultAttrImage == null;
    }


    public AttributesPdf getDefaultAttrPdf() {
        return defaultAttrPdf == null?new AttributesPdf():defaultAttrPdf;
    }

    public void setDefaultAttrPdf( AttributesPdf defaultAttrPdf) {
        this.defaultAttrPdf = defaultAttrPdf;
    }
    public boolean isNullDefaultAttrPdf(){
        return defaultAttrPdf == null;
    }

    // ========== строка текста ===========

    public void println( String string){
        commands.add(new CommandString(string,getDefaultAttrString()));
    }

    public void println( String string,  AttributesString attr){
        commands.add(new CommandString(string,attr));
    }

    public void ln(){
        commands.add(new CommandNewLine(1));
    }

    public void ln(int n){
        commands.add(new CommandNewLine(n));
    }

    // ========== rich format ==============

    public void leftRightText(String leftText, String rightText){
        commands.add(new CommandLeftRightText(leftText,rightText,getDefaultAttrString()));
    }

    public void leftRightTextWithFormat(String leftText, String rightText,AttributesString attr){
        commands.add(new CommandLeftRightText(leftText,rightText,attr));
    }

    public void leftRightTextWithBothFormat(String leftText, String rightText,AttributesString attrLeft,AttributesString attrRight){
        commands.add(new CommandLeftRightText(leftText,rightText,0,0,attrLeft,attrRight));
    }


    public void leftIndentRightText(int leftIndent, String leftText, String rightText){
        CommandLeftRightText command = new CommandLeftRightText(leftText,rightText,getDefaultAttrString());
        command.setLeftIndent(leftIndent);
        commands.add(command);
    }

    public void leftIndentRightTextWithFormat(int leftIndent, String leftText, String rightText,AttributesString attr){
        CommandLeftRightText command = new CommandLeftRightText(leftText,rightText,attr);
        command.setLeftIndent(leftIndent);
        commands.add(command);
    }


    public void leftRightIndentText(int rightIndent, String leftText, String rightText){
        CommandLeftRightText command = new CommandLeftRightText(leftText,rightText,getDefaultAttrString());
        command.setRightIndent(rightIndent);
        commands.add(command);
    }


    public void leftRightIndentTextWithFormat(int rightIndent, String leftText, String rightText, AttributesString attr){
        CommandLeftRightText command = new CommandLeftRightText(leftText,rightText,attr);
        command.setRightIndent(rightIndent);
        commands.add(command);
    }

    // ============== prn & bytes =====================
    public void sendBytes( String base64){
        commands.add(new CommandBytesInBase64(base64));
    }

    // ========== barcode & qr ==========

    public void barcode(CommandBarcode commandBarcode){
        commands.add(commandBarcode);
    }

    public void barcode(String data, AttributesBarcode attr){
        commands.add(new CommandBarcode(data,attr));
    }

    public void qrcode(CommandQRcode commandQRcode) { commands.add(commandQRcode); }
    public void qrcode(String data, AttributesQRcode attr){
        commands.add(new CommandQRcode(data,attr));
    }


    // ========= cut ===========

    public void cut(){
        commands.add(new CommandCut());
    }

    // =======================
    public void drawLine(Character ch){
        commands.add( new CommandDrawLine(ch,getDefaultAttrString()));
    }

    public void drawLine(Character ch, AttributesString attr){
        commands.add( new CommandDrawLine(ch,attr));
    }

    // =============================

    public void delimiterImages() {
        add(new CommandDelimiterImages());
    }


    // ==============

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    // ====================
    public String GSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}