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
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("product_id")
    private String product_id;

    @JsonProperty("offer_id")
    private String offer_id;

    @JsonProperty("category")
    private String category;

    @JsonProperty("total_monthly_premium_amount")
    private BigDecimal total_monthly_premium_amount;

    @JsonProperty("total_coverage_amount")
    private BigDecimal total_coverage_amount;

    @JsonProperty("coverages")
    private Map<String, BigDecimal> coverages;

    @JsonProperty("assistances")
    private List<String> assistances;

    @JsonProperty("customer")
    private CustomerResponse customer;

    @Builder.Default
    private String policy_id = null;

    @Builder.Default
    private String policty_created_at = null;

    @ToString.Include(name = "total_monthly_premium_amount", rank = 1)
    public String maskTotalMonthlyPremiumAmount() {
        if (Objects.nonNull(total_monthly_premium_amount)) {
            return "****";
        }
        return null;
    }
}