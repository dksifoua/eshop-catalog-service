package io.dksifoua.eshop.catalog.repository;

import io.dksifoua.eshop.catalog.entity.Category;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends R2dbcRepository<Category, Integer> {
}
