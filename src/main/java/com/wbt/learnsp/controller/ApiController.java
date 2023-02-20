package com.wbt.learnsp.controller;

import com.wbt.learnsp.model.Video;
import com.wbt.learnsp.service.VideoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {
    private final VideoService mVideoService;

    public ApiController(VideoService videoService) {
        mVideoService = videoService;
    }

    @GetMapping(path = {"/api/videos"})
    public List<Video> all() {
        return mVideoService.getVideos();
    }

    @PostMapping(path = {"/api/videos"})
    public Video add(final @RequestBody Video video) {
        return mVideoService.create(video);
    }
}
