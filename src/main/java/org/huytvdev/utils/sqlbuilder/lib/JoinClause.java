package org.huytvdev.utils.sqlbuilder.lib;

import lombok.Builder;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
public class JoinClause {
    public static final String SINGLE_QUOTE = "'";
    private String type; //inner, right, left
    private String first;
    private String table;
    private String operator;
    private String second;
    private boolean and;


    public String getSql() {
        return table
                + ' ' + "ON"
                + ' ' + first
                + ' ' + operator
                + ' ' + second
                ;
    }

    public static List<String> extractedStatements(List<JoinClause> clauses) {
        final var result = new LinkedList<String>();
        for (final JoinClause clauseSql : clauses) {
            result.add(clauseSql.type + ' ' + "JOIN");
            result.add(clauseSql.getSql());
        }
        return result;
    }
}
