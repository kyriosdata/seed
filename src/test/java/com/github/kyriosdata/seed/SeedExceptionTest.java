package com.github.kyriosdata.seed;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SeedExceptionTest {

    @Test
    public void criacaoDeInstancia() {
        Exception exp = new SeedException("situação excepcional");
        assertThrows(SeedException.class, () -> {throw exp;});
    }
}

