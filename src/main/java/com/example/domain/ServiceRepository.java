package com.example.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "service", path = "service")
public interface ServiceRepository extends PagingAndSortingRepository<Service,Long> {
}
