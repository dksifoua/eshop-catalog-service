package io.dksifoua.eshop.catalog.category;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
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

    public Mono<ServerResponse> removeCategory(ServerRequest request) {
        return categoryService.deleteCategory(request.pathVariable("id"))
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> updateCategory(ServerRequest request) {
        return request.bodyToMono(Category.class)
                .flatMap(category -> categoryService.updateCategory(request.pathVariable("id"), category))
                .flatMap(updatedCategory -> ServerResponse.ok().bodyValue(updatedCategory))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
