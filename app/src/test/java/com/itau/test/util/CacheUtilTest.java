package com.itau.test.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

class CacheUtilTest {

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @Mock
    private Cache.ValueWrapper valueWrapper;

    @Mock
    private Logger logger;

    @InjectMocks
    private CacheUtil cacheUtil;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCachedResponse_CacheHit() throws JsonProcessingException {
        String cacheKey = "testCache";
        String id = "123";
        TestResponse expectedResponse = new TestResponse("test");

        String json = objectMapper.writeValueAsString(expectedResponse);
        when(cacheManager.getCache(cacheKey)).thenReturn(cache);
        when(cache.get(id, String.class)).thenReturn(json);

        TestResponse result = cacheUtil.getCachedResponse(cacheKey, id, TestResponse.class);

        assertEquals(expectedResponse, result);
    }

    @Test
    void testGetCachedResponse_CacheMiss() {
        String cacheKey = "testCache";
        String id = "123";

        when(cacheManager.getCache(cacheKey)).thenReturn(cache);
        when(cache.get(id, String.class)).thenReturn(null);

        TestResponse result = cacheUtil.getCachedResponse(cacheKey, id, TestResponse.class);

        assertNull(result);
    }

    static class TestResponse {
        private String value;

        public TestResponse() {
        }

        public TestResponse(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            TestResponse that = (TestResponse) o;

            return value != null ? value.equals(that.value) : that.value == null;
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }
    }

    @Test
    void testCacheResponse_Success() throws JsonProcessingException {
        String cacheKey = "testCache";
        String id = "123";
        TestResponse response = new TestResponse("test");

        String json = objectMapper.writeValueAsString(response);
        when(cacheManager.getCache(cacheKey)).thenReturn(cache);

        cacheUtil.cacheResponse(cacheKey, id, response);

        verify(cache).put(id, json);
    }

    @Test
    void testCacheResponse_CacheNull() {
        String cacheKey = "testCache";
        String id = "123";
        TestResponse response = new TestResponse("test");

        when(cacheManager.getCache(cacheKey)).thenReturn(null);

        cacheUtil.cacheResponse(cacheKey, id, response);

        verify(cache, never()).put(anyString(), anyString());
    }
}