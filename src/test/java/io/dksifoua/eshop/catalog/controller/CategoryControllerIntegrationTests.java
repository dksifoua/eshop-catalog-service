package io.dksifoua.eshop.catalog.controller;

import io.dksifoua.eshop.catalog.AbstractContainerBase;
import io.dksifoua.eshop.catalog.dto.CategoryDTO;
import io.dksifoua.eshop.catalog.entity.Category;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.yaml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class CategoryControllerIntegrationTests extends AbstractContainerBase {

    @Value("${app.context-path}")
    private String contextPath;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @DisplayName("Create a new category test.")
    public void createNewCategoryTest() {
        CategoryDTO categoryDTO  = CategoryDTO.builder()
                .name("Furniture")
                .description("Wide range of furniture for home, office, and outdoors.")
                .build();
        webTestClient.post().uri(contextPath + "/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(categoryDTO), Category.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.name").isEqualTo(categoryDTO.getName())
                .jsonPath("$.description").isEqualTo(categoryDTO.getDescription());
    }

    @Test
    @Order(2)
    @DisplayName("Get All Product Catalog Categories")
    void getAllProductCatalogCategories() {
        webTestClient.get().uri(contextPath + "/categories")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(CategoryDTO.class)
                .consumeWith(System.out::println);
    }
}
