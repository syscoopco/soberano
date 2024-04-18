//taken and adapted from 
//https://github.com/402d/RawbtAPI/

package co.syscoop.soberano.printjobs.rawbt;

public class CommandBytesInBase64 implements RawbtCommand {
    final public static String TAG = "sendBytes"; // for GSON
    String command = TAG;
    String base64 = null;

    public CommandBytesInBase64() {
    }

    public CommandBytesInBase64(String base64) {
        this.base64 = base64;
    }

     public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}