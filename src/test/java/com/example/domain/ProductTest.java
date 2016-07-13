package com.example.domain;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private LocalDateTime start;
    private LocalDateTime end;
    private Product p;

    @Before
    public void setUp() throws Exception {
        start = LocalDateTime.parse("2007-12-03T10:15:30");
        end = LocalDateTime.parse("2007-12-04T10:15:26");
        p = new Product("junit");
    }

    @Test
    public void addSamePriceTwice() throws Exception {
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

    @Test
    public void addNonOverlappingPrices() {
        DateRange range = DateRange.builder()
                .from(start)
                .to(end)
                .build();

        LocalDateTime end2 = LocalDateTime.parse("2007-12-05T10:15:26");
        DateRange second = DateRange.builder()
                .from(end)
                .to(end2)
                .build();

        DateRange third = DateRange.builder()
                .from(end2)
                .build();

        Price p1 = Price.builder()
                .countryCode("DE")
                .validity(range)
                .money(Money.of(12,"EUR"))
                .build();

        Price p2 = Price.builder()
                .countryCode("DE")
                .validity(second)
                .money(Money.of(13,"EUR"))
                .build();

        Price p3 = Price.builder()
                .countryCode("DE")
                .validity(third)
                .money(Money.of(14,"EUR"))
                .build();

        p.addPrices(p1,p2,p3);

        assertThat(p.getPrice("DE",start).get()).isEqualTo(p1);
        assertThat(p.getPrice("DE",end).get()).isEqualTo(p2);
        assertThat(p.getPrice("DE",end2).get()).isEqualTo(p3);
        assertThat(p.getPrice("US",end2)).isEmpty();
    }

    @Test
    public void testOverlapWithDifferentCountry() {
        DateRange range = DateRange.builder()
                .from(start)
                .to(end)
                .build();

        Price p1 = Price.builder()
                .countryCode("DE")
                .validity(range)
                .money(Money.of(12,"EUR"))
                .build();

        Price p2 = Price.builder()
                .countryCode("US")
                .validity(range)
                .money(Money.of(12,"USD"))
                .build();

        Product product = new Product("test");
        product.addPrices(p1,p2);
        assertThat(product.getPrice("DE",start).get()).isEqualTo(p1);
        assertThat(product.getPrice("US",start).get()).isEqualTo(p2);
    }

    @Test
    public void testGetPriceNow() {

        Price p1 = Price.builder()
                .countryCode("DE")
                .validity(DateRange.builder()
                        .from(start)
                        .to(end)
                        .build())
                .money(Money.of(12,"EUR"))
                .build();


        Product product = new Product("test");
        product.addPrices(p1);
        assertThat(product.getPrice("DE")).isEmpty();

        Price p2 = Price.builder()
                .countryCode("DE")
                .validity(DateRange.builder()
                        .from(end)
                        .build())
                .money(Money.of(13,"EUR"))
                .build();
        product.addPrice(p2);
        assertThat(product.getPrice("DE").get()).isEqualTo(p2);

    }

}