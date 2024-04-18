//taken and adapted from 
//https://github.com/402d/RawbtAPI/

package co.syscoop.soberano.printjobs.rawbt;

public class AttributesImage {
    int graphicFilter = Constant.DITHERING_BW;
    boolean rotateImage = false;
    boolean inverseColor = false;
    private int scale = 16;
    private boolean doScale = true;
    private String alignment = Constant.ALIGNMENT_LEFT;



    public AttributesImage() {
    }

    public AttributesImage(int graphicFilter) {
        this.graphicFilter = graphicFilter;
    }

    
    
    // =========== get & set ========================
    public int getGraphicFilter() {
        return graphicFilter;
    }
    public boolean isRotateImage() {
        return rotateImage;
    }
    public boolean isInverseColor() {
        return inverseColor;
    }
    public int getScale() {
        return scale;
    }
    public String getAlignment() {
        return alignment;
    }

    public boolean isDoScale() {
        return doScale;
    }

    public AttributesImage setGraphicFilter(int graphicFilter) {
        this.graphicFilter = graphicFilter;
        return this;
    }

    public AttributesImage setRotateImage(boolean rotateImage) {
        this.rotateImage = rotateImage;
        return this;
    }

    public AttributesImage setInverseColor(boolean inverseColor) {
        this.inverseColor = inverseColor;
        return this;
    }

    public AttributesImage setScale(int scale) {
        this.scale = scale;
        return this;
    }

    public AttributesImage setAlignment(String alignment) {
        this.alignment = alignment;
        return this;
    }

    public AttributesImage setDoScale(boolean doScale) {
        this.doScale = doScale;
        return this;
    }

    

}
