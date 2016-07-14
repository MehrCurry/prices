package com.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor
@Getter
@Entity
public class Product extends BillableEntity {

    @ManyToMany
    private Set<Service> requiredServices = new HashSet<>();

    @ManyToMany
    private Set<Service> excludedServices = new HashSet<>();

    public Product(String name) {
        super(name);
    }

    public void addRequiredServices(Service... services) {
        requiredServices.addAll(Arrays.asList(services));
    }

    public void addExcludedServices(Service... services) {
        excludedServices.addAll(Arrays.asList(services));
    }

}
