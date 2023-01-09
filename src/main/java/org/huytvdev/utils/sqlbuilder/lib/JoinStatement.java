package org.huytvdev.utils.sqlbuilder.lib;


public class JoinStatement<J extends QueryBuilder<DefaultSqlQueryChain>>
        extends QueryStatementAdapter<DefaultSqlQueryChain, J> {
    private String table;
    private String condition;

    public JoinStatement() {
    }


    public JoinStatement<J> setTable(String table) {
        this.table = table;
        return this;
    }

    public JoinStatement<J> setCondition(String table) {
        this.table = table;
        return this;
    }


    public JoinCondition on() {
        return new JoinCondition();
    }

    public String getTable() {
        return table;
    }

    private static class JoinCondition implements StatementBuilder<JoinCondition> {
        @Override
        public JoinCondition and(String column, String operator, Object value) {
            return JoinCondition.this;
        }

        @Override
        public JoinCondition or(String column, String operator, Object value) {
            return JoinCondition.this;
        }
    }


    public JoinStatement<J> and(Customizer<StatementBuilder<JoinStatement<J>>> customizer) {
        return this;
    }


}
