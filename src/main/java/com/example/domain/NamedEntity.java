package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NamedEntity extends AbstractEntity {
    @NotNull
    private String name;
}
