package io.dksifoua.eshop.catalog.category;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Configuration
public class CategoryHandler {

    private final CategoryService categoryService;

    public CategoryHandler(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Mono<ServerResponse> listCategories() {
        return ServerResponse.ok().body(categoryService.getCategories(), Category.class);
    }

    public Mono<ServerResponse> addCategory(ServerRequest request) {
        return request.bodyToMono(Category.class)
                .flatMap(categoryService::saveCategory)
                .flatMap(category -> ServerResponse.created(
                        UriComponentsBuilder.fromPath(("/{id}")).buildAndExpand(category.getId()).toUri()
                ).bodyValue(category));
    }
}