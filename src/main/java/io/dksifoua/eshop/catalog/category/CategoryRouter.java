package io.dksifoua.eshop.catalog.category;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class CategoryRouter {

    @Value("${app.context-path}" + "${app.endpoints.categories}")
    private String categoryEndpoint;

    @Bean
    public RouterFunction<ServerResponse> listCategoriesRouterFunction(CategoryHandler categoryHandler) {
        RequestPredicate predicate = RequestPredicates.GET(categoryEndpoint);
        return RouterFunctions.route(predicate, request -> categoryHandler.listCategories());
    }

    @Bean
    public RouterFunction<ServerResponse> addCategoryRouterFunction(CategoryHandler categoryHandler) {
        RequestPredicate predicate = RequestPredicates.POST(categoryEndpoint);
        return RouterFunctions.route(predicate, categoryHandler::addCategory);
    }

    @Bean
    public RouterFunction<ServerResponse> removeCategoryRouterFunction(CategoryHandler categoryHandler) {
        RequestPredicate predicate = RequestPredicates.DELETE(categoryEndpoint + "/{id}");
        return RouterFunctions.route(predicate, categoryHandler::removeCategory);
    }

    @Bean
    public RouterFunction<ServerResponse> updateCategoryRouterFunction(CategoryHandler categoryHandler) {
        RequestPredicate predicate = RequestPredicates.PUT(categoryEndpoint + "/{id}");
        return RouterFunctions.route(predicate, categoryHandler::updateCategory);
    }
}
