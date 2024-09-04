package com.itau.test.domain.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InsurancePolicy implements Serializable {

    @NotBlank(message = "insurance_policy_id is mandatory")
    private String insurance_policy_id;

    @NotNull(message = "quote_id is mandatory")
    private long quote_id;

    @NotBlank(message = "created_at is mandatory")
    private String created_at;
}