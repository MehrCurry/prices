package com.example.domain;

import lombok.*;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jadira.usertype.moneyandcurrency.moneta.PersistentMoneyAmountAndCurrency;
import org.javamoney.moneta.Money;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@TypeDef(name = "testmoneta_MoneyAmountWithCurrencyType", typeClass = PersistentMoneyAmountAndCurrency.class)
@EqualsAndHashCode(callSuper = false,exclude = "product")
@ToString(exclude = "product")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Price extends AbstractEntity {

    @Columns(columns = { @Column(name = "MY_CURRENCY"), @Column(name = "MY_AMOUNT") })
    @Type(type = "testmoneta_MoneyAmountWithCurrencyType")
    private Money money;
    @Embedded
    private DateRange validity;
    private String countryCode;

    @ManyToOne
    @Setter
    private Product product;

    public boolean isValidAt(LocalDateTime pointInTime) {
        return validity.isInRange(pointInTime);
    }

    public boolean isSameCountry(Price other) {
        return countryCode.equals(other.getCountryCode());
    }

    public boolean isOverlapping(Price other) {
        return validity.isOverlapping(other.validity);
    }

    public boolean isBelongingTo(String countryCode) {
        return this.countryCode.equals(countryCode);
    }
}
