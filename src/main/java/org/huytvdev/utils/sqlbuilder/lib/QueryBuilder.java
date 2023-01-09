package org.huytvdev.utils.sqlbuilder.lib;

public interface QueryBuilder<O> {
    O build() throws Exception;
}
