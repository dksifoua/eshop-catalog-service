package io.dksifoua.eshop.catalog.category;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Flux<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Mono<Category> saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Mono<Void> deleteCategory(String id) {
        return categoryRepository.deleteAllById(Collections.singleton(id));
    }
}
