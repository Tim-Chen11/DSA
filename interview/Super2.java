import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HotelDataProcessor {

    // ===========================================
    // PHASE 1: Data Understanding and Consistency
    // ===========================================

    // PHASE 1 GOAL: Understand the data formats and combine them into a common format

    // PHASE 1 TASK:
    // 1. Examine the data structures from the 3 suppliers
    // 2. Identify the key differences between the formats
    // 3. Implement the function below to combine the data into a single format
    // 4. Are there any further improvements we can do to the resulting normalized data?

    static List<Map<String, Object>> SUPPLIER_A_DATA = Arrays.asList(
        new HashMap<String, Object>() {{
            put("hotel_id", "hotel_001");
            put("hotel_name", "Grand Hotel");
            put("location", "New York");
            put("price", 250.00);
            put("currency_code", "USD");
            put("star_rating", 4.5);
        }},
        new HashMap<String, Object>() {{
            put("hotel_id", "hotel_002");
            put("hotel_name", "Seaside Resort");
            put("location", "Miami");
            put("price", 180.50);
            put("currency_code", "USD");
            put("star_rating", 4.2);
        }},
        new HashMap<String, Object>() {{
            put("hotel_id", "hotel_003");
            put("hotel_name", "Mountain Lodge");
            put("location", "Denver");
            put("price", 320.00);
            put("currency_code", "USD");
            put("star_rating", 4.8);
        }}
    );

    static List<Map<String, Object>> SUPPLIER_B_DATA = Arrays.asList(
        new HashMap<String, Object>() {{
            put("property_id", "hotel_001");
            put("property_name", "Urban Boutique");
            put("city", "Los Angeles");
            put("country", "USA");
            put("pricing", new HashMap<String, Object>() {{
                put("amount", 195.75);
                put("currency", "USD");
            }});
            put("quality_score", 4.3);
        }},
        new HashMap<String, Object>() {{
            put("property_id", "hotel_002");
            put("property_name", "Historic Inn");
            put("city", "Boston");
            put("country", "USA");
            put("pricing", new HashMap<String, Object>() {{
                put("amount", 275.00);
                put("currency", "USD");
            }});
            put("quality_score", 4.6);
        }},
        new HashMap<String, Object>() {{
            put("property_id", "hotel_003");
            put("property_name", "Tech Hub Hotel");
            put("city", "San Francisco");
            put("country", "USA");
            put("pricing", new HashMap<String, Object>() {{
                put("amount", 450.00);
                put("currency", "USD");
            }});
            put("quality_score", 4.7);
        }}
    );

    static List<Map<String, Object>> SUPPLIER_C_DATA = Arrays.asList(
        new HashMap<String, Object>() {{
            put("id", "1");
            put("name", "Super Hotel");
            put("location", "Toronto, Canada");
            put("pricing", new HashMap<String, Object>() {{
                put("amount", 12.75);
                put("currency", "USD");
            }});
            put("star_rating", "3.5/5");
        }},
        new HashMap<String, Object>() {{
            put("id", "2");
            put("name", "Four Seasons");
            put("location", "Vancouver, Canada");
            put("pricing", new HashMap<String, Object>() {{
                put("amount", 53.00);
                put("currency", "USD");
            }});
            put("star_rating", "4.5/5");
        }},
        new HashMap<String, Object>() {{
            put("id", "3");
            put("name", "The Ventian");
            put("location", "122 st road, county name, Las Vegas, USA");
            put("pricing", new HashMap<String, Object>() {{
                put("amount", 123.45);
                put("currency", "USD");
            }});
            put("star_rating", "3.0/5");
        }}
    );

    public static List<Hotel> getAllHotels() {
        List<Hotel> combinedList = new ArrayList<>();

        // Supplier A
        for (Map<String, Object> map : SUPPLIER_A_DATA) {
            String id = (String) map.get("hotel_id");
            String name = (String) map.get("hotel_name");
            String location = (String) map.get("location");
            double amount = toDouble(map.get("price"));
            String currency = (String) map.get("currency_code");
            double rating = toDouble(map.get("star_rating"));
            combinedList.add(new Hotel(id, name, location, amount, currency, rating, "A"));
        }

        // Supplier B
        for (Map<String, Object> map : SUPPLIER_B_DATA) {
            String id = (String) map.get("property_id");
            String name = (String) map.get("property_name");
            String city = (String) map.get("city");
            String country = (String) map.get("country");
            String location = joinNonNull(city, country);
            @SuppressWarnings("unchecked")
            Map<String, Object> pricing = (Map<String, Object>) map.get("pricing");
            double amount = toDouble(pricing.get("amount"));
            String currency = (String) pricing.get("currency");
            double rating = toDouble(map.get("quality_score")); // same 0–5 scale assumed
            combinedList.add(new Hotel(id, name, location, amount, currency, rating, "B"));
        }

        // Supplier C
        for (Map<String, Object> map : SUPPLIER_C_DATA) {
            String id = (String) map.get("id");
            String name = (String) map.get("name");
            String location = (String) map.get("location");
            @SuppressWarnings("unchecked")
            Map<String, Object> pricing = (Map<String, Object>) map.get("pricing");
            double amount = toDouble(pricing.get("amount"));
            String currency = (String) pricing.get("currency");
            double rating = parseStarRating((String) map.get("star_rating")); // "x/5" -> x
            combinedList.add(new Hotel(id, name, location, amount, currency, rating, "C"));
        }

        return combinedList;
    }

    private static String joinNonNull(String a, String b) {
        if (a == null || a.isEmpty()) return b == null ? "" : b;
        if (b == null || b.isEmpty()) return a;
        return a + ", " + b;
    }

    private static double toDouble(Object o) {
        if (o == null) return 0.0;
        if (o instanceof Number) return ((Number) o).doubleValue();
        String s = o.toString().trim();
        // Strip non-numeric except dot and minus
        s = s.replaceAll("[^0-9.\\-]", "");
        if (s.isEmpty() || s.equals(".") || s.equals("-")) return 0.0;
        return Double.parseDouble(s);
    }

    private static double parseStarRating(String s) {
        if (s == null) return 0.0;
        // expects formats like "3.5/5"
        int slash = s.indexOf('/');
        if (slash > 0) {
            return toDouble(s.substring(0, slash));
        }
        return toDouble(s);
    }

    public static void main(String[] args) {
        List<Hotel> allHotels = getAllHotels();
        System.out.println(allHotels);
    }

    public static class Hotel {
        public final String id;
        public final String name;
        public final String location;  // free-form for now
        public final double amount;    // nightly price
        public final String currency;  // ISO code
        public final double starRating; // 0–5
        public final String source;    // "A"/"B"/"C"

        public Hotel(String id, String name, String location,
                     double amount, String currency, double starRating, String source) {
            this.id = id;
            this.name = name;
            this.location = location;
            this.amount = amount;
            this.currency = currency;
            this.starRating = starRating;
            this.source = source;
        }

        @Override
        public String toString() {
            return String.format(
                "{source:%s, id:%s, name:%s, location:%s, amount:%.2f, currency:%s, starRating:%.1f}",
                source, id, name, location, amount, currency, starRating
            );
        }
    }
}
