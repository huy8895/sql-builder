package org.huytvdev.utils.sqlbuilder.lib;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Builder
@AllArgsConstructor
public class JoinClause {
    public static final String SINGLE_QUOTE = "'";
    private String type; //inner, right, left
    private String first;
    private String table;
    private String operator;
    private String second;
    private boolean and;
    private final List<JoinClause> conditions = new LinkedList<>();

    public JoinClause(String table, String type) {
        this.table = table;
        this.type = type;
    }

    private String getSql() {
        return table
                + ' ' + "ON"
                + ' ' + first
                + ' ' + operator
                + ' ' + second
                ;
    }

    private String getSql(String table) {
        return table
                + ' ' + "ON"
                + ' ' + first
                + ' ' + operator
                + ' ' + second
                ;
    }

    public List<JoinClause> getConditions() {
        return conditions;
    }

    private JoinClause on(String first, String operator, String second, boolean isAnd) {
        this.conditions.add(JoinClause.builder()
                                      .first(first)
                                      .operator(operator)
                                      .second(second)
                                      .and(isAnd)
                                      .build());
        return this;
    }

    public JoinClause on(String first, String operator, String second){
        return this.on(first, operator, second, true);
    };

    public JoinClause or(String first, String operator, String second){
        return this.on(first, operator, second, false);
    };
    public static List<String> extractedStatements(List<JoinClause> clauses) {
        final var result = new LinkedList<String>();
        final var index = new AtomicInteger(0);
        for (final JoinClause clauseSql : clauses) {
            result.add(clauseSql.type + ' ' + "JOIN");
            if (clauseSql.conditions.isEmpty()){
                result.add(clauseSql.getSql());
                continue;
            }
            clauseSql.conditions.forEach(joinClause -> {
                if (index.getAndIncrement() == 0) {
                    result.add(clauseSql.table
                                       + ' ' + "ON"
                                       + ' ' + joinClause.first
                                       + ' ' + joinClause.operator
                                       + ' ' + joinClause.second);
                } else {
                    final var isAnd = joinClause.and ? "AND" : "OR";
                    result.add(isAnd + " " + joinClause.first
                                       + ' ' + joinClause.operator
                                       + ' ' + joinClause.second);
                }
            });

        }


        return result;
    }
}
