package com.itau.test.mapper;

import com.itau.test.domain.entity.QuoteEntity;
import com.itau.test.domain.entity.Customer;
import com.itau.test.domain.request.QuoteRequest;
import com.itau.test.domain.response.QuoteResponse;
import com.itau.test.domain.response.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QuoteMapperTest {

    private final QuoteMapper mapper = Mappers.getMapper(QuoteMapper.class);

    @Test
    void testEntityToResponse() {
        QuoteEntity entity = new QuoteEntity();
        Customer customer = new Customer();
        customer.setDate_of_birth(LocalDate.of(1990, 1, 1));
        customer.setDocument_number("123456789");
        customer.setPhone_number(987654321L);
        entity.setCustomer(customer);

        QuoteResponse response = mapper.entityToResponse(entity);

        assertNotNull(response);
        assertEquals(LocalDate.of(1990, 1, 1), response.getCustomer().getDate_of_birth());
        assertEquals("123456789", response.getCustomer().getDocument_number());
        assertEquals(987654321L, response.getCustomer().getPhone_number());
    }

    @Test
    void testEntitiesToResponses() {
        QuoteEntity entity = new QuoteEntity();
        Customer customer = new Customer();
        customer.setDate_of_birth(LocalDate.of(1990, 1, 1));
        customer.setDocument_number("123456789");
        customer.setPhone_number(987654321L);
        entity.setCustomer(customer);

        List<QuoteResponse> responses = mapper.entitiesToResponses(Collections.singletonList(entity));

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(LocalDate.of(1990, 1, 1), responses.get(0).getCustomer().getDate_of_birth());
        assertEquals("123456789", responses.get(0).getCustomer().getDocument_number());
        assertEquals(987654321L, responses.get(0).getCustomer().getPhone_number());
    }

    @Test
    void testCustomerToCustomerResponse() {
        Customer customer = new Customer();
        customer.setDate_of_birth(LocalDate.of(1990, 1, 1));
        customer.setDocument_number("123456789");
        customer.setPhone_number(987654321L);

        CustomerResponse response = mapper.customerToCustomerResponse(customer);

        assertNotNull(response);
        assertEquals(LocalDate.of(1990, 1, 1), response.getDate_of_birth());
        assertEquals("123456789", response.getDocument_number());
        assertEquals(987654321L, response.getPhone_number());
    }

    @Test
    void testRequestToEntity() {
        QuoteRequest quoteRequest = new QuoteRequest();
        QuoteRequest.Customer customer = new QuoteRequest.Customer();
        customer.setDate_of_birth(LocalDate.of(1990, 1, 1));
        customer.setDocument_number("123456789");
        customer.setPhone_number(987654321L);
        quoteRequest.setCustomer(customer);

        QuoteEntity entity = mapper.requestToEntity(quoteRequest);

        assertNotNull(entity);
        assertEquals(LocalDate.of(1990, 1, 1), entity.getCustomer().getDate_of_birth());
        assertEquals("123456789", entity.getCustomer().getDocument_number());
        assertEquals(987654321L, entity.getCustomer().getPhone_number());
    }
}