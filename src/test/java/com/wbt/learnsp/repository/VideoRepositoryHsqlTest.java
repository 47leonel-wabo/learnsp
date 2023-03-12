package com.wbt.learnsp.repository;

import com.wbt.learnsp.model.VideoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VideoRepositoryHsqlTest {

    @Autowired
    VideoRepository mVideoRepository;

    @BeforeEach
    void setUp() {
        mVideoRepository.saveAll(
                List.of(
                        new VideoEntity("Spring Boot 3.x", "Hands on spring boot 3 with new features.", "nero"),
                        new VideoEntity("Always test your code", "Every features of this application should be tested.", "nera"),
                        new VideoEntity("Deploy Spring Boot application to production", "Make your application available to the whole world and enjoy your skills.", "neo")
                ));
    }

    @Test
    void findAllShouldProduceAllVideos() {
        List<VideoEntity> allVideos = mVideoRepository.findAll();
        assertThat(allVideos).hasSize(3);
    }

    @Test
    void findByNameShouldRetrieveOneEntry() {
        List<VideoEntity> entityList = mVideoRepository.findByNameContainsIgnoreCase("sPring bOOt 3");
        assertThat(entityList).hasSize(1);
        assertThat(entityList).extracting(VideoEntity::getDescription)
                .containsExactlyInAnyOrder("Hands on spring boot 3 with new features.");
    }

    @Test
    void findByNameOrDescriptionShouldFindTwo() {
        List<VideoEntity> videos = mVideoRepository.findByNameContainsOrDescriptionContainsAllIgnoreCase("CoDe", "YOUR APPLICATION");
        assertThat(videos).hasSize(2);
        assertThat(videos)
                .extracting(VideoEntity::getDescription)
                .contains("Every features of this application should be tested.",
                        "Make your application available to the whole world and enjoy your skills.");
    }
}