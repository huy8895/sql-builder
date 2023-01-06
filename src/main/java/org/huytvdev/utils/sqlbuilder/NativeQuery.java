package org.huytvdev.utils.sqlbuilder;

public class NativeQuery extends AbstractConfiguredQueryBuilder<DefaultSqlQueryChain, NativeQuery>
        implements QueryBuilder<DefaultSqlQueryChain>, SqlQueryBuilder<NativeQuery> {
    protected NativeQuery(boolean allowConfigurersOfSameType, ObjectPostProcessor<Object> objectPostProcessor) {
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

    public JoinConfigurer<NativeQuery> join() throws Exception {
        return apply(new JoinConfigurer<>());
    }
}
