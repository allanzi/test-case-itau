package com.itau.test.constant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BusinessConstantTest {

    @Test
    void testDefaultConnectionTimeout() {
        Assertions.assertEquals("3000", BusinessConstant.DEFAULT_CONNECTION_TIMEOUT);
    }

    @Test
    void testDefaultSocketTimeout() {
        Assertions.assertEquals("5000", BusinessConstant.DEFAULT_SOCKET_TIMEOUT);
    }

    @Test
    void testQuoteConstant() {
        Assertions.assertEquals("/quote", BusinessConstant.QUOTE);
    }

    @Test
    void testClassIsFinal() {
        Assertions.assertTrue(java.lang.reflect.Modifier.isFinal(BusinessConstant.class.getModifiers()));
    }

    @Test
    void testConstructorIsPrivate() throws NoSuchMethodException {
        java.lang.reflect.Constructor<BusinessConstant> constructor = BusinessConstant.class.getDeclaredConstructor();
        Assertions.assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
    }
}