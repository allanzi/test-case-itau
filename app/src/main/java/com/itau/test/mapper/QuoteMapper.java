package com.itau.test.mapper;

import com.itau.test.domain.entity.QuoteEntity;
import com.itau.test.domain.entity.Customer;
import com.itau.test.domain.request.QuoteRequest;
import com.itau.test.domain.response.QuoteResponse;
import com.itau.test.domain.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.ReportingPolicy.WARN;

@Mapper(componentModel = "spring", unmappedTargetPolicy = WARN)
public interface QuoteMapper {

    @Mapping(source = "customer.date_of_birth", target = "customer.date_of_birth")
    @Mapping(source = "customer.document_number", target = "customer.document_number")
    @Mapping(source = "customer.phone_number", target = "customer.phone_number")
    QuoteResponse entityToResponse(QuoteEntity entity);

    List<QuoteResponse> entitiesToResponses(List<QuoteEntity> entities);

    @Mapping(source = "date_of_birth", target = "date_of_birth")
    @Mapping(source = "document_number", target = "document_number")
    @Mapping(source = "phone_number", target = "phone_number")
    CustomerResponse customerToCustomerResponse(Customer customer);

    @Mapping(source = "quoteRequest.product_id", target = "product_id")
    @Mapping(source = "quoteRequest.offer_id", target = "offer_id")
    @Mapping(source = "quoteRequest.category", target = "category")
    @Mapping(source = "quoteRequest.total_monthly_premium_amount", target = "total_monthly_premium_amount")
    @Mapping(source = "quoteRequest.total_coverage_amount", target = "total_coverage_amount")
    @Mapping(source = "quoteRequest.coverages", target = "coverages")
    @Mapping(source = "quoteRequest.assistances", target = "assistances")
    @Mapping(source = "quoteRequest.customer.document_number", target = "customer.document_number")
    @Mapping(source = "quoteRequest.customer.name", target = "customer.name")
    @Mapping(source = "quoteRequest.customer.email", target = "customer.email")
    @Mapping(source = "quoteRequest.customer.type", target = "customer.type")
    @Mapping(source = "quoteRequest.customer.gender", target = "customer.gender")
    @Mapping(source = "quoteRequest.customer.date_of_birth", target = "customer.date_of_birth")
    @Mapping(source = "quoteRequest.customer.phone_number", target = "customer.phone_number")
    @Mapping(target = "policy_id", ignore = true)
    @Mapping(target = "policty_created_at", ignore = true)
    @Mapping(target = "id", ignore = true)
    QuoteEntity requestToEntity(QuoteRequest quoteRequest);
}