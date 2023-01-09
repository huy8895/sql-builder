package org.huytvdev.utils.sqlbuilder.lib;

import lombok.Builder;

import java.util.LinkedList;
import java.util.List;

@Builder
public final class DefaultSqlQueryChain {
    private List<String> columns = new LinkedList<>();

    public String getSql() {
        return "SELECT " + String.join(" , ", columns);
    }
}
