package org.huytvdev.utils.sqlbuilder;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Query.table("employee")
             .select("id", "name")
             .where("id", "=", "1")
                .build();

    }
}