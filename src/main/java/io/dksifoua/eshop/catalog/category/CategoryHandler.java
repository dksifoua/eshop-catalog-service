package io.dksifoua.eshop.catalog.category;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class CategoryHandler {

    @Value("${app.context-path}"+"${app.endpoints.categories}")
    private String categoryEndpoint;

    private final CategoryService categoryService;

    public CategoryHandler(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Bean
    public RouterFunction<ServerResponse> categoryRoutes() {
        return route()
                .GET(categoryEndpoint, this::listCategories)
                .build();
    }

    public Mono<ServerResponse> listCategories(ServerRequest request) {

        return categoryService
                .getCategories()
                .collectList()
                .flatMap(categories -> ServerResponse.ok()
                        .body(BodyInserters.fromValue(categories))
                );
    }
}
