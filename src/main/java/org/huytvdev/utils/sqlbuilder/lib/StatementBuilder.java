package org.huytvdev.utils.sqlbuilder.lib;

public interface StatementBuilder<S> {
    S and (String column, String operator, Object value);
    S or (String column, String operator, Object value);
}
