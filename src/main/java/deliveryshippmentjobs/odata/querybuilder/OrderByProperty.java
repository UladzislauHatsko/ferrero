package deliveryshippmentjobs.odata.querybuilder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderByProperty {

    private String property;
    private OrderByMode orderByMode;

    @Override
    public String toString() {
        return String.join(" ", property, orderByMode.getValue());
    }
}
