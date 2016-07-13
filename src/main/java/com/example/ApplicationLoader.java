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

@Component
@Slf4j
public class ApplicationLoader implements ApplicationRunner {
    @Autowired
    private ProductRepository repository;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        LocalDateTime start = LocalDateTime.parse("2007-12-03T10:15:30");
        LocalDateTime end = LocalDateTime.parse("2007-12-04T10:15:26");

        Product p = new Product("Test");
        p.addPrice(Price.builder()
                .validity(DateRange.builder()
                        .from(start)
                        .to(end)
                        .build())
                .money(Money.of(12,"EUR"))
                .countryCode("DE")
                .build());
        p.addPrice(Price.builder()
                .validity(DateRange.builder()
                        .from(start)
                        .to(end)
                        .build())
                .money(Money.of(13,"EUR"))
                .countryCode("US")
                .build());
        repository.save(p);
        repository.findAll().forEach(e -> log.debug(e.toString()));
    }
}
