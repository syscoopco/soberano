//taken and adapted from 
//https://github.com/402d/RawbtAPI/

package co.syscoop.soberano.printjobs.rawbt;

public class AttributesBarcode {
    String type;

    String hri = Constant.HRI_NONE;
    int font =  Constant.FONT_A;
    int height = 162;
    int width = 3;

    String alignment = Constant.ALIGNMENT_LEFT;

    public AttributesBarcode() {
    }

    // =========== get & set ========================


    public String getType() {
        return type;
    }

    public String getHri() {
        return hri;
    }

    public int getFont() {
        return font;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getAlignment() {
        return alignment;
    }

    public AttributesBarcode setType(String type) {
        this.type = type;
        return this;
    }

    public AttributesBarcode setHri(String hri) {
        this.hri = hri;
        return this;
    }

    public AttributesBarcode setFont(int font) {
        this.font = font;
        return this;
    }

    public AttributesBarcode setHeight(int height) {
        this.height = height;
        return this;
    }

    public AttributesBarcode setWidth(int width) {
        this.width = width;
        return this;
    }

    public AttributesBarcode setAlignment(String alignment) {
        this.alignment = alignment;
        return this;
    }


    public AttributesBarcode build(){
        return new AttributesBarcode()
                .setType(this.type)
                .setHri(this.hri)
                .setFont(this.font)
                .setHeight(this.height)
                .setWidth(this.width)
                .setAlignment(this.alignment);
    }

    

}