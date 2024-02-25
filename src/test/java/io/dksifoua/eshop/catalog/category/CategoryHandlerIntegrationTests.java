package io.dksifoua.eshop.catalog.category;

import io.dksifoua.eshop.catalog.utility.Active;
import io.dksifoua.eshop.catalog.utility.Audit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryHandlerIntegrationTests {

    @Value("${app.context-path}" + "${app.endpoints.categories}")
    String categoryEndpoint;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void addCategoryTest() {
        Category category = Category.builder()
                .name("Home Appliances")
                .description("Assortment of household and commercial appliances.")
                .active(Active.builder().from(LocalDateTime.now()).to(null).build())
                .audit(Audit.builder().build())
                .build();


        webTestClient.post().uri(categoryEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(category), Category.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void listCategoriesTest() {
        webTestClient.get().uri(categoryEndpoint).exchange()
                .expectStatus().isOk()
                .expectBodyList(Category.class)
                .consumeWith(System.out::println);
    }
}
