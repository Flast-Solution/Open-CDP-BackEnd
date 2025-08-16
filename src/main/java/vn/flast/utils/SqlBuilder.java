package vn.flast.utils;
/**************************************************************************/
/*  app.java                                                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/

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

@SuppressWarnings({"UnusedReturnValue", "unchecked", "FieldCanBeLocal", "unused"})
@NoArgsConstructor
public class SqlBuilder {

    private String query;
    private String orderBy;
    private String groupBy;
    private Map<String, Object> maps;
    private List<String> nonColumn;

    private final String COUNT_QUERY = "COUNT(*)";
    /* DISTINCT(a.id) as 'id2d', a.*  */
    private final String SELECT_QUERY = "*";

    public static SqlBuilder init(String query) {
        var instances = new SqlBuilder();
        instances.query = query;
        instances.maps = new HashMap<>();
        instances.nonColumn = new ArrayList<>();
        return instances;
    }

    public SqlBuilder addOrderByDesc(String strOrderBy) {
        orderBy = "ORDER BY " + strOrderBy + " DESC";
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

    public String countQueryString() {
        String PRE_FIX_COUNT = COUNT_QUERY.toUpperCase().startsWith("SELECT") ? "" : "SELECT ";
        return PRE_FIX_COUNT + COUNT_QUERY + " " + this.builderCount();
    }

    public String builderCount() {
        StringBuilder stringBuilder = new StringBuilder(this.query);
        if(maps == null || maps.isEmpty()) {
            return stringBuilder.toString();
        }
        mapToStringBuilder(stringBuilder);
        return stringBuilder.toString();
    }

    private void mapToStringBuilder(StringBuilder stringBuilder) {
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
    }

    public SqlBuilder like(String column, String value) {
        if(StringUtils.isNotEmpty(column) && value != null) {
            String valueEscape = escapeSqlLiteral(value);
            maps.put(" AND ".concat(column), " LIKE '%" + valueEscape + "%'");
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
            String valueEscape = escapeSqlLiteral(value);
            maps.put(" AND ".concat(column).concat(" = "), "'" + valueEscape + "'");
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

    private String escapeSqlLiteral(String s) {
        return s.replace("'", "''");
    }
}
