package org.huytvdev.utils.sqlbuilder.lib;

public class NativeQuery extends AbstractConfiguredQueryBuilder<DefaultSqlQueryChain, NativeQuery>
        implements QueryBuilder<DefaultSqlQueryChain> {
    public NativeQuery(boolean allowConfigurersOfSameType, ObjectPostProcessor<Object> objectPostProcessor) {
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

    public JoinStatement<NativeQuery> join() throws Exception {
        return this.addStatement(new JoinStatement<>());
    }
}
