package com.wbt.learnsp.controller;

import com.wbt.learnsp.model.NewVideo;
import com.wbt.learnsp.model.UniversalSearch;
import com.wbt.learnsp.model.VideoEntity;
import com.wbt.learnsp.model.VideoSearch;
import com.wbt.learnsp.service.VideoService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class GreetingController {

    private final VideoService mVideoService;

    public GreetingController(VideoService videoService) {
        mVideoService = videoService;
    }

    @GetMapping
    public String greet(final Model model, final Authentication authentication) {
        model.addAttribute("videos", mVideoService.getVideos());
        model.addAttribute("authentication", authentication);
        return "index";
    }

    @PostMapping(path = {"/new-video"})
    public String newVideo(final @ModelAttribute NewVideo newVideo, final Authentication auth) {
        mVideoService.create(newVideo, auth.getName());
        return "redirect:/";
    }

    @PostMapping(path = {"/delete/videos/{videoId}"})
    public String deleteMovie(final @PathVariable Long videoId) {
        mVideoService.delete(videoId);
        return "redirect:/";
    }

    @GetMapping(path = {"/react"})
    public String react() {
        return "react";
    }

    @PostMapping(path = {"/multi-field-search"})
    public String multiFieldSearch(final @ModelAttribute VideoSearch search, final Model model) {
        List<VideoEntity> videos = mVideoService.search(search);
        model.addAttribute("videos", videos);
        return "index";
    }

    @PostMapping(path = {"/universal-search"})
    public String universalSearch(final @ModelAttribute UniversalSearch search, final Model model) {
        List<VideoEntity> searchResults = mVideoService.search(search);
        model.addAttribute("videos", searchResults);
        return "index";
    }
}
