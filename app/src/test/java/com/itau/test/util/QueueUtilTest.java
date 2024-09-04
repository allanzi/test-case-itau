package com.itau.test.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QueueUtilTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private QueueUtil queueUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStringToObject_Success() throws JsonProcessingException {
        String jsonString = "{\"name\":\"test\"}";
        TestObject expectedObject = new TestObject("test");

        when(objectMapper.readValue(jsonString, TestObject.class)).thenReturn(expectedObject);

        TestObject result = QueueUtil.stringToObject(jsonString, TestObject.class);

        assertEquals(expectedObject, result);
    }

    @Test
    void testObjectToString_Success() throws JsonProcessingException {
        TestObject testObject = new TestObject("test");
        String expectedJsonString = "{\"name\":\"test\"}";

        when(objectMapper.writeValueAsString(testObject)).thenReturn(expectedJsonString);

        String result = QueueUtil.objectToString(testObject);

        assertEquals(expectedJsonString, result);
    }

    static class TestObject {
        private String name;

        public TestObject() {
        }

        public TestObject(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestObject that = (TestObject) o;

            return name != null ? name.equals(that.name) : that.name == null;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }
}