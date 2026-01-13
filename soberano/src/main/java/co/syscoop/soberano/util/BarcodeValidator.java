package co.syscoop.soberano.util;

/*Deepseek-generated class*/
public class BarcodeValidator {

    public static Boolean isAValidBarCode(String barCode) {
        
    	if (barCode == null || !barCode.matches("\\d+")) {
            return false;
        }

        int length = barCode.length();
        int[] weights;

        // 2. Define the weight pattern based on the barcode length
        switch (length) {
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
