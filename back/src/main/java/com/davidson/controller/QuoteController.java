package com.davidson.controller;

import com.davidson.model.Quote;
import com.davidson.service.QuoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Controller for Quote
 */
@RestController
@RequestMapping("/api/v1")
@Api(tags = "Quote")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    private static final Logger log = LoggerFactory.getLogger(QuoteController.class);

    /**
     * Allows to recover all quotes
     * @return quote list and display response in format JSON
     */
    @GetMapping("/quotes")
    @ApiOperation("Allows to recover all quotes")
    public ResponseEntity collectAll() {
        return ResponseEntity.ok(quoteService.findAll());
    }

    /**
     * Allows to create a quote
     * @param quote create
     * @return 201 created
     */
    @PostMapping(value = "/quotes")
    @ApiOperation("Allows to create a quote")
    public ResponseEntity persistQuote(@RequestBody Quote quote) {
        Quote result =  quoteService.createQuote(quote);
        return ResponseEntity.ok(result);
    }

    /**
     * Allows to update a quote
     * @param id of the quote we want to update
     * @param quote data to be updated
     * @return not found if id parameter is not defined or ok if the quote is present
     */
    @PutMapping(value = "/quotes/{quoteid}")
    @ApiOperation("Allows to update a quote")
    public ResponseEntity updateQuote(@PathVariable("quoteid") Long id, @RequestBody Quote quote) {
        Optional<Quote> currentQuote = quoteService.findById(id);
        if(!currentQuote.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        quote.setId(id);
        quoteService.updateQuote(quote);
        return ResponseEntity.ok(quote);
    }

    /**
     * Allows to validate a quote and send email
     * @param id of the quote to be validated
     * @return quote that has the id
     */
    @PutMapping(value = "/quotes/{quoteid}/validate")
    @ApiOperation("Allows to validate a quote")
    public ResponseEntity validateQuote(@PathVariable("quoteid") Long id) {
        log.info("Validate Quote");
        Optional<Quote> optionalQuote = quoteService.findById(id);
        if(!optionalQuote.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Quote quote = optionalQuote.get();
        quote.setId(id);
        quote = quoteService.validateQuote(quote);
        return ResponseEntity.ok(quote);
    }
}
