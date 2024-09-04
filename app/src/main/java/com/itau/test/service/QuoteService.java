package com.itau.test.service;

import com.itau.test.domain.entity.InsurancePolicy;
import com.itau.test.domain.request.QuoteRequest;
import com.itau.test.domain.response.QuoteResponse;

import java.util.List;

public interface QuoteService {

    List<QuoteResponse> getQuotes();

    QuoteResponse requestQuote(QuoteRequest quoteRequest);

    void updatePolicy(InsurancePolicy policy);

}