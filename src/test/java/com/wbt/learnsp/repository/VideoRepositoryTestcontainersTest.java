package com.wbt.learnsp.repository;

import com.wbt.learnsp.model.VideoEntity;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// our static inner class
@ContextConfiguration(initializers = {VideoRepositoryTestcontainersTest.DataSourceInitializer.class})
public class VideoRepositoryTestcontainersTest {

    @Autowired
    VideoRepository mRepository;

    @Container
    static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:alpine").withUsername("postgres");

    static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + database.getJdbcUrl(),
                    "spring.datasource.username=" + database.getUsername(),
                    "spring.datasource.password=" + database.getPassword(),
                    "spring.jpa.hibernate.ddl-auto=create-drop");
        }
    }

    @BeforeEach
    void setUp() {
        mRepository.saveAll(List.of(
                new VideoEntity("NEED HELP with your SPRING BOOT 3 App?", "SPRING BOOT 3 will only speed things up.", "alice"),
                new VideoEntity("Don't do this in your own CODE!", "As a pro developer, never EVER di this to your code.", "alice"),
                new VideoEntity("SECRET to fix BROKEN CODE!", "Discover way to not only debug your code.", "bob")
        ));
    }

    @Test
    void findAllShouldProduceAllVideos() {
        List<VideoEntity> videoEntityList = mRepository.findAll();
        assertThat(videoEntityList).hasSize(3);
    }

    @Test
    void findByName() {
        List<VideoEntity> entityList = mRepository.findByNameContainsIgnoreCase("SPRING BOOT 3");
        assertThat(entityList).hasSize(1);
    }

    @Test
    void findByNameOrDescription() {
        List<VideoEntity> entityList = mRepository.findByNameContainsOrDescriptionContainsAllIgnoreCase("CODE", "developer");
        assertThat(entityList).hasSize(2);
    }
}
