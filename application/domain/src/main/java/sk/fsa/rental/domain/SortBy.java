package sk.fsa.rental.domain;

public enum SortBy {
    NEWEST, PRICE_ASC, PRICE_DESC, AREA_ASC, AREA_DESC;

    public static SortBy fromString(String value) {
        if (value == null) return NEWEST;
        return switch (value.toLowerCase()) {
            case "price_asc"  -> PRICE_ASC;
            case "price_desc" -> PRICE_DESC;
            case "area_asc"   -> AREA_ASC;
            case "area_desc"  -> AREA_DESC;
            default           -> NEWEST;
        };
    }
}
