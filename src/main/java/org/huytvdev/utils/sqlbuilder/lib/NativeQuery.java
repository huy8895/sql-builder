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

//    public JoinStatement<NativeQuery> join(String table) throws Exception {
//        return this.addStatement(new JoinStatement<>(table));
//    }
//
//    public JoinStatement<NativeQuery> join(String tablename, Customizer<JoinStatement<NativeQuery>> customizer)
//            throws Exception {
//        final var statement = new JoinStatement<NativeQuery>(tablename);
//        customizer.customize(statement);
//        return this.addStatement(statement);
//    }

    public JoinStatement<NativeQuery> join(Customizer<JoinStatement<NativeQuery>> customizer)
            throws Exception {
        final var statement = new JoinStatement<NativeQuery>();
        customizer.customize(statement);
        return this.addStatement(statement);
    }


    public NativeQuery join(String table, String first, String operator, String second)
            throws Exception {
        final var statement = new JoinStatement<NativeQuery>();
        this.addStatement(statement);
        return this;
    }
}
