package com.proishan11.stock.dbservice.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("resource/db")
public class DbService {

    @GetMapping("/{username}")
    public List<String> getQuotes(@PathVariable("username")
                                  final String username) {

        quotesRepository.findByUsername(username);

        return null;
    }
}
