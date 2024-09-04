package com.itau.test.domain.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quote")
public class QuoteEntity extends BaseEntity {

    @Transient
    public static final String SEQUENCE_NAME = "quote_sequence";

    @Id
    private long id;

    @NotBlank(message = "product_id is mandatory")
    private String product_id;

    @NotBlank(message = "offer_id is mandatory")
    private String offer_id;

    @NotBlank(message = "category is mandatory")
    private String category;

    @NotNull(message = "total_monthly_premium_amount is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "total_monthly_premium_amount must be greater than 0")
    private BigDecimal total_monthly_premium_amount;

    @NotNull(message = "total_coverage_amount is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "total_coverage_amount must be greater than 0")
    private BigDecimal total_coverage_amount;

    @NotEmpty(message = "coverages is mandatory")
    private Map<String, BigDecimal> coverages;

    @NotEmpty(message = "assistances is mandatory")
    private List<String> assistances;

    @Valid
    @NotNull(message = "customer is mandatory")
    private Customer customer;

    private String policy_id;
    private String policty_created_at;
}