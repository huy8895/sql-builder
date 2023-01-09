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

    @Override
    public String toString() {
        return column
                + ' ' + operator
                + ' ' + '\'' + value + '\'';
    }
}
