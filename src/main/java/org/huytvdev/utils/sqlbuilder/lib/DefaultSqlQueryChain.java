package org.huytvdev.utils.sqlbuilder.lib;

import lombok.Builder;

import java.util.LinkedList;
import java.util.List;

@Builder
public final class DefaultSqlQueryChain {
    private List<String> columns;
    private List<WhereClause> wheres;
    private List<JoinClause> joins;
    private String from;

    public String getSql() {
        final List<String> statements = new LinkedList<>();
        statements.add("SELECT");
        if (this.columns.isEmpty()) {
            statements.add("*");
        } else {
            statements.add(String.join(",", this.columns));
        }


        if (null != from) {
            statements.add("FROM");
            statements.add(this.from);
        }

        if (!this.joins.isEmpty()) {
            final var joinStatements = JoinClause.extractedStatements(this.joins);
            statements.addAll(joinStatements);
        }

        if (!this.wheres.isEmpty()) {
            statements.add("WHERE");
            final var wheresStatements = WhereClause.extractedStatements(this.wheres);
            statements.addAll(wheresStatements);
        }

        final var sql = String.join(" ", statements);
        System.out.println("queryChain.getSql() = " + sql);
        return sql;
    }
}
