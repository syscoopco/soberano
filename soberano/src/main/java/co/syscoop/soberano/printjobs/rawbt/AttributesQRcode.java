//taken and adapted from 
//https://github.com/402d/RawbtAPI/

package co.syscoop.soberano.printjobs.rawbt;

public class AttributesQRcode {

    String alignment = Constant.ALIGNMENT_CENTER;
    int multiply = 3;

    public AttributesQRcode() {
    }

    // =========== get & set ========================

    public String getAlignment() {
        return alignment;
    }

    public int getMultiply() {
        return multiply;
    }




    public  AttributesQRcode setAlignment(String alignment) {
        this.alignment = alignment;
        return this;
    }


    public AttributesQRcode setMultiply(int multiply) {
        this.multiply = multiply;
        return this;
    }



    // builder
    public  AttributesQRcode build(){
        return new AttributesQRcode()
                .setAlignment(this.alignment)
                .setMultiply(this.multiply);
    }


}