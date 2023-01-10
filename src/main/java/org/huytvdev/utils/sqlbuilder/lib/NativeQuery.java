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

    private boolean invalidOperator(String operator) {
        if (operator == null) return false;
        return Arrays.stream(OPERATORS)
                     .noneMatch(s -> s.equalsIgnoreCase(operator));
    }

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
        return createWhere(column, operator, value, true);
    }

    public NativeQuery orWhere(String column, String operator, Object value) {
        return createWhere(column, operator, value, false);
    }

    private NativeQuery createWhere(String column, String operator, Object value, boolean and) {
        if (this.invalidOperator(operator)) {
            throw new InvalidArgumentException("Illegal operator and value combination.");
        }
        this.wheres.add(WhereClause.builder()
                                   .column(column)
                                   .operator(operator)
                                   .value(value)
                                   .and(and)
                                   .build());
        return this;
    }

    public NativeQuery where(Customizer<NativeQuery> whereClauseCustomizer) {
        final var query = new NativeQuery();
        whereClauseCustomizer.customize(query);
        final var whereStatements = WhereClause.extractedStatements(query.wheres);
        final var rawSql = "(" + String.join(" ", whereStatements) + ")";
        this.wheres.add(WhereClause.builder()
                                   .rawSql(rawSql)
                                   .and(true)
                                   .build());
        return this;
    }

    public NativeQuery whereRaw(String raw) {
        this.wheres.add(WhereClause.builder()
                                   .rawSql(raw)
                                   .and(true)
                                   .build());
        return this;
    }
}
