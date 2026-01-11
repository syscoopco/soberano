package co.syscoop.soberano.util;

/*Deepseek-generated class*/
public class Barcode {

    /**
     * Validates if a string is a valid UPC-A, EAN-8, or EAN-13 barcode.
     *
     * @param barCode the barcode string to validate.
     * @return true if and only if the barcode is valid for one of the specified formats.
     */
    public static Boolean isAValidUPCorEANBarcode(String barCode) {
        // 1. Basic null and format check
        if (barCode == null || !barCode.matches("\\d+")) {
            return false;
        }

        int length = barCode.length();
        int[] weights;

        // 2. Define the weight pattern based on the barcode length
        switch (length) {
            case 8:  // EAN-8
            case 12: // UPC-A
                // For EAN-8 and UPC-A: even indices (0-based) get weight 3, odd get 1.
                weights = new int[]{3, 1};
                break;
            case 13: // EAN-13
                // For EAN-13: even indices (0-based) get weight 1, odd get 3.
                weights = new int[]{1, 3};
                break;
            default:
                // Not a valid length for the targeted formats
                return false;
        }

        // 3. Calculate the weighted sum
        int weightedSum = 0;
        for (int i = 0; i < length; i++) {
            int digit = Character.getNumericValue(barCode.charAt(i));
            weightedSum += digit * weights[i % 2];
        }

        // 4. A valid barcode's weighted sum is a multiple of 10
        return (weightedSum % 10 == 0);
    }
}
