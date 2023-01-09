package org.huytvdev.utils.sqlbuilder.lib;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NativeQuery extends AbstractConfiguredQueryBuilder<DefaultSqlQueryChain, NativeQuery>
        implements QueryBuilder<DefaultSqlQueryChain> {
    private final List<String> columns = new LinkedList<>();
    private String from;
    private List<WhereClause> wheres = new LinkedList<>();

    public NativeQuery(boolean allowConfigurersOfSameType, ObjectPostProcessor<Object> objectPostProcessor) {
        super(allowConfigurersOfSameType, objectPostProcessor);
    }

    public NativeQuery() {
        super(true, new ObjectPostProcessor<Object>() {
            @Override
            public <O> O postProcess(O object) {
                return object;
            }
        });
    }

//    protected SqlQueryBuilder(ObjectPostProcessor<Object> objectPostProcessor) {
//        super(objectPostProcessor);
//    }

    @Override
    protected DefaultSqlQueryChain performBuild() throws Exception {
        return DefaultSqlQueryChain.builder()
                                   .columns(this.columns)
                                   .from(this.from)
                                   .wheres(this.wheres)
                                   .build();
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

    public NativeQuery select(String... columns) {
        Collections.addAll(this.columns, columns);
        return this;
    }

    public NativeQuery from(String table) {
        this.from = table;
        return this;
    }

    public NativeQuery where(String column, String operator, Object value) {
        this.wheres.add(WhereClause.builder()
                                   .column(column)
                                   .operator(operator)
                                   .value(value)
                                   .and(true)
                                   .build());
        return this;
    }
}
