package io.dksifoua.eshop.catalog;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@SpringBootApplication
public class EshopCatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EshopCatalogServiceApplication.class, args);
    }

    @Bean
	public ConnectionFactoryInitializer setupTables(ConnectionFactory connectionFactory) {
        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("data.sql")));

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(populator);

        return initializer;
    }
}
