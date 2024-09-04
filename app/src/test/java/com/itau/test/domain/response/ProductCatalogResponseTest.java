package com.itau.test.domain.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.util.Arrays;

public class ProductCatalogResponseTest {

    @Test
    public void testBuilder() {
        ProductCatalogResponse response = ProductCatalogResponse.builder()
            .id("123")
            .name("Product Name")
            .createdAt(LocalDateTime.now())
            .active(true)
            .offers(Arrays.asList("Offer1", "Offer2"))
            .build();

        assertNotNull(response);
        assertEquals("123", response.getId());
        assertEquals("Product Name", response.getName());
        assertNotNull(response.getCreatedAt());
        assertTrue(response.getActive());
        assertEquals(2, response.getOffers().size());
    }

    @Test
    public void testGettersAndSetters() {
        ProductCatalogResponse response = new ProductCatalogResponse();
        response.setId("123");
        response.setName("Product Name");
        response.setCreatedAt(LocalDateTime.now());
        response.setActive(true);
        response.setOffers(Arrays.asList("Offer1", "Offer2"));

        assertEquals("123", response.getId());
        assertEquals("Product Name", response.getName());
        assertNotNull(response.getCreatedAt());
        assertTrue(response.getActive());
        assertEquals(2, response.getOffers().size());
    }

    @Test
    public void testToString() {
        ProductCatalogResponse response = ProductCatalogResponse.builder()
            .id("123")
            .name("Product Name")
            .createdAt(LocalDateTime.now())
            .active(true)
            .offers(Arrays.asList("Offer1", "Offer2"))
            .build();

        String expected = "ProductCatalogResponse(id=123, name=Product Name, createdAt=" + response.getCreatedAt() + ", active=true, offers=[Offer1, Offer2])";
        assertEquals(expected, response.toString());
    }

    @Test
    public void testJsonSerialization() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ProductCatalogResponse response = ProductCatalogResponse.builder()
            .id("123")
            .name("Product Name")
            .createdAt(LocalDateTime.of(2023, 10, 1, 12, 0))
            .active(true)
            .offers(Arrays.asList("Offer1", "Offer2"))
            .build();

        String json = mapper.writeValueAsString(response);
        assertTrue(json.contains("\"id\":\"123\""));
        assertTrue(json.contains("\"name\":\"Product Name\""));
        assertTrue(json.contains("\"created_at\":\"2023-10-01T12:00:00\""));
        assertTrue(json.contains("\"active\":true"));
        assertTrue(json.contains("\"offers\":[\"Offer1\",\"Offer2\"]"));
    }

    @Test
    public void testJsonDeserialization() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String json = "{\"id\":\"123\",\"name\":\"Product Name\",\"created_at\":\"2023-10-01T12:00:00\",\"active\":true,\"offers\":[\"Offer1\",\"Offer2\"]}";

        ProductCatalogResponse response = mapper.readValue(json, ProductCatalogResponse.class);
        assertEquals("123", response.getId());
        assertEquals("Product Name", response.getName());
        assertEquals(LocalDateTime.of(2023, 10, 1, 12, 0), response.getCreatedAt());
        assertTrue(response.getActive());
        assertEquals(2, response.getOffers().size());
    }
}