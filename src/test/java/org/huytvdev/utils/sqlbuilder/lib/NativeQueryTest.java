package org.huytvdev.utils.sqlbuilder.lib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NativeQueryTest {
    @Test
    void testSelect() throws Exception {
        final var nativeQuery = new NativeQuery();
        final var queryChain = nativeQuery.select("id", "name")
                                     .build();
        System.out.println("queryChain.getSql() = " + queryChain.getSql());
        Assertions.assertEquals("SELECT id , name".replaceAll("\\s+", ""), queryChain.getSql()
                                                                                     .replaceAll("\\s+", ""));
    }

}