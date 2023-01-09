package org.huytvdev.utils.sqlbuilder;


import org.huytvdev.utils.sqlbuilder.lib.NativeQuery;
import org.huytvdev.utils.sqlbuilder.lib.ObjectPostProcessor;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
//        final var sqlQueryBuilder = new SqlQueryBuilder(true, ObjectPostProcessor<>);
//        final var build = sqlQueryBuilder.build();
        final var nativeQuery = new NativeQuery(true, new ObjectPostProcessor<Object>() {
            @Override
            public Object postProcess(Object object) {
                return null;
            }
        });

        nativeQuery.join()
                        .or()
                   .appendQuery()
                   .build();


    }
}