//taken and adapted from 
//https://github.com/402d/RawbtAPI/

package co.syscoop.soberano.printjobs.rawbt;

public class CommandNewLine implements RawbtCommand {
    final public static String TAG = "ln"; // for GSON
    String command = TAG;

    int count = 1;

    public CommandNewLine() {
    }

    public CommandNewLine(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
