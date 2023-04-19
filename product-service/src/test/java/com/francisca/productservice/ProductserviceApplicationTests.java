package com.francisca.productservice;//package com.francisca.productservice;
//
//import com.francisca.productservice.dto.ProductDto;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Required;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.testcontainers.containers.MongoDBContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.math.BigDecimal;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@Testcontainers
//@AutoConfigureMockMvc
//@RequiredArgsConstructor
//class ProductServiceApplicationTests {
//
//	@Container
//	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
//	private final MockMvc mockMvc;
//	private final ObjectMapper objectMapper; // this converts POJO objects to JSON and vice versa
//	@DynamicPropertySource
//	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
//		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer:: getReplicaSetUrl);
//	}
//
//	@Test
//	void shouldCreateProduct() throws Exception {
//		ProductDto productDto = getProductDto();
//		String productRequestingString = objectMapper.writeValueAsString(productDto);
//		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
//					.contentType(MediaType.APPLICATION_JSON)
//					.content(productRequestingString))
//				.andExpect(status().isCreated());
//	}
//
//	private ProductDto getProductDto(){
//		return ProductDto.builder()
//				.name("samsung")
//				.description("samsung 23")
//				.price(BigDecimal.valueOf(2300))
//				.build();
//	}
//}
