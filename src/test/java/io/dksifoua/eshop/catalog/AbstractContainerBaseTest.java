package io.dksifoua.eshop.catalog;

import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbstractContainerBaseTest {

    protected static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER;

    static {
        POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:latest");
        POSTGRESQL_CONTAINER
                .withUsername("postgres-test")
                .withPassword("postgres-test")
                .withDatabaseName("eshop-product-catalog-test")
                .withExposedPorts(PostgreSQLContainer.POSTGRESQL_PORT)
                .start();
    }

    private static String getR2dbcUrl() {
        return POSTGRESQL_CONTAINER.getJdbcUrl().replace("jdbc", "r2dbc");
    }

    @DynamicPropertySource
    public static void registerDatabasePropertiesToApplicationContext(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", AbstractContainerBaseTest::getR2dbcUrl);
    }
}
