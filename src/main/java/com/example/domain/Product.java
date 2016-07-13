package com.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@Getter
@Entity
public class Product extends AbstractEntity {
    private String name;

    public Product(String name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "product")
    private Set<Price> prices = new HashSet<>();

    public void addPrice(Price price) {
        checkArgument(!prices.stream()
                .filter(c -> c.isSameCountry(price))
                .filter(c -> c.isOverlapping(price))
                .findFirst().isPresent(),
                "Prices must not overlap");
        price.setProduct(this);
        prices.add(price);
    }

    public void addPrices(Price... prices) {
        for (Price p:prices) {
            addPrice(p);
        }
    }

    public Optional<Price> getPrice(String countryCode) {
        return getPrice(countryCode,LocalDateTime.now());
    }

    public Optional<Price> getPrice(String countryCode, LocalDateTime pointInTime) {
        checkNotNull(countryCode);
        checkNotNull(pointInTime);
        return prices.stream()
                .filter(p -> p.isBelongingTo(countryCode))
                .filter(p -> p.isValidAt(pointInTime))
                .findFirst();
    }

    public void addPrices(List<Price> prices) {
        prices.forEach(p -> addPrice(p));
    }

    public Collection<Price> getPrices() {
        return Collections.unmodifiableSet(prices);
    }
}
