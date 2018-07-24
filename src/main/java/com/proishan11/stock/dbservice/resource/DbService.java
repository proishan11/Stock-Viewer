package com.proishan11.stock.dbservice.resource;

import com.proishan11.stock.dbservice.model.Quote;
import com.proishan11.stock.dbservice.model.Quotes;
import com.proishan11.stock.dbservice.repository.QuotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("resource/db")
public class DbService {

    @Autowired
    private QuotesRepository quotesRepository;

    //GET method which return the quotes by a particular username
    @GetMapping("/{username}")
    public List<String> getQuotes(@PathVariable("username")
                                  final String username) {

        return quotesRepository.findByUserName(username)
                .stream()
                .map(Quote::getQuote)
                .collect(Collectors.toList());

    }

    //wrapper function as used by /add
    private List<String> getQuotesByUsername(@PathVariable("username") String username) {
        return quotesRepository.findByUserName(username)
                .stream()
                .map(Quote::getQuote)
                .collect(Collectors.toList());
    }


    //Post Method which adds quotes by a user and return the added posts
    //quotes is a list of Quote
    //Example header Content: {"username": "Sam", "quotes" : ["FOO BAR"]}
    @PostMapping("/add")
    public List<String> add(@RequestBody final Quotes quotes) {
        quotes.getQuotes()
                .stream()
                .map(quote -> new Quote(quotes.getUserName(), quote))
                .forEach(quote -> quotesRepository.save(quote));
        return getQuotesByUsername(quotes.getUserName());
    }

    @PostMapping("/delete/{username}")
    public List<String> delete(@PathVariable("username") final String username) {

        List<Quote> quotes = quotesRepository.findByUserName(username);

        for(Quote quote: quotes) {
            quotesRepository.deleteById(quote.getId());
        }

        return getQuotesByUsername(username);
    }
}
