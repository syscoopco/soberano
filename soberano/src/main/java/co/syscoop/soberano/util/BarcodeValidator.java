package co.syscoop.soberano.util;

/*Deepseek-generated class*/
public class BarcodeValidator {

    /**
     * Validates if a string is a correctly formatted and checksum-valid
     * UPC-A, EAN-8, or EAN-13 barcode.
     * Treats each barcode standard as a distinct format based on length.
     *
     * @param barCode the barcode string to validate.
     * @return true if and only if the barcode is valid for its specific format.
     */
    public static Boolean isAValidBarCode(String barCode) {
        // 1. Basic null and format check
        if (barCode == null || !barCode.matches("\\d+")) {
            return false;
        }

        int length = barCode.length();

        // 2. Dispatch validation based on exact length
        switch (length) {
            case 12:
                return isValidUPCA(barCode);
            case 13:
                return isValidEAN13(barCode);
            default:
                return false; // Not a length for our targeted formats
        }
    }

    /**
     * Validates a 12-digit UPC-A barcode.
     * Uses the UPC-A weight pattern: 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3.
     * The 12th digit is the check digit.
     */
    private static boolean isValidUPCA(String code) {
        // Weight pattern for positions 1-11 (0-indexed 0-10): 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            int digit = Character.getNumericValue(code.charAt(i));
            sum += digit * ((i % 2 == 0) ? 3 : 1);
        }
        int calculatedCheckDigit = (10 - (sum % 10)) % 10;
        int actualCheckDigit = Character.getNumericValue(code.charAt(11));
        return calculatedCheckDigit == actualCheckDigit;
    }

    /**
     * Validates a 13-digit EAN-13 barcode.
     * Uses the EAN-13 weight pattern: 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3.
     * The 13th digit is the check digit.
     * Note: Position 1 (the leftmost digit) is part of the number system,
     * but is weighted in the calculation.
     */
    private static boolean isValidEAN13(String code) {
        // Weight pattern for positions 1-12 (0-indexed 0-11): 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(code.charAt(i));
            sum += digit * ((i % 2 == 0) ? 1 : 3); // Note: First digit uses weight 1
        }
        int calculatedCheckDigit = (10 - (sum % 10)) % 10;
        int actualCheckDigit = Character.getNumericValue(code.charAt(12));
        return calculatedCheckDigit == actualCheckDigit;
    }
}