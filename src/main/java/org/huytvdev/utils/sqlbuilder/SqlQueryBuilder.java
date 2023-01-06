package org.huytvdev.utils.sqlbuilder;

public interface SqlQueryBuilder<S extends SqlQueryBuilder<S>>
        extends QueryBuilder<DefaultSqlQueryChain> {

    <C extends QueryConfigurer<DefaultSqlQueryChain, S>> C removeConfigurer(Class<C> clazz);
}
