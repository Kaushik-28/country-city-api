package com.kaushikdas.countrycity;

import com.kaushikdas.countrycity.controller.LocationController;
import com.kaushikdas.countrycity.repository.InMemoryLocationRepository;
import com.kaushikdas.countrycity.service.LocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CountryCityApiApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoadsWithRequiredApplicationBeans() {
		assertThat(applicationContext).isNotNull();

		assertThat(applicationContext.getBean(LocationController.class)).isNotNull();
		assertThat(applicationContext.getBean(LocationService.class)).isNotNull();
		assertThat(applicationContext.getBean(InMemoryLocationRepository.class)).isNotNull();
	}
}