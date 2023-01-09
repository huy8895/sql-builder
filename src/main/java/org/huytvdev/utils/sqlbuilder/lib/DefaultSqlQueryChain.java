package org.huytvdev.utils.sqlbuilder.lib;

import lombok.Builder;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public final class DefaultSqlQueryChain {
    private List<String> columns;
    private List<WhereClause> wheres;
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

        if (!this.wheres.isEmpty()) {
            statements.add("WHERE");
            for (int i = 0; i < this.wheres.size(); i++) {
                final var whereClause = this.wheres.get(i);
                if (i == 0) {
                    statements.add(whereClause.toString());
                } else {
                    statements.add(whereClause.getType() + " " + whereClause);
                }
            }
        }

        final var sql = String.join(" ", statements);
        System.out.println("queryChain.getSql() = " + sql);
        return sql;
    }
}
