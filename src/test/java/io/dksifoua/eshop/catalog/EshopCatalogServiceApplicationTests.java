package io.dksifoua.eshop.catalog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@AutoConfigureWebTestClient
@TestPropertySource(locations = "classpath:application-test.yaml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class EshopCatalogServiceApplicationTests extends AbstractContainerBaseTest {

	@Test
	void contextLoads() {
		Assertions.assertTrue(true);
	}

}
