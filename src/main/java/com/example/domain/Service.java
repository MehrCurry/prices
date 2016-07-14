package com.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Getter
@Entity
public class Service extends BillableEntity {
    public Service(String name) {
        super(name);
    }
}
