package io.dksifoua.eshop.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MongoDBContainer;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
		classes = TestEShopProductCatalogServiceApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class EShopProductCatalogServiceApplicationTests {

	@Autowired
	private MongoDBContainer mongoDBContainer;

	@Test
	void contextLoads() {
		assertTrue(mongoDBContainer.isRunning());
	}

}
