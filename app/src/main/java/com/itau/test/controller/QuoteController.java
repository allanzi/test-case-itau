package com.itau.test.controller;

import com.itau.test.domain.request.QuoteRequest;
import com.itau.test.domain.response.QuoteResponse;
import com.itau.test.queue.producer.QuoteRecievedProducer;
import com.itau.test.service.QuoteService;
import com.itau.test.util.QueueUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.itau.test.constant.BusinessConstant.QUOTE;

@RestController
@RequestMapping(QUOTE)
@RequiredArgsConstructor
@Tag(name = "Quote API")
public class QuoteController {

    private final QuoteRecievedProducer quoteRecievedProducer;
    private final QuoteService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteController.class);
    private static final String RESPONSE_BODY_LOGGER = "Response body: {}";
    private static final String QUEUE_LOGGER = "Post Quote Recevied Queue: {}";

    @Operation(summary = "GET Quotes")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<QuoteResponse>>> getQuotes() {
        var response = service.getQuotes();

        LOGGER.info(RESPONSE_BODY_LOGGER, response.toString().substring(0, Math.min(response.toString().length(), 400)));

        return ResponseEntity.ok(Collections.singletonMap("data", response));
    }

    @Operation(summary = "POST Quote")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuoteResponse> requestQuote(@Valid @RequestBody QuoteRequest quoteRequest) {
        var quote = service.requestQuote(quoteRequest);

        try {
            String message = QueueUtil.objectToString(quote);
            quoteRecievedProducer.send(message);
            LOGGER.info(QUEUE_LOGGER, quote);
        } catch (Exception e) {
            LOGGER.warn("Error sending quote to queue: {}", e.getMessage());
        }

        LOGGER.info(RESPONSE_BODY_LOGGER, quote);

        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri())
                .body(quote);
    }
}