package com.biblioteca.Biblioteca.utils;

import java.util.UUID;

public class UUIDGenerator {

    public static String getId() {
        return UUID.randomUUID().toString();
    }

}
