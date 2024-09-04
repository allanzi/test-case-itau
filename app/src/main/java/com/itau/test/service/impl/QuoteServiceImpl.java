package com.itau.test.service.impl;

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
import com.itau.test.service.QuoteService;
import com.itau.test.util.CacheUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.itau.test.exception.model.CommonErrorCodesEnum.*;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    @Value("${catalog.url}")
    private String catalogUrl;

    private final QuoteValidationService quoteValidationService;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final CacheUtil cacheUtil;
    private final QuoteMapper mapper;
    private final QuoteRepository repository;
    private final HttpClient httpClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteServiceImpl.class);

    private static final String CACHE_KEY_PRODUCTS = "products";
    private static final String CACHE_KEY_OFFERS = "offers";
    private static final String ERROR_FETCHING_PRODUCT = "Error fetching product: {}. Error message: {}";
    private static final String ERROR_FETCHING_OFFER = "Error fetching offer: {}. Error message: {}";

    @Override
    @Transactional(readOnly = true)
    public List<QuoteResponse> getQuotes() {
        List<QuoteEntity> quotes = repository.findAll();
        return mapper.entitiesToResponses(quotes);
    }

    @Override
    public QuoteResponse requestQuote(QuoteRequest quoteRequest) {
        if (!isValidProduct(quoteRequest.getProduct_id())) {
            throw new CommonException(COMMON_ERROR_422_001, "Product");
        }

        OfferCatalogResponse offer = getOffer(quoteRequest.getOffer_id());
        if (offer == null) {
            throw new CommonException(COMMON_ERROR_422_001, "Offer");
        }

        quoteValidationService.validateCoverages(quoteRequest, offer);
        quoteValidationService.validateAssistances(quoteRequest, offer);
        quoteValidationService.validateTotalMonthlyPremiumAmount(quoteRequest, offer);
        quoteValidationService.validateTotalCoverageAmount(quoteRequest);

        QuoteEntity quote = mapper.requestToEntity(quoteRequest);
        quote.setId(sequenceGeneratorService.generateSequence(QuoteEntity.SEQUENCE_NAME));
        quote = repository.insert(quote);

        return mapper.entityToResponse(quote);
    }

    @Override
    public void updatePolicy(InsurancePolicy policy) {
        long quoteId = policy.getQuote_id();
        String policyId = policy.getInsurance_policy_id();
        String createdAt = policy.getCreated_at();

        QuoteEntity quote = repository.findOne(quoteId)
                .orElseThrow(() -> new CommonException(COMMON_ERROR_404_001, "Quote", quoteId));

        quote.setPolicy_id(policyId);
        quote.setPolicty_created_at(createdAt);
        repository.save(quote);
    }

    private boolean isValidProduct(String productId) {
        try {
            ProductCatalogResponse product = cacheUtil.getCachedResponse(CACHE_KEY_PRODUCTS, productId,
                    ProductCatalogResponse.class);
            if (product != null) {
                return product.getActive();
            }

            String productUri = String.format("%s/products/%s", catalogUrl, productId);
            ResponseEntity<ProductCatalogResponse> response = httpClient.getRestTemplate()
                    .exchange(
                            productUri,
                            HttpMethod.GET,
                            new HttpEntity<>(HttpClient.getBasicHttpHeaders()),
                            new ParameterizedTypeReference<>() {
                            });

            if (!response.hasBody()) {
                return false;
            }

            product = response.getBody();
            cacheUtil.cacheResponse(CACHE_KEY_PRODUCTS, productId, product);

            return product.getActive();
        } catch (Exception e) {
            LOGGER.warn(ERROR_FETCHING_PRODUCT, productId, e.getMessage());
            return false;
        }
    }

    private OfferCatalogResponse getOffer(String offerId) {
        try {
            OfferCatalogResponse offer = cacheUtil.getCachedResponse(CACHE_KEY_OFFERS, offerId, OfferCatalogResponse.class);
            if (offer != null) {
                return offer.getActive() ? offer : null;
            }

            String offerUri = String.format("%s/offers/%s", catalogUrl, offerId);
            ResponseEntity<OfferCatalogResponse> response = httpClient.getRestTemplate()
                    .exchange(
                            offerUri,
                            HttpMethod.GET,
                            new HttpEntity<>(HttpClient.getBasicHttpHeaders()),
                            new ParameterizedTypeReference<>() {
                            });

            if (!response.hasBody()) {
                return null;
            }

            offer = response.getBody();
            cacheUtil.cacheResponse(CACHE_KEY_OFFERS, offerId, offer);

            return offer.getActive() ? offer : null;
        } catch (Exception e) {
            LOGGER.warn(ERROR_FETCHING_OFFER, offerId, e.getMessage());
            return null;
        }
    }
}