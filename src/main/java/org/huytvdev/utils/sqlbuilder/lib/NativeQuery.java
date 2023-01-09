package org.huytvdev.utils.sqlbuilder.lib;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NativeQuery extends AbstractConfiguredQueryBuilder<DefaultSqlQueryChain, NativeQuery>
        implements QueryBuilder<DefaultSqlQueryChain> {
    public static final String[] OPERATORS = {
            "=", "<", ">", "<=", ">=", "<>", "!=", "<=>",
            "like", "like binary", "not like", "ilike",
            "&", "|", "^", "<<", ">>", "&~", "is", "is not",
            "rlike", "not rlike", "regexp", "not regexp",
            "~", "~*", "!~", "!~*", "similar to",
            "not similar to", "not ilike", "~~*", "!~~*"};
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
        if (this.invalidOperator(operator)) {
            throw new InvalidArgumentException("Illegal operator and value combination.");
        }
        this.wheres.add(WhereClause.builder()
                                   .column(column)
                                   .operator(operator)
                                   .value(value)
                                   .and(true)
                                   .build());
        return this;
    }

    private boolean invalidOperator(String operator) {
        if (operator == null) return false;
        return Arrays.stream(OPERATORS)
                     .noneMatch(s -> s.equalsIgnoreCase(operator));
    }

    public NativeQuery whereRaw(String raw) {
        this.wheres.add(WhereClause.builder()
                                   .rawSql(raw)
                                   .and(true)
                                   .build());
        return this;
    }
}
