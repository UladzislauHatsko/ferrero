package deliveryshippmentjobs.odata.querybuilder;

import deliveryshippmentjobs.model.Entity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static org.springframework.util.CollectionUtils.isEmpty;
import static utils.DateUtils.wrapToFilterValue;

/**
 * @author uladzislau.hatsko
 */
public class ODataQueryBuilder {

    private final String entity;
    private final Set<String> expandFields;
    private final List<String> filters;

    public ODataQueryBuilder(Entity entity) {
        this.entity = entity.getValue();
        expandFields = new HashSet<>();
        filters = new ArrayList<>();
    }

    public ODataQueryBuilder withExpandField(String expandField) {
        this.expandFields.add(expandField);
        return this;
    }

    public ODataQueryBuilder withExpandFields(Set<String> expandFields) {
        this.expandFields.addAll(expandFields);
        return this;
    }

    public ODataQueryBuilder withFilter(String filterProperty, FilterOperator filterOperator, String filterValue) {
        this.filters.add(getFilterString(filterProperty, filterOperator.getValue(), wrapWithQuotes(filterValue)));
        return this;
    }

    public ODataQueryBuilder withFilter(String filterProperty, FilterOperator filterOperator, LocalDate filterDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String filterValue = "datetime'" + formatter.format(filterDate) + "T00:00:00'";
        this.filters.add(getFilterString(filterProperty, filterOperator.getValue(), filterValue));
        return this;
    }

    public ODataQueryBuilder withFilter(String filterProperty, FilterOperator filterOperator, Instant filterDate) {
        String filterValue = wrapToFilterValue(filterDate);
        this.filters.add(getFilterString(filterProperty, filterOperator.getValue(), filterValue));
        return this;
    }

    public ODataQueryBuilder withFilterIn(String filterFieldName, List<String> filterValues) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < filterValues.size(); i++) {
            if (i != 0) {
                sb.append(" or ");
            }
            sb.append(filterFieldName);
            sb.append(" eq ");
            sb.append(wrapWithQuotes(filterValues.get(i)));
        }
        sb.append(")");
        this.filters.add(sb.toString());
        return this;
    }

    public String buildUrl() {

        // Concatenate the internal components...
        StringBuilder url = new StringBuilder();
        url.append(entity);

        url.append("?$format=json");

        // expand fields
        if (!isEmpty(expandFields)) {
            url.append("&$expand=").append(String.join(",", expandFields));
        }

        // filter parameters
        if (!isEmpty(filters)) {
            url.append("&$filter=").append(String.join(" and ", filters));
        }

        return url.toString();
    }

    private String getFilterString(String fieldName, String operator, String value) {
        return format("%s %s %s", fieldName, operator, value);
    }

    private String wrapWithQuotes(String value) {
        return format("'%s'", value);
    }

}
