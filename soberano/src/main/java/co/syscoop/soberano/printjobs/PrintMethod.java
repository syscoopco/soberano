package co.syscoop.soberano.printjobs;

public enum PrintMethod {
    PDF(0),
    IMAGE(1),
    RAW(2);

    private final int code;

    PrintMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PrintMethod fromCode(int code) {
        for (PrintMethod method : values()) {
            if (method.code == code) {
                return method;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
