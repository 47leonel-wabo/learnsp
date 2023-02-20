package com.wbt.learnsp.service;

import com.wbt.learnsp.model.Video;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {
    List<Video> mVideos = List.of(
            new Video("Hands on spring boot 3"),
            new Video("Hard coded values"),
            new Video("Not a good habit")
    );

    public List<Video> getVideos() {
        return mVideos;
    }

    public Video create(final Video video) {
        ArrayList<Video> videoArrayList = new ArrayList<>(mVideos);
        videoArrayList.add(video);
        this.mVideos = List.copyOf(videoArrayList);
        return video;
    }
}
