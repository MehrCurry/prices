package com.example.domain;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Product extends AbstractEntity {
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Price> prices = new HashSet<>();

    public void addPrice(Price price) {
        prices.add(price);
    }
}
