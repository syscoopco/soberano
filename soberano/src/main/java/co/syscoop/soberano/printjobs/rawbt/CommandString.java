//taken and adapted from 
//https://github.com/402d/RawbtAPI/

package co.syscoop.soberano.printjobs.rawbt;

public class CommandString implements RawbtCommand {
    final public static String TAG = "print"; // for GSON
    String command = TAG;

    String text = "";
    AttributesString attributesString = null;

    public CommandString() {
    }

    public CommandString(String text, AttributesString attr) {
        this.text = text;
        this.attributesString = attr;
    }


    // ============ get & set ==================
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AttributesString getAttributesString() {
        return attributesString;
    }

    public void setAttributesString(AttributesString attributesString) {
        this.attributesString = attributesString;
    }

}