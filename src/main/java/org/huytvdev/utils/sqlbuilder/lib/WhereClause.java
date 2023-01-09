package org.huytvdev.utils.sqlbuilder.lib;

import lombok.Builder;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
public class WhereClause {
    public static final String SINGLE_QUOTE = "'";
    private String column;
    private String operator;
    private Object value;
    private boolean and;
    private String rawSql;


    public String getSql() {
        if (this.rawSql != null) return this.rawSql;
        String value = this.value == null ? null : String.format("'%s'", this.value);
        return column
                + ' ' + operator
                + ' ' + value;
    }

    public static List<String> extractedStatements(List<WhereClause> whereClauses) {
        final var result = new LinkedList<String>();
        for (int i = 0; i < whereClauses.size(); i++) {
            final var whereClause = whereClauses.get(i);
            if (i == 0) {
                result.add(whereClause.getSql());
            } else {
                final var isAnd = whereClause.isAnd() ? "AND" : "OR";
                result.add(isAnd + " " + whereClause.getSql());
            }
        }
        return result;
    }
}
