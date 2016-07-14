package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

import static com.google.common.base.Preconditions.*;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Product.class, name = "product"),
        @JsonSubTypes.Type(value = Service.class, name = "service")
})
public abstract class BillableEntity extends NamedEntity {
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "billable")
    private Set<Price> prices = new HashSet<>();

    public BillableEntity(String name) {
        super(name);
    }

    public void addPrice(Price price) {
        checkArgument(!prices.stream()
                .filter(c -> c.isSameCountry(price))
                .filter(c -> c.isOverlapping(price))
                .findFirst().isPresent(),
                "Prices must not overlap");
        price.setBillable(this);
        prices.add(price);
    }

    public void addPrices(Price... prices) {
        for (Price p:prices) {
            addPrice(p);
        }
    }

    @JsonIgnore
    public Optional<Price> getPrice() {
        return getPrice(Locale.getDefault().getCountry());
    }

    @JsonIgnore
    public Optional<Price> getPrice(String countryCode) {
        return getPrice(countryCode, LocalDateTime.now());
    }

    @JsonIgnore
    public Optional<Price> getPrice(String countryCode, LocalDateTime pointInTime) {
        checkNotNull(countryCode);
        checkNotNull(pointInTime);
        return prices.stream()
                .filter(p -> p.isBelongingTo(countryCode))
                .filter(p -> p.isValidAt(pointInTime))
                .findFirst();
    }

    public void updatePriceByDueDate(Price newPrice) {
        Price currentPrice=getPrice(newPrice.getCountryCode())
                .orElseThrow(() -> new IllegalStateException("no current price found"));
        checkState(currentPrice
                .isOpenEnded(),"update only works if current price is open ended");
        checkState(newPrice.getValidity()
                .getFrom().isAfter(currentPrice.getValidity().getFrom()),"new price must be after current price");

        DateRange range=DateRange.builder()
                .from(currentPrice.getValidity().getFrom())
                .to(newPrice.getValidity().getFrom())
                .build();

        Price currentPriceEnded = Price.builder()
                .validity(range)
                .countryCode(currentPrice.getCountryCode())
                .money(currentPrice.getMoney())
                .build();

        removePrice(currentPrice);
        addPrices(currentPriceEnded,newPrice);
    }

    private void removePrice(Price aPrice) {
        prices.remove(aPrice);
    }

    public void addPrices(List<Price> prices) {
        prices.forEach(p -> addPrice(p));
    }

    @JsonProperty
    public Collection<Price> getPrices() {
        return Collections.unmodifiableSet(prices);
    }
}
