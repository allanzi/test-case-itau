package com.itau.test.domain.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SequenceEntityTest {

    @Test
    public void testGetAndSetId() {
        SequenceEntity sequenceEntity = new SequenceEntity();
        String id = "testId";
        sequenceEntity.setId(id);
        assertEquals(id, sequenceEntity.getId());
    }

    @Test
    public void testGetAndSetSeq() {
        SequenceEntity sequenceEntity = new SequenceEntity();
        long seq = 12345L;
        sequenceEntity.setSeq(seq);
        assertEquals(seq, sequenceEntity.getSeq());
    }
}