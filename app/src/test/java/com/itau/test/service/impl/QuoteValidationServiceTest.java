package com.itau.test.service.impl;

import com.itau.test.domain.request.QuoteRequest;
import com.itau.test.domain.response.OfferCatalogResponse;
import com.itau.test.exception.model.CommonException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class QuoteValidationServiceTest {

    private QuoteValidationService quoteValidationService;
    private QuoteRequest mockQuoteRequest;
    private OfferCatalogResponse mockOffer;

    @BeforeEach
    public void setUp() {
        quoteValidationService = new QuoteValidationService();
        mockQuoteRequest = Mockito.mock(QuoteRequest.class);
        mockOffer = Mockito.mock(OfferCatalogResponse.class);
    }

    @Test
    public void testValidateCoverages_ThrowsException_WhenCoverageNotFound() {
        Map<String, BigDecimal> quoteCoverages = new HashMap<>();
        quoteCoverages.put("Coverage1", BigDecimal.valueOf(100));
        when(mockQuoteRequest.getCoverages()).thenReturn(quoteCoverages);

        Map<String, BigDecimal> offerCoverages = new HashMap<>();
        offerCoverages.put("coverage2", BigDecimal.valueOf(100));
        when(mockOffer.getCoverages()).thenReturn(offerCoverages);

        assertThrows(CommonException.class, () -> {
            quoteValidationService.validateCoverages(mockQuoteRequest, mockOffer);
        });
    }

    @Test
    public void testValidateCoverages_ThrowsException_WhenCoverageValueExceeds() {
        Map<String, BigDecimal> quoteCoverages = new HashMap<>();
        quoteCoverages.put("Coverage1", BigDecimal.valueOf(200));
        when(mockQuoteRequest.getCoverages()).thenReturn(quoteCoverages);

        Map<String, BigDecimal> offerCoverages = new HashMap<>();
        offerCoverages.put("coverage1", BigDecimal.valueOf(100));
        when(mockOffer.getCoverages()).thenReturn(offerCoverages);

        assertThrows(CommonException.class, () -> {
            quoteValidationService.validateCoverages(mockQuoteRequest, mockOffer);
        });
    }

    @Test
    public void testValidateAssistances_ThrowsException_WhenAssistanceNotFound() {
        List<String> quoteAssistances = List.of("Assistance1");
        when(mockQuoteRequest.getAssistances()).thenReturn(quoteAssistances);

        List<String> offerAssistances = List.of("assistance2");
        when(mockOffer.getAssistances()).thenReturn(offerAssistances);

        assertThrows(CommonException.class, () -> {
            quoteValidationService.validateAssistances(mockQuoteRequest, mockOffer);
        });
    }

    @Test
    public void testValidateTotalMonthlyPremiumAmount_ThrowsException_WhenAmountOutOfRange() {
        when(mockQuoteRequest.getTotal_monthly_premium_amount()).thenReturn(BigDecimal.valueOf(150));

        OfferCatalogResponse.MonthlyPremiumAmount monthlyPremiumAmount = new OfferCatalogResponse.MonthlyPremiumAmount();
        monthlyPremiumAmount.setMinAmount(BigDecimal.valueOf(100));
        monthlyPremiumAmount.setMaxAmount(BigDecimal.valueOf(140));
        when(mockOffer.getMonthlyPremiumAmount()).thenReturn(monthlyPremiumAmount);

        assertThrows(CommonException.class, () -> {
            quoteValidationService.validateTotalMonthlyPremiumAmount(mockQuoteRequest, mockOffer);
        });
    }

    @Test
    public void testValidateTotalCoverageAmount_ThrowsException_WhenAmountMismatch() {
        Map<String, BigDecimal> coverages = new HashMap<>();
        coverages.put("Coverage1", BigDecimal.valueOf(100));
        coverages.put("Coverage2", BigDecimal.valueOf(200));
        when(mockQuoteRequest.getCoverages()).thenReturn(coverages);
        when(mockQuoteRequest.getTotal_coverage_amount()).thenReturn(BigDecimal.valueOf(250));

        assertThrows(CommonException.class, () -> {
            quoteValidationService.validateTotalCoverageAmount(mockQuoteRequest);
        });
    }
}