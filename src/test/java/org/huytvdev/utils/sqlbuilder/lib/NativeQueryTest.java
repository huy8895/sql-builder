package org.huytvdev.utils.sqlbuilder.lib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NativeQueryTest {
    @Test
    void testSelect() throws Exception {
        final var nativeQuery = new NativeQuery();
        final var queryChain = nativeQuery.select("id", "name")
                                          .build();
        final var expected = "SELECT id , name";
        final var actual = queryChain.getSql();
        Assertions.assertEquals(expected.replaceAll("\\s+", ""),
                                actual.replaceAll("\\s+", ""));
    }

    @Test
    void testSelectFrom() throws Exception {
        final var nativeQuery = new NativeQuery();
        final var queryChain =
                nativeQuery.select("id", "name")
                           .from("user")
                           .build();
        final var expected = "SELECT id , name FROM user";
        final var actual = queryChain.getSql();
        Assertions.assertEquals(expected.replaceAll("\\s+", ""),
                                actual.replaceAll("\\s+", ""));
    }

    @Test
    void testSelectFromWhere() throws Exception {
        final var nativeQuery = new NativeQuery();
        final var queryChain =
                nativeQuery.select("id", "name")
                           .from("user")
                           .where("user.id", "=", 1)
                           .build();
        final var expected = "SELECT id , name FROM user WHERE user.id = '1'";
        final var actual = queryChain.getSql();
        Assertions.assertTrue(expected.replaceAll("\\s+", "")
                                      .equalsIgnoreCase(actual.replaceAll("\\s+", "")));
    }

    @Test
    void testSelectFromWhereMulti() throws Exception {
        final var nativeQuery = new NativeQuery();
        final var queryChain =
                nativeQuery.select("id", "name")
                           .from("user")
                           .where("user.id", "=", 1)
                           .where("user.name", "LIKE", "%" + "huy" + "%")
                           .build();
        final var expected = "SELECT id , name FROM user WHERE user.id = '1' AND user.name LIKE '%huy%'";
        final var actual = queryChain.getSql();
        Assertions.assertEquals(expected.replaceAll("\\s+", ""),
                                      (actual.replaceAll("\\s+", "")));
    }

    @Test
    void testWhereInvalidOperator() throws Exception {
        final var nativeQuery = new NativeQuery();
        nativeQuery.select("id", "name")
                   .from("user")
                   .where("user.id", "=", 1)
                   .where("user.name", "LIKE", "%" + "huy" + "%");
        Assertions.assertThrows(InvalidArgumentException.class,
                                () -> nativeQuery.where("user.age", "abc", "not null")
                                                 .build());
    }

    @Test
    void testWhereRaw() throws Exception {
        final var nativeQuery = new NativeQuery();
        final var queryChain =
                nativeQuery.select("id", "name")
                           .from("user")
                           .where("user.id", "=", 1)
                           .where("user.name", "LIKE", "%" + "huy" + "%")
                           .whereRaw("user.role is not null")
                           .build();
        final var expected = "SELECT id , name FROM user WHERE user.id = '1' AND user.name LIKE '%huy%' AND user.role is not null";
        final var actual = queryChain.getSql();
        Assertions.assertEquals(expected.replaceAll("\\s+", ""),
                                (actual.replaceAll("\\s+", "")));
    }

    @Test
    void testWhereIsNull() throws Exception {
        final var nativeQuery = new NativeQuery();
        final var queryChain =
                nativeQuery.select("id", "name")
                           .from("user")
                           .where("user.id", "=", 1)
                           .where("user.name", "LIKE", "%" + "huy" + "%")
                           .where("user.role", "is", null)
                           .build();
        final var expected = "SELECT id , name FROM user WHERE user.id = '1' AND user.name LIKE '%huy%' AND user.role is null";
        final var actual = queryChain.getSql();
        Assertions.assertEquals(expected.replaceAll("\\s+", ""),
                                (actual.replaceAll("\\s+", "")));
    }

    //Logical Grouping
    //select * from users where name = 'John' and (votes > 100 or title = 'Admin')
    @Test
    void testWhereGroup() throws Exception {
        final var nativeQuery = new NativeQuery();
        final var queryChain =
                nativeQuery.select("*")
                           .from("users")
                           .where("name", "=", "John")
                           .where(q -> q.where("votes", ">", "100")
                                        .orWhere("title", "=", "Admin"))
                           .build();
        final var expected = "select * from users where name = 'John' and (votes > '100' or title = 'Admin')";
        final var actual = queryChain.getSql();
        Assertions.assertEquals(expected.toUpperCase().replaceAll("\\s+", ""),
                                (actual.toUpperCase().replaceAll("\\s+", "")));
    }

    @Test
    void testJoin() throws Exception {
        final var nativeQuery = new NativeQuery();
        final var queryChain =
                nativeQuery.select("*")
                           .from("users")
                           .join("contacts", "users.id", "=", "contacts.user_id")
                           .join("orders", "users.id", "=", "orders.user_id")
                           .where("name", "=", "John")
                           .build();
        final var expected =
                "SELECT * FROM users " +
                        "INNER JOIN contacts ON users.id = contacts.user_id " +
                        "INNER JOIN orders ON users.id = orders.user_id WHERE name = 'John'";
        final var actual = queryChain.getSql();
        Assertions.assertEquals(expected.toUpperCase().replaceAll("\\s+", ""),
                                (actual.toUpperCase().replaceAll("\\s+", "")));
    }

    @Test
    void testJoinOnChained() throws Exception {
        final var nativeQuery = new NativeQuery();
        final var queryChain =
                nativeQuery.select("*")
                           .from("users")
                           .join("contacts",
                                 q -> q.on("users.id", "=", "contacts.user_id")
                                       .on("contacts.info_id", "=", "orders.user_id"))
                           .join("orders", "users.id", "=", "orders.user_id")
                           .where("name", "=", "John")
                           .build();
        final var expected =
                "SELECT * FROM users " +
                        "INNER JOIN contacts ON users.id = contacts.user_id AND contacts.info_id = orders.user_id " +
                        "INNER JOIN orders ON users.id = orders.user_id WHERE name = 'John'";
        final var actual = queryChain.getSql();
        Assertions.assertEquals(expected.toUpperCase().replaceAll("\\s+", " "),
                                (actual.toUpperCase().replaceAll("\\s+", " ")));
    }

    @Test
    void testJoinOrOnChained() throws Exception {
        final var nativeQuery = new NativeQuery();
        final var queryChain =
                nativeQuery.select("*")
                           .from("users")
                           .join("contacts",
                                 q -> q.on("users.id", "=", "contacts.user_id")
                                       .or("contacts.info_id", "=", "orders.user_id"))
                           .join("orders", "users.id", "=", "orders.user_id")
                           .where("name", "=", "John")
                           .build();
        final var expected =
                "SELECT * FROM users " +
                        "INNER JOIN contacts ON users.id = contacts.user_id OR contacts.info_id = orders.user_id " +
                        "INNER JOIN orders ON users.id = orders.user_id WHERE name = 'John'";
        final var actual = queryChain.getSql();
        Assertions.assertEquals(expected.toUpperCase().replaceAll("\\s+", " "),
                                (actual.toUpperCase().replaceAll("\\s+", " ")));
    }
}