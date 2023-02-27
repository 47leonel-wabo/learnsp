package com.wbt.learnsp.repository;

import com.wbt.learnsp.model.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
    List<VideoEntity> findByName(final String name);

    List<VideoEntity> findByNameContainsOrDescriptionContainsAllIgnoreCase(final String name, final String description);

    List<VideoEntity> findByDescriptionContainsIgnoreCase(final String description);

    List<VideoEntity> findByNameContainsIgnoreCase(final String name);
}
