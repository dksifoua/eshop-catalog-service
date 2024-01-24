package io.dksifoua.eshop.catalog.controller;

import io.dksifoua.eshop.catalog.dto.CategoryDTO;
import io.dksifoua.eshop.catalog.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/catalog/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.create(categoryDTO);
    }

    @GetMapping
    public Flux<CategoryDTO> list() {
        return categoryService.getAll();
    }
}
