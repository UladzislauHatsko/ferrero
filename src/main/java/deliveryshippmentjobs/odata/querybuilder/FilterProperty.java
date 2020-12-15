package deliveryshippmentjobs.odata.querybuilder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FilterProperty {

    private final String property;
    private final FilterOperator operator;
    private final String value;

    @Override
    public String toString() {
        return String.join(" ", property, operator.getValue(), value);
    }
}
