package com.itau.test.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.itau.test.domain.entity.SequenceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.FindAndModifyOptions;

public class SequenceGeneratorServiceTest {

    @Mock
    private MongoOperations mongoOperations;

    @InjectMocks
    private SequenceGeneratorService sequenceGeneratorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateSequence() {
        SequenceEntity sequenceEntity = new SequenceEntity();
        sequenceEntity.setSeq(2);

        when(mongoOperations.findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                eq(SequenceEntity.class)
        )).thenReturn(sequenceEntity);

        long seq = sequenceGeneratorService.generateSequence("testSeq");
        assertEquals(2, seq);
    }

    @Test
    public void testGenerateSequenceWhenNull() {
        when(mongoOperations.findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                eq(SequenceEntity.class)
        )).thenReturn(null);

        long seq = sequenceGeneratorService.generateSequence("testSeq");
        assertEquals(1, seq);
    }
}