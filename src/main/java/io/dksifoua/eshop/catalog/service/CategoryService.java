package io.dksifoua.eshop.catalog.service;

import io.dksifoua.eshop.catalog.dto.CategoryDTO;
import io.dksifoua.eshop.catalog.mapper.CategoryMapper;
import io.dksifoua.eshop.catalog.repository.ICategoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryService {

    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Mono<CategoryDTO> create(CategoryDTO categoryDTO) {
        return categoryRepository
                .save(CategoryMapper.from(categoryDTO))
                .map((CategoryMapper::from));
    }

    public Flux<CategoryDTO> getAll() {
        return categoryRepository
                .findAll()
                .map(CategoryMapper::from)
                .switchIfEmpty(Flux.empty());
    }
}
