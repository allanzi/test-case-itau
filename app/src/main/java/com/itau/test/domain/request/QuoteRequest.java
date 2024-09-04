package com.itau.test.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class QuoteRequest {

    @NotBlank(message = "product_id is mandatory")
    private String product_id;

    @NotBlank(message = "offer_id is mandatory")
    private String offer_id;

    @NotBlank(message = "category is mandatory")
    private String category;

    @NotNull(message = "total_monthly_premium_amount is mandatory")
    private BigDecimal total_monthly_premium_amount;

    @NotNull(message = "total_coverage_amount is mandatory")
    private BigDecimal total_coverage_amount;

    @NotNull(message = "coverages is mandatory")
    private Map<String, BigDecimal> coverages;

    @NotNull(message = "assistances is mandatory")
    private List<String> assistances;

    @Valid
    @NotNull(message = "customer is mandatory")
    private Customer customer;

    @Getter
    @Setter
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Customer {

        @NotBlank(message = "document_number is mandatory")
        private String document_number;

        @NotBlank(message = "name is mandatory")
        private String name;

        @NotBlank(message = "type is mandatory")
        private String type;

        @NotBlank(message = "gender is mandatory")
        private String gender;

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "date_of_birth is mandatory")
        private LocalDate date_of_birth;

        @NotBlank(message = "email is mandatory")
        @Pattern(regexp = ".+@.+\\..+", message = "email should be a valid email")
        private String email;

        @NotNull(message = "phone_number is mandatory")
        private Long phone_number;
    }
}