package com.wbt.learnsp.service;

import com.wbt.learnsp.model.NewVideo;
import com.wbt.learnsp.model.UniversalSearch;
import com.wbt.learnsp.model.VideoEntity;
import com.wbt.learnsp.model.VideoSearch;
import com.wbt.learnsp.repository.VideoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Service
public class VideoService {
    private final VideoRepository mVideoRepository;

    @PostConstruct
    void initDatabase() {
        mVideoRepository.saveAll(List.of(
                new VideoEntity("Spring Boot 3", "Spring Boot 3 will only speed things up and make it super simple", "leno"),
                new VideoEntity("Clean Code", "Don't do this on your own code, inserting data programmatically", "leno"),
                new VideoEntity("Broken code Tweaks", "Find ways to debug your code and spot errors or miss-behaviors", "lena")
        ));
    }

    public VideoService(VideoRepository videoRepository) {
        mVideoRepository = videoRepository;
    }

    public List<VideoEntity> getVideos() {
        return mVideoRepository.findAll();
    }

    public List<VideoEntity> search(final VideoSearch search) {
        if (StringUtils.hasText(search.name()) && StringUtils.hasText(search.description())) {
            return mVideoRepository.findByNameContainsOrDescriptionContainsAllIgnoreCase(search.name(), search.description());
        }
        if (StringUtils.hasText(search.name())) {
            return mVideoRepository.findByNameContainsIgnoreCase(search.name());
        }
        if (StringUtils.hasText(search.description())) {
            return mVideoRepository.findByDescriptionContainsIgnoreCase(search.description());
        }
        return Collections.emptyList();
    }

    public List<VideoEntity> search(final UniversalSearch search) {
        VideoEntity videoEntity = new VideoEntity();
        if (StringUtils.hasText(search.value())) {
            videoEntity.setName(search.value());
            videoEntity.setDescription(search.value());
        }
        Example<VideoEntity> example = Example.of(
                videoEntity,
                ExampleMatcher.matchingAny()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );
        return mVideoRepository.findAll(example);
    }

    public VideoEntity create(final NewVideo newVideo, final String username) {
        return mVideoRepository
                .saveAndFlush(new VideoEntity(newVideo.name(), newVideo.description(), username));
    }

    public void delete(final Long videoId) {
        mVideoRepository.findById(videoId)
                .map(videoEntity -> {
                    mVideoRepository.delete(videoEntity);
                    return true;
                })
                .orElseThrow(() -> new RuntimeException("No video at " + videoId));
    }
}
