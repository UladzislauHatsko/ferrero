package deliveryshippmentjobs.odata.querybuilder;

public enum FilterOperator {
    EQUALS("eq"),
    NOT_EQUALS("ne"),
    LESS_THAN("lt"),
    GREATER_EQUALS("ge"),
    LESS_EQUALS("le"),
    IN("in");

    private String value;

    FilterOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
