package com.itau.test.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class HttpClientTest {

    private HttpClient httpClient;
    private ConnectionProps mockConnectionProps;

    @BeforeEach
    public void setUp() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        mockConnectionProps = Mockito.mock(ConnectionProps.class);
        ConnectionProps.Read mockRead = Mockito.mock(ConnectionProps.Read.class);

        when(mockConnectionProps.getTimeout()).thenReturn("3000");
        when(mockRead.getTimeout()).thenReturn("5000");
        when(mockConnectionProps.getRead()).thenReturn(mockRead);

        httpClient = new HttpClient("3000", "5000") {
            @Override
            protected void restTemplateSetup() {
                try {
                    var factory = new SimpleClientHttpRequestFactory();
                    factory.setConnectTimeout(Integer.parseInt(this.connProps.getTimeout()));
                    factory.setReadTimeout(Integer.parseInt(this.connProps.getRead().getTimeout()));
                    this.restTemplate.setRequestFactory(factory);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Test
    public void testRestTemplateSetup() throws NoSuchFieldException, IllegalAccessException {
        RestTemplate restTemplate = httpClient.getRestTemplate();
        assertNotNull(restTemplate, "RestTemplate should not be null");

        SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();

        Field connectTimeoutField = SimpleClientHttpRequestFactory.class.getDeclaredField("connectTimeout");
        connectTimeoutField.setAccessible(true);
        int connectTimeout = (int) connectTimeoutField.get(factory);

        Field readTimeoutField = SimpleClientHttpRequestFactory.class.getDeclaredField("readTimeout");
        readTimeoutField.setAccessible(true);
        int readTimeout = (int) readTimeoutField.get(factory);

        assertEquals(3000, connectTimeout, "Connect timeout should be 3000");
        assertEquals(5000, readTimeout, "Read timeout should be 5000");
    }

    @Test
    public void testGetBasicHttpHeaders() {
        HttpHeaders headers = HttpClient.getBasicHttpHeaders();
        assertNotNull(headers, "HttpHeaders should not be null");
        assertEquals(MediaType.APPLICATION_JSON, headers.getContentType(), "Content type should be application/json");
    }
}