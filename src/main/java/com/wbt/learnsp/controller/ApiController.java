package com.wbt.learnsp.controller;

import com.wbt.learnsp.model.VideoEntity;
import com.wbt.learnsp.service.VideoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {
    private final VideoService mVideoService;

    public ApiController(VideoService videoService) {
        mVideoService = videoService;
    }

    @GetMapping(path = {"/api/videos"})
    public List<VideoEntity> all() {
        return mVideoService.getVideos();
    }


}
