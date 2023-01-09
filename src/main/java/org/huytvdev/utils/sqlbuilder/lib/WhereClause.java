package org.huytvdev.utils.sqlbuilder.lib;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WhereClause {
    private String column;
    private String operator;
    private Object value;
    private boolean and;
    private String rawSql;


    public String getSql() {
        if (this.rawSql != null) return this.rawSql;
        return column
                + ' ' + operator
                + ' ' + '\'' + value + '\'';
    }
}
