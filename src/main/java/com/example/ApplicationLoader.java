package com.example;

import com.example.domain.DateRange;
import com.example.domain.Price;
import com.example.domain.Product;
import com.example.domain.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ApplicationLoader implements ApplicationRunner {
    @Autowired
    private ProductRepository repository;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        LocalDateTime start = LocalDateTime.parse("2007-12-03T10:15:30");
        LocalDateTime end = LocalDateTime.parse("2007-12-04T10:15:26");
        final DateRange dateRange = DateRange.builder()
                .from(start)
                .build();

        Product p = new Product("Test");

        List<Price> prices = Arrays.stream(Locale.getISOCountries()).map(c -> {
            return Price.builder()
                    .validity(dateRange)
                    .money(Money.of(12, "EUR"))
                    .countryCode(c)
                    .build();
        }).collect(Collectors.toList());
        p.addPrices(prices);
        repository.save(p);
        repository.findAll().forEach(e -> log.debug(e.toString()));
    }
}
