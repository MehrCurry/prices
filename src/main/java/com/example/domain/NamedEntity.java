package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NamedEntity extends AbstractEntity {
    @NotNull
    private String name;
}
