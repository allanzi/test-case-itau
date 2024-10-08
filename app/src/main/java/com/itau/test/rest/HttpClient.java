package com.itau.test.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Component
public class HttpClient extends AbstractRestTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    @Autowired
    public HttpClient(@Value("${connection.timeout:3000}") String connTimeout, @Value("${connection.read.timeout:5000}") String connReadTimeout)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        super(new ConnectionProps(connTimeout, new ConnectionProps.Read(connReadTimeout)));
    }

    protected void restTemplateSetup() {
        try {
            var factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(Integer.parseInt(this.connProps.getTimeout()));
            factory.setReadTimeout(Integer.parseInt(this.connProps.getRead().getTimeout()));

            this.restTemplate.setRequestFactory(factory);
        } catch (Exception e) {
            LOGGER.error("Error creating HttpClient RestTemplate");
            throw e;
        }
    }

    public static HttpHeaders getBasicHttpHeaders() {
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }
}
