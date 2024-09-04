package com.itau.test.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferCatalogResponse {

    private String id;

    @JsonProperty("product_id")
    private String productId;

    private String name;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    private Boolean active;

    private Map<String, BigDecimal> coverages;

    private List<String> assistances;

    @JsonProperty("monthly_premium_amount")
    private MonthlyPremiumAmount monthlyPremiumAmount;

    @Getter
    @Setter
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MonthlyPremiumAmount {
        @JsonProperty("max_amount")
        private BigDecimal maxAmount;

        @JsonProperty("min_amount")
        private BigDecimal minAmount;

        @JsonProperty("suggested_amount")
        private BigDecimal suggestedAmount;
    }
}