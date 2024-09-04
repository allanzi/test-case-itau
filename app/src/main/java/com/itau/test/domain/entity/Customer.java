package com.itau.test.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {

    @NotBlank(message = "document_number is mandatory")
    private String document_number;

    @NotBlank(message = "name is mandatory")
    private String name;

    @NotBlank(message = "type is mandatory")
    private String type;

    @NotNull(message = "gender is mandatory")
    private String gender;

    @NotNull(message = "date_of_birth is mandatory")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date_of_birth;

    @NotBlank(message = "email is mandatory")
    private String email;

    @NotNull(message = "phone_number is mandatory")
    private Long phone_number;
}