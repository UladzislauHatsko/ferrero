package deliveryshippmentjobs.odata.querybuilder;

public enum OrderByMode {
    ASC("asc"),
    DESC("desc");

    private String value;

    OrderByMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
