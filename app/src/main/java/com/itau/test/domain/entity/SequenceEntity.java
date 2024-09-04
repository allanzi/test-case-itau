package com.itau.test.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "sequences")
public class SequenceEntity {
    @Id
    private String id;
    private long seq;
}