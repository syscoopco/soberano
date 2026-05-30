package co.syscoop.soberano.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class GoUPCScraper {

    /**
     * Fetches product details from go-upc.com for a given barcode.
     *
     * @param barcode The product barcode (e.g., "096619883165").
     * @return A Product object containing name, brand, category, and description, or null if not found.
     */
    public static String getProductDetails(String barcode) {
        if (barcode == null || barcode.trim().isEmpty()) {
            return null;
        }

        String url = "https://go-upc.com/search?q=" + barcode;
        try {
            System.out.println("Fetching: " + url);
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                    .get();

            Product product = new Product();

            // 1. Extract Product Name
            Element nameElement = doc.selectFirst("h1.product-name");
            if (nameElement != null) {
                product.setName(nameElement.text());
            }

            // Check if we found at least a product name
            if (product.getName() == null) {
                return null;
            }

            System.out.println("Found: " + product);
            return product.getName();

        } catch (IOException e) {
            return null;
        }
    }

    // Simple Product class to hold the extracted data
    public static class Product {
        private String name;

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}