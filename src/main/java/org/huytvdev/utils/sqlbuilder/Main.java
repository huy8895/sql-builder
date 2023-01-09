package org.huytvdev.utils.sqlbuilder;


import org.huytvdev.utils.sqlbuilder.lib.JoinStatement;
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

//        select * from users where name = 'John' and (votes > 100 or title = 'Admin')

        nativeQuery.join(join -> join.setTable("table_name")
                           .on())
                .appendQuery()
                .join("proposal as p", "p.id", "=", "au.proposal_id")
//                .on()
//                   .and(q -> q.or()
//                              .and())
//                   .appendQuery()
                   .build();


    }
}