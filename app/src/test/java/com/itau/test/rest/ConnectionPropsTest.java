package com.itau.test.rest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConnectionPropsTest {

    @Test
    public void testDefaultConstructor() {
        ConnectionProps.Read read = new ConnectionProps.Read();
        assertEquals(null, read.getTimeout());
    }

    @Test
    public void testParameterizedConstructor() {
        ConnectionProps.Read read = new ConnectionProps.Read("5000");
        assertEquals("5000", read.getTimeout());
    }

    @Test
    public void testGettersAndSetters() {
        ConnectionProps.Read read = new ConnectionProps.Read();
        read.setTimeout("3000");
        assertEquals("3000", read.getTimeout());
    }
}