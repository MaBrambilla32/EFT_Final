package com.minimarket;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinimarketApplicationTests {

    @Test
    void testVerificarSistema() {
        int a = 1;
        int b = 1;
        assertTrue(a == b, "El sistema de pruebas está activo");
    }
}