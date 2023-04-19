package com.francisca.inventoryService;

import com.francisca.inventoryService.model.Inventory;
import com.francisca.inventoryService.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient

public class InventoryServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(InventoryServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("ABC123");
			inventory.setQuantity(10);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("ABC124");
			inventory1.setQuantity(15);
			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory);
			logger.info("Inventory data loaded");
		};
	}
}
