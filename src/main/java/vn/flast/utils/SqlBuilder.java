package vn.flast.utils;

import jakarta.persistence.Query;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
public class SqlBuilder {

    private String query;
    private String orderBy;
    private String groupBy;
    private Map<String, Object> maps;
    private List<String> nonColumn;

    public static SqlBuilder init(String query) {
        var instances = new SqlBuilder();
        instances.query = query;
        instances.maps = new HashMap<>();
        instances.nonColumn = new ArrayList<>();
        return instances;
    }

    public SqlBuilder addOrderBy(String strOrderBy) {
        orderBy = strOrderBy;
        return this;
    }

    public SqlBuilder addGroupBy(String column) {
        groupBy = column;
        return this;
    }

    private String stripFirst(Object andOr) {
        String tmp = "";
        if(andOr instanceof Integer int0) {
            tmp = String.valueOf(int0);
        }
        if(andOr instanceof String str) {
            tmp = str;
        }
        if(tmp.startsWith(" AND ")) {
            return tmp.substring(5);
        }
        if(tmp.startsWith(" OR ")) {
            return tmp.substring(4);
        }
        return tmp;
    }

    private void appendOrderAndGroupBy(StringBuilder stringBuilder) {
        if(StringUtils.isNotEmpty(orderBy)) {
            stringBuilder.append(" ").append(orderBy);
        }
        if(StringUtils.isNotEmpty(groupBy)) {
            stringBuilder.append(" GROUP BY ").append(groupBy);
        }
    }

    public String builder() {
        StringBuilder stringBuilder = new StringBuilder(this.query);
        if(maps == null || maps.isEmpty()) {
            appendOrderAndGroupBy(stringBuilder);
            return stringBuilder.toString();
        }
        int index = 0;
        for(var entry : maps.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();
            index ++;
            if(index == 1) {
                stringBuilder.append(" WHERE ").append(stripFirst(key)).append(value);
                continue;
            }
            if(value instanceof Integer intVal) {
                stringBuilder.append(key).append(intVal);
            }
            if(value instanceof String strVal) {
                stringBuilder.append(key).append(strVal);
            }
        }
        /* Các query dạng nhóm điều kiện như này nó sẽ không cho vào column nào được
         * AND (b.warehouse_status = 0 OR b.warehouse_status IS NULL OR b.`status` = 26) */
        if(!nonColumn.isEmpty()) {
            String joinNonColumn = String.join(" ", nonColumn);
            stringBuilder.append(" ").append(joinNonColumn);
        }
        appendOrderAndGroupBy(stringBuilder);
        return Common.escape(stringBuilder.toString());
    }

    public SqlBuilder addQueryNonColumn(String q) {
        nonColumn.add(q);
        return this;
    }

    public SqlBuilder like(String column, String value) {
        if(StringUtils.isNotEmpty(column) && value != null) {
            maps.put(" AND ".concat(column), " LIKE '% " + value + " %'");
        }
        return this;
    }

    public SqlBuilder addDateBetween(String column, Date start, Date end) {
        if(start == null || end == null) {
            return this;
        }
        if(StringUtils.isNotEmpty(column)) {
            String strStart = DateUtils.dateToString(start, "yyyy-MM-dd HH:mm:ss");
            String strEnd = DateUtils.dateToString(end, "yyyy-MM-dd HH:mm:ss");
            maps.put(" AND ".concat(column).concat(" BETWEEN "), "'" + strStart + "'" + " AND " + "'" + strEnd + "'");
        }
        return this;
    }

    public SqlBuilder addDateBetween(String column, String start, String end) {
        if(start == null || end == null) {
            return this;
        }
        if(StringUtils.isNotEmpty(column)) {
            maps.put(" AND ".concat(column).concat(" BETWEEN "), "'" + start + "'" + " AND " + "'" + end + "'");
        }
        return this;
    }

    public SqlBuilder addNotEquals(String column, Object value) {
        if(StringUtils.isNotEmpty(column) && value != null) {
            maps.put(" AND ".concat(column).concat(" != "), value);
        }
        return this;
    }

    public SqlBuilder addIntegerEquals(String column, Integer value) {
        if(StringUtils.isNotEmpty(column) && value != null) {
            maps.put(" AND ".concat(column).concat(" = "), value);
        }
        return this;
    }

    public SqlBuilder addNotEmpty(String column) {
        if(StringUtils.isNotEmpty(column)) {
            maps.put(" AND ".concat(column).concat(" != "), "''");
        }
        return this;
    }

    public SqlBuilder addNotNUL(String column) {
        if(StringUtils.isNotEmpty(column)) {
            maps.put(" AND ".concat(column).concat(" IS NOT NULL "), "");
        }
        return this;
    }

    public SqlBuilder addIsEmpty(String column) {
        if(StringUtils.isNotEmpty(column)) {
            maps.put(" AND ".concat(column).concat(" IS NULL "), "");
        }
        return this;
    }

    public SqlBuilder addDateLessThan(String column, Date value) {
        if(StringUtils.isNotEmpty(column) && value != null) {
            String strDate = DateUtils.dateToString(value, "yyyy-MM-dd HH:mm:ss");
            maps.put(" AND ".concat(column).concat(" <= "), "'" + strDate + "'");
        }
        return this;
    }

    public SqlBuilder addStringEquals(String column, String value) {
        if(StringUtils.isNotEmpty(column) && StringUtils.isNotEmpty(value)) {
            maps.put(" AND ".concat(column).concat(" = "), "'" + value + "'");
        }
        return this;
    }

    public SqlBuilder addIn(String column, List<String> values) {
        if(StringUtils.isNotEmpty(column) && !Common.CollectionIsEmpty(values)) {
            String joins = String.join(",", values);
            maps.put(" AND ".concat(column).concat(" IN "), "(" + joins + ")");
        }
        return this;
    }

    public SqlBuilder addNotIn(String column, Integer ...values) {
        if(StringUtils.isEmpty(column) || Arrays.stream(values).findAny().isEmpty()) {
            return this;
        }
        var joins = Stream.of(values).map(String::valueOf).collect(Collectors.joining(","));
        maps.put(" AND ".concat(column).concat(" NOT IN "), "(" + joins + ")");
        return this;
    }

    public SqlBuilder lessThanOrEqualsTo(String column, String value) {
        if(StringUtils.isNotEmpty(column) && StringUtils.isNotEmpty(value)) {
            maps.put(" AND ".concat(column).concat(" <= "), value);
        }
        return this;
    }

    public SqlBuilder greatThanOrEqualsTo(String column, String value) {
        if(StringUtils.isNotEmpty(column) && StringUtils.isNotEmpty(value)) {
            maps.put(" AND ".concat(column).concat(" >= "), value);
        }
        return this;
    }

    public void removeMapKey(String key) {
        maps.remove(key);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getListOfNativeQuery(Query query, Class<T> type){
        return (List<T>) query.getResultList();
    }

    public Long countOrSumQuery(Query query) {
        var object = query.getSingleResult();
        if(object instanceof Long count) {
            return count;
        }
        if( object instanceof BigInteger bigInteger) {
            return Optional.of(bigInteger).map(BigInteger::longValue).orElse(0L);
        }
        BigDecimal result = (BigDecimal) object;
        return Optional.ofNullable(result).map(BigDecimal::longValue).orElse(0L);
    }
}
