package io.dksifoua.eshop.catalog.category;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Flux<Category> getCategories() {

        return categoryRepository.findAll();
    }
}
