package org.huytvdev.utils.sqlbuilder.lib;

import lombok.Builder;
import lombok.Getter;

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
}
