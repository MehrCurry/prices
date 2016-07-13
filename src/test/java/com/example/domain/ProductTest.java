package com.example.domain;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;

public class ProductTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private LocalDateTime start;
    private LocalDateTime end;

    @Before
    public void setUp() throws Exception {
        start = LocalDateTime.parse("2007-12-03T10:15:30");
        end = LocalDateTime.parse("2007-12-04T10:15:26");
    }

    @Test
    public void addSamePriceTwice() throws Exception {
        Product p = new Product("junit");
        DateRange range = DateRange.builder()
                .from(start)
                .to(end)
                .build();

        Price aPrice = Price.builder()
                .countryCode("DE")
                .validity(range)
                .money(Money.of(12,"EUR"))
                .build();

        thrown.expect(IllegalArgumentException.class);
        p.addPrices(aPrice,aPrice);


    }

}