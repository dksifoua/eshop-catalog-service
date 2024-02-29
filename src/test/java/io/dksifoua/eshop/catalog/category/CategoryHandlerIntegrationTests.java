package io.dksifoua.eshop.catalog.category;

import io.dksifoua.eshop.catalog.TestEShopProductCatalogServiceApplication;
import io.dksifoua.eshop.catalog.utility.Active;
import io.dksifoua.eshop.catalog.utility.Audit;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
                    assertNotNull(categoryResponse);
                    assertNotNull(categoryResponse.getAudit().getCreatedAt());
                    assertNotNull(categoryResponse.getAudit().getUpdatedAt());

                    category = categoryResponse;
                }).consumeWith(System.out::println);
    }

    @Test
    @Order(2)
    public void addExistingCategoryTest() {
        Category anExistingCategory = Category.builder()
                .id(null)
                .name(category.getName())
                .description(category.getDescription())
                .parentId(category.getParentId())
                .active(category.getActive())
                .audit(category.getAudit())
                .build();
        webTestClient.post().uri(categoryEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(anExistingCategory), Category.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody()
                .consumeWith(System.out::println);
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
    public void updateCategoryTest() {
        Category updatedCategory = Category.builder()
                .name("Furniture")
                .description("Wide range of furniture for home, office, and outdoors.")
                .active(category.getActive())
                .audit(category.getAudit())
                .build();
        webTestClient.put().uri(categoryEndpoint + "/{id}", category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updatedCategory), Category.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Category.class)
                .consumeWith(response -> {
                    Category categoryResponse = response.getResponseBody();
                    assertNotNull(categoryResponse);
                    assertEquals(category.getId(), categoryResponse.getId());
                    assertEquals(updatedCategory.getName(), categoryResponse.getName());
                    assertEquals(updatedCategory.getDescription(), categoryResponse.getDescription());
                    assertEquals(updatedCategory.getActive().getFrom(), categoryResponse.getActive().getFrom());
                    assertEquals(updatedCategory.getAudit().getCreatedAt(), categoryResponse.getAudit().getCreatedAt());
                    assertTrue(updatedCategory.getAudit().getUpdatedAt()
                            .isBefore(categoryResponse.getAudit().getUpdatedAt()));
                });
    }

    @Test
    @Order(4)
    public void removeCategoryTest() {
        webTestClient.delete().uri(categoryEndpoint + "/{id}", category.getId()).exchange()
                .expectStatus().isNoContent();
    }
}
