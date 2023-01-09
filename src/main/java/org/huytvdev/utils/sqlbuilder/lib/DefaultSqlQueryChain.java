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

        final var sql = String.join(" ", statements);
        System.out.println("queryChain.getSql() = " + sql);
        return sql;
    }
}
