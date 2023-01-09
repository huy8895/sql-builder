package org.huytvdev.utils.sqlbuilder.lib;


public class JoinStatement<J extends QueryBuilder<DefaultSqlQueryChain>>
        extends QueryStatementAdapter<DefaultSqlQueryChain, J> {
    public JoinStatement() {
    }

    public JoinStatement<J> or(){
        return this;
    }
}
