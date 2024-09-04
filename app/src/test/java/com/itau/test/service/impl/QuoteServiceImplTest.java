package com.itau.test.service.impl;

import static com.itau.test.exception.model.CommonErrorCodesEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.itau.test.domain.entity.InsurancePolicy;
import com.itau.test.domain.entity.QuoteEntity;
import com.itau.test.domain.request.QuoteRequest;
import com.itau.test.domain.response.OfferCatalogResponse;
import com.itau.test.domain.response.ProductCatalogResponse;
import com.itau.test.domain.response.QuoteResponse;
import com.itau.test.exception.model.CommonException;
import com.itau.test.mapper.QuoteMapper;
import com.itau.test.repository.QuoteRepository;
import com.itau.test.rest.HttpClient;
import com.itau.test.util.CacheUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class QuoteServiceImplTest {

    @Mock
    private QuoteValidationService quoteValidationService;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @Mock
    private CacheUtil cacheUtil;

    @Mock
    private QuoteMapper mapper;

    @Mock
    private QuoteRepository repository;

    @Mock
    private HttpClient httpClient;

    @InjectMocks
    private QuoteServiceImpl quoteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuotes_Success() {
        QuoteEntity quoteEntity1 = new QuoteEntity();
        QuoteEntity quoteEntity2 = new QuoteEntity();
        List<QuoteEntity> quoteEntities = Arrays.asList(quoteEntity1, quoteEntity2);

        QuoteResponse quoteResponse1 = new QuoteResponse();
        QuoteResponse quoteResponse2 = new QuoteResponse();
        List<QuoteResponse> quoteResponses = Arrays.asList(quoteResponse1, quoteResponse2);

        when(repository.findAll()).thenReturn(quoteEntities);
        when(mapper.entitiesToResponses(quoteEntities)).thenReturn(quoteResponses);

        List<QuoteResponse> result = quoteService.getQuotes();

        assertEquals(quoteResponses, result);
    }

    @Test
    void testUpdatePolicy_Success() {
        long quoteId = 1L;
        String policyId = "policy123";
        String createdAt = "2023-10-01";

        InsurancePolicy policy = new InsurancePolicy();
        policy.setQuote_id(quoteId);
        policy.setInsurance_policy_id(policyId);
        policy.setCreated_at(createdAt);

        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setId(quoteId);

        when(repository.findOne(quoteId)).thenReturn(Optional.of(quoteEntity));

        quoteService.updatePolicy(policy);

        assertEquals(policyId, quoteEntity.getPolicy_id());
        assertEquals(createdAt, quoteEntity.getPolicty_created_at());
    }

    @Test
    void testUpdatePolicy_QuoteNotFound() {
        long quoteId = 1L;
        String policyId = "policy123";
        String createdAt = "2023-10-01";

        InsurancePolicy policy = new InsurancePolicy();
        policy.setQuote_id(quoteId);
        policy.setInsurance_policy_id(policyId);
        policy.setCreated_at(createdAt);

        when(repository.findOne(quoteId)).thenReturn(Optional.empty());

        CommonException exception = assertThrows(CommonException.class, () -> {
            quoteService.updatePolicy(policy);
        });

        assertEquals(COMMON_ERROR_404_001, exception.getErrorCode());
    }

    @Test
    void testRequestQuote_Success() {
        QuoteRequest quoteRequest = new QuoteRequest();
        quoteRequest.setProduct_id("product123");
        quoteRequest.setOffer_id("offer123");

        ProductCatalogResponse productCatalogResponse = new ProductCatalogResponse();
        productCatalogResponse.setActive(true);

        OfferCatalogResponse offerCatalogResponse = new OfferCatalogResponse();
        offerCatalogResponse.setActive(true);

        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setId(1L);

        QuoteResponse quoteResponse = new QuoteResponse();

        when(cacheUtil.getCachedResponse("products", "product123", ProductCatalogResponse.class))
                .thenReturn(productCatalogResponse);
        when(cacheUtil.getCachedResponse("offers", "offer123", OfferCatalogResponse.class))
                .thenReturn(offerCatalogResponse);
        when(mapper.requestToEntity(quoteRequest)).thenReturn(quoteEntity);
        when(sequenceGeneratorService.generateSequence(QuoteEntity.SEQUENCE_NAME)).thenReturn(1L);
        when(repository.insert(quoteEntity)).thenReturn(quoteEntity);
        when(mapper.entityToResponse(quoteEntity)).thenReturn(quoteResponse);

        QuoteResponse result = quoteService.requestQuote(quoteRequest);

        assertEquals(quoteResponse, result);
    }

    @Test
    void testRequestQuote_InvalidProduct() {
        QuoteRequest quoteRequest = new QuoteRequest();
        quoteRequest.setProduct_id("invalidProduct");

        when(cacheUtil.getCachedResponse("CACHE_KEY_PRODUCTS", "invalidProduct", ProductCatalogResponse.class))
                .thenReturn(null);

        CommonException exception = assertThrows(CommonException.class, () -> {
            quoteService.requestQuote(quoteRequest);
        });

        assertEquals(COMMON_ERROR_422_001, exception.getErrorCode());
    }

    @Test
    void testRequestQuote_InvalidOffer() {
        QuoteRequest quoteRequest = new QuoteRequest();
        quoteRequest.setProduct_id("product123");
        quoteRequest.setOffer_id("invalidOffer");

        ProductCatalogResponse productCatalogResponse = new ProductCatalogResponse();
        productCatalogResponse.setActive(true);

        when(cacheUtil.getCachedResponse("CACHE_KEY_PRODUCTS", "product123", ProductCatalogResponse.class))
                .thenReturn(productCatalogResponse);
        when(cacheUtil.getCachedResponse("CACHE_KEY_OFFERS", "invalidOffer", OfferCatalogResponse.class))
                .thenReturn(null);

        CommonException exception = assertThrows(CommonException.class, () -> {
            quoteService.requestQuote(quoteRequest);
        });

        assertEquals(COMMON_ERROR_422_001, exception.getErrorCode());
    }
}