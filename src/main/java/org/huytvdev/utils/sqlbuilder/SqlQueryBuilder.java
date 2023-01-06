package org.huytvdev.utils.sqlbuilder;

public class SqlQueryBuilder extends AbstractConfiguredQueryBuilder<DefaultSqlQueryChain, SqlQueryBuilder>
//    implements QueryBuilder<DefaultSqlQueryChain>
{
    protected SqlQueryBuilder(boolean allowConfigurersOfSameType, ObjectPostProcessor<Object> objectPostProcessor) {
        super(allowConfigurersOfSameType, objectPostProcessor);
    }

//    protected SqlQueryBuilder(ObjectPostProcessor<Object> objectPostProcessor) {
//        super(objectPostProcessor);
//    }

    @Override
    protected DefaultSqlQueryChain performBuild() throws Exception {
        // TODO: 6/1/2023
        return new DefaultSqlQueryChain();
    }
}
