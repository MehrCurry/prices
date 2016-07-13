package com.example.domain;

import com.example.util.ValidateableObject;
import lombok.AccessLevel;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity extends ValidateableObject {
    @Id
    @GeneratedValue
    @Getter(AccessLevel.PROTECTED)

    private Long id;

}
