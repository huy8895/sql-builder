package org.huytvdev.utils.sqlbuilder;

public interface QueryBuilder<O> {
    O build() throws Exception;
}
