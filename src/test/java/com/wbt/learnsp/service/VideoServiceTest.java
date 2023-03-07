package com.wbt.learnsp.service;

import com.wbt.learnsp.model.NewVideo;
import com.wbt.learnsp.model.VideoEntity;
import com.wbt.learnsp.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    VideoService mVideoService;

    @Mock
    VideoRepository mRepository;

    @BeforeEach
    void setUp() {
        this.mVideoService = new VideoService(mRepository);
    }

    @Test
    void getVideosShouldReturnAll() {
        // given
        VideoEntity leoVideo = new VideoEntity("leo", "desc leo");
        VideoEntity leaVideo = new VideoEntity("lea", "desc lea");

        // when
        when(mRepository.findAll()).thenReturn(List.of(leoVideo, leaVideo));
        List<VideoEntity> videos = mVideoService.getVideos();

        // then
        assertThat(videos).containsExactly(leoVideo, leaVideo);
    }

    @Test
    void creatingNewVideoShouldReturnTheSameData() {
        // given
        given(mRepository.saveAndFlush(any(VideoEntity.class)))
                .willReturn(new VideoEntity("video 1", "description 1", "neo")); // this will be returned when saving new video

        // when
        VideoEntity newVideo = mVideoService.create(new NewVideo("new video", "new description"), "new user");

        // then
        assertThat(newVideo.getName()).isEqualTo("video 1");
        assertThat(newVideo.getDescription()).isEqualTo("description 1");
        assertThat(newVideo.getOwner()).isEqualTo("neo");
    }

    @Test
    void deletingVideoShouldWork() {
        // given
        VideoEntity video = new VideoEntity("video 1", "description 1");
        video.setId(1L);

        // when
        when(mRepository.findById(1L)).thenReturn(Optional.of(video));
        mVideoService.delete(1L);

        // then
        verify(mRepository).findById(1L); // check that "findById" is called
        verify(mRepository).delete(video); // check that "delete" is called
    }
}