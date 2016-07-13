package com.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

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

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Price> prices = new HashSet<>();

    public void addPrice(Price price) {
        checkArgument(prices.stream()
                .filter(c -> c.getCountryCode().equals(price.getCountryCode()))
                .filter(c -> !c.getValidity().isOverlapping(price.getValidity())).findFirst().isPresent());
        prices.add(price);
    }

    public void addPrices(Price... prices) {
        for (Price p:prices) {
            addPrice(p);
        }
    }
}
