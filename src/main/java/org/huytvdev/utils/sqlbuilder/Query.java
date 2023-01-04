package org.huytvdev.utils.sqlbuilder;

import java.util.*;

public class Query {
    public static QueryBuilder table(String table){
        return new QueryBuilder(table);
    }

    static class QueryBuilder {
        private final String table;
        /**
         * The columns that should be returned.
         */
        private final List<String> columns = new LinkedList<>();

        private final List<String> wheres = new LinkedList<>();

        private final Map<String, Object> mapParamValue;

        private QueryBuilder(String table) {
            this.table = table;
            this.mapParamValue = new LinkedHashMap<>();
        }

        public QueryBuilder select(String... columns){
            Collections.addAll(this.columns, columns);
            return this;
        }

        public QueryBuilder from(String table){
            return this;
        };

        public QueryBuilder where(String column, String operator, Object value) {
            this.mapParamValue.put(column, value);
            return this;
        }

        public QueryBuilder where(String column, String operator, String parameterName) {
            this.mapParamValue.put(column, parameterName);
            return this;
        }

        public QueryBuilder whereRaw(String sql) {
            this.wheres.add(sql);
            return this;
        }

        public Query build() {
            return new Query();
        }
    }
}
