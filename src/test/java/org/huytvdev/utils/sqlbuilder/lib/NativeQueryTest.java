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

}