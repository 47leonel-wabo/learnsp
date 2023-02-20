package com.wbt.learnsp.controller;

import com.wbt.learnsp.model.Video;
import com.wbt.learnsp.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GreetingController {

    private final VideoService mVideoService;

    public GreetingController(VideoService videoService) {
        mVideoService = videoService;
    }

    @GetMapping(path = {"/"})
    public String greet(final Model model) {
        model.addAttribute("videos", mVideoService.getVideos());
        return "index";
    }

    @PostMapping(path = {"/new-video"})
    public String newVideo(final @ModelAttribute Video video) {
        mVideoService.create(video);
        return "redirect:/";
    }

    @GetMapping(path = {"/react"})
    public String react() {
        return "react";
    }
}
