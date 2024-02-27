package io.dksifoua.eshop.catalog.category;

import io.dksifoua.eshop.catalog.TestEShopProductCatalogServiceApplication;
import io.dksifoua.eshop.catalog.utility.Active;
import io.dksifoua.eshop.catalog.utility.Audit;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureWebTestClient
@SpringBootTest(
        classes = TestEShopProductCatalogServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class CategoryHandlerIntegrationTests {

    @Value("${app.context-path}" + "${app.endpoints.categories}")
    private String categoryEndpoint;

    @Autowired
    private WebTestClient webTestClient;

    private static Category category;

    @BeforeAll
    public static void setup() {
        category = Category.builder()
                .name("Home Appliances")
                .description("Assortment of household and commercial appliances.")
                .active(Active.builder().from(LocalDateTime.now()).to(null).build())
                .audit(Audit.builder().build())
                .build();
    }

    @Test
    @Order(1)
    public void addCategoryTest() {
        webTestClient.post().uri(categoryEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(category), Category.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Category.class)
                .consumeWith(response -> {
                    Category categoryResponse = response.getResponseBody();
                    assert categoryResponse != null;
                    Assertions.assertNotNull(categoryResponse.getAudit().getCreatedAt());
                    Assertions.assertNotNull(categoryResponse.getAudit().getUpdatedAt());

                    category = categoryResponse;
                });
    }

    @Test
    @Order(2)
    public void listCategoriesTest() {
        webTestClient.get().uri(categoryEndpoint).exchange()
                .expectStatus().isOk()
                .expectBodyList(Category.class)
                .consumeWith(System.out::println)
                .hasSize(1);
    }

    @Test
    @Order(3)
    public void removeCategoryTest() {
        webTestClient.delete().uri(categoryEndpoint + "/{id}", category.getId()).exchange()
                .expectStatus().isNoContent();
    }
}
