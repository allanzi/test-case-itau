package com.itau.test.domain.response;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OfferCatalogResponseTest {

    @Test
    public void testOfferCatalogResponseBuilder() {
        OfferCatalogResponse.MonthlyPremiumAmount monthlyPremiumAmount = OfferCatalogResponse.MonthlyPremiumAmount.builder()
                .maxAmount(new BigDecimal("100.00"))
                .minAmount(new BigDecimal("50.00"))
                .suggestedAmount(new BigDecimal("75.00"))
                .build();

        OfferCatalogResponse offerCatalogResponse = OfferCatalogResponse.builder()
                .id("123")
                .productId("456")
                .name("Test Product")
                .createdAt(LocalDateTime.now())
                .active(true)
                .coverages(Map.of("coverage1", new BigDecimal("1000.00")))
                .assistances(List.of("assistance1", "assistance2"))
                .monthlyPremiumAmount(monthlyPremiumAmount)
                .build();

        assertNotNull(offerCatalogResponse);
        assertEquals("123", offerCatalogResponse.getId());
        assertEquals("456", offerCatalogResponse.getProductId());
        assertEquals("Test Product", offerCatalogResponse.getName());
        assertNotNull(offerCatalogResponse.getCreatedAt());
        assertTrue(offerCatalogResponse.getActive());
        assertEquals(1, offerCatalogResponse.getCoverages().size());
        assertEquals(2, offerCatalogResponse.getAssistances().size());
        assertNotNull(offerCatalogResponse.getMonthlyPremiumAmount());
        assertEquals(new BigDecimal("100.00"), offerCatalogResponse.getMonthlyPremiumAmount().getMaxAmount());
        assertEquals(new BigDecimal("50.00"), offerCatalogResponse.getMonthlyPremiumAmount().getMinAmount());
        assertEquals(new BigDecimal("75.00"), offerCatalogResponse.getMonthlyPremiumAmount().getSuggestedAmount());
    }

    @Test
    public void testOfferCatalogResponseSettersAndGetters() {
        OfferCatalogResponse offerCatalogResponse = new OfferCatalogResponse();
        offerCatalogResponse.setId("123");
        offerCatalogResponse.setProductId("456");
        offerCatalogResponse.setName("Test Product");
        offerCatalogResponse.setCreatedAt(LocalDateTime.now());
        offerCatalogResponse.setActive(true);
        offerCatalogResponse.setCoverages(Map.of("coverage1", new BigDecimal("1000.00")));
        offerCatalogResponse.setAssistances(List.of("assistance1", "assistance2"));

        OfferCatalogResponse.MonthlyPremiumAmount monthlyPremiumAmount = new OfferCatalogResponse.MonthlyPremiumAmount();
        monthlyPremiumAmount.setMaxAmount(new BigDecimal("100.00"));
        monthlyPremiumAmount.setMinAmount(new BigDecimal("50.00"));
        monthlyPremiumAmount.setSuggestedAmount(new BigDecimal("75.00"));
        offerCatalogResponse.setMonthlyPremiumAmount(monthlyPremiumAmount);

        assertEquals("123", offerCatalogResponse.getId());
        assertEquals("456", offerCatalogResponse.getProductId());
        assertEquals("Test Product", offerCatalogResponse.getName());
        assertNotNull(offerCatalogResponse.getCreatedAt());
        assertTrue(offerCatalogResponse.getActive());
        assertEquals(1, offerCatalogResponse.getCoverages().size());
        assertEquals(2, offerCatalogResponse.getAssistances().size());
        assertNotNull(offerCatalogResponse.getMonthlyPremiumAmount());
        assertEquals(new BigDecimal("100.00"), offerCatalogResponse.getMonthlyPremiumAmount().getMaxAmount());
        assertEquals(new BigDecimal("50.00"), offerCatalogResponse.getMonthlyPremiumAmount().getMinAmount());
        assertEquals(new BigDecimal("75.00"), offerCatalogResponse.getMonthlyPremiumAmount().getSuggestedAmount());
    }

    @Test
    public void testToString() {
        OfferCatalogResponse offerCatalogResponse = OfferCatalogResponse.builder()
                .id("123")
                .productId("456")
                .name("Test Product")
                .createdAt(LocalDateTime.now())
                .active(true)
                .coverages(Map.of("coverage1", new BigDecimal("1000.00")))
                .assistances(List.of("assistance1", "assistance2"))
                .monthlyPremiumAmount(OfferCatalogResponse.MonthlyPremiumAmount.builder()
                        .maxAmount(new BigDecimal("100.00"))
                        .minAmount(new BigDecimal("50.00"))
                        .suggestedAmount(new BigDecimal("75.00"))
                        .build())
                .build();

        String toString = offerCatalogResponse.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("123"));
        assertTrue(toString.contains("456"));
        assertTrue(toString.contains("Test Product"));
        assertTrue(toString.contains("true"));
        assertTrue(toString.contains("coverage1"));
        assertTrue(toString.contains("assistance1"));
        assertTrue(toString.contains("assistance2"));
        assertTrue(toString.contains("100.00"));
        assertTrue(toString.contains("50.00"));
        assertTrue(toString.contains("75.00"));
    }
}