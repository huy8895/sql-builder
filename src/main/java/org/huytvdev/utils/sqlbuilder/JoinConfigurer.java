package org.huytvdev.utils.sqlbuilder;

public class JoinConfigurer<J extends SqlQueryBuilder<J>>
        extends AbstractSqlConfigurer<JoinConfigurer<J>, J> {
    public JoinConfigurer() {
    }

    public JoinConfigurer<J> or(){
        return this;
    }
}
