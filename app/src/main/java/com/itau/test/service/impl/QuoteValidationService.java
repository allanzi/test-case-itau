package com.itau.test.service.impl;

import static com.itau.test.exception.model.CommonErrorCodesEnum.COMMON_ERROR_422_000;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itau.test.domain.request.QuoteRequest;
import com.itau.test.domain.response.OfferCatalogResponse;
import com.itau.test.exception.model.CommonException;

@Service
public class QuoteValidationService {
    public void validateCoverages(QuoteRequest quoteRequest, OfferCatalogResponse offer) {
        Map<String, BigDecimal> normalizedOfferCoverages = offer.getCoverages().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> normalizeString(entry.getKey()),
                        Map.Entry::getValue));

        for (Map.Entry<String, BigDecimal> entry : quoteRequest.getCoverages().entrySet()) {
            String quoteCoverage = entry.getKey();
            String normalizedQuoteCoverage = normalizeString(quoteCoverage);

            BigDecimal quoteCoverageValue = entry.getValue();
            BigDecimal offerCoverageValue = normalizedOfferCoverages.get(normalizedQuoteCoverage);

            if (!normalizedOfferCoverages.containsKey(normalizedQuoteCoverage)) {
                throw new CommonException(COMMON_ERROR_422_000,
                        String.format("Coverage: %s not found in the offered coverage options.", quoteCoverage));
            }

            if (quoteCoverageValue.compareTo(offerCoverageValue) > 0) {
                throw new CommonException(COMMON_ERROR_422_000, String
                        .format("Coverage: %s, the provided value is higher than the offered coverage", quoteCoverage));
            }
        }
    }

    public void validateAssistances(QuoteRequest quoteRequest, OfferCatalogResponse offer) {
        List<String> quoteAssistances = quoteRequest.getAssistances().stream()
                .map(this::normalizeString)
                .collect(Collectors.toList());
        List<String> offerAssistances = offer.getAssistances().stream()
                .map(this::normalizeString)
                .collect(Collectors.toList());

        for (String assistance : quoteAssistances) {
            if (!offerAssistances.contains(assistance)) {
                throw new CommonException(COMMON_ERROR_422_000, String
                        .format("Assistance %s is not available in the offer", assistance));
            }
        }
    }

    public void validateTotalMonthlyPremiumAmount(QuoteRequest quoteRequest, OfferCatalogResponse offer) {
        BigDecimal totalMonthlyPremiumAmount = quoteRequest.getTotal_monthly_premium_amount();
        BigDecimal minMonthlyPremiumAmount = offer.getMonthlyPremiumAmount().getMinAmount();
        BigDecimal maxMonthlyPremiumAmount = offer.getMonthlyPremiumAmount().getMaxAmount();

        if (totalMonthlyPremiumAmount.compareTo(minMonthlyPremiumAmount) < 0
                || totalMonthlyPremiumAmount.compareTo(maxMonthlyPremiumAmount) > 0) {
            throw new CommonException(COMMON_ERROR_422_000, String
                    .format("Total monthly premium amount must be between %s and %s", minMonthlyPremiumAmount,
                            maxMonthlyPremiumAmount));
        }
    }

    public void validateTotalCoverageAmount(QuoteRequest quoteRequest) {
        BigDecimal totalCoverageAmount = quoteRequest.getCoverages().values().stream()
                .map(value -> new BigDecimal(String.valueOf(value)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalCoverageAmount.compareTo(quoteRequest.getTotal_coverage_amount()) != 0) {
            throw new CommonException(COMMON_ERROR_422_000,
                    "Total coverage amount does not match the sum of the coverages");
        }
    }

    private String normalizeString(String input) {
        return input.toLowerCase().trim();
    }
}
