package com.wbt.learnsp.service;

import com.wbt.learnsp.model.UniversalSearch;
import com.wbt.learnsp.model.Video;
import com.wbt.learnsp.model.VideoEntity;
import com.wbt.learnsp.model.VideoSearch;
import com.wbt.learnsp.repository.VideoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VideoService {
    private final VideoRepository mVideoRepository;

    List<Video> mVideos = List.of(
            new Video("Hands on spring boot 3"),
            new Video("Hard coded values"),
            new Video("Not a good habit")
    );

    public VideoService(VideoRepository videoRepository) {
        mVideoRepository = videoRepository;
    }

    public List<Video> getVideos() {
        return mVideos;
    }

    public Video create(final Video video) {
        ArrayList<Video> videoArrayList = new ArrayList<>(mVideos);
        videoArrayList.add(video);
        this.mVideos = List.copyOf(videoArrayList);
        return video;
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
}
