package com.wbt.learnsp.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VideoEntityTest {

    @Test
    void newVideoEntityShouldHaveNullId() {
        VideoEntity video = new VideoEntity("title", "description", "nero");
        assertThat(video.getId()).isNull();
        assertThat(video.getName()).isEqualTo("title");
        assertThat(video.getOwner()).isEqualTo("nero");
        assertThat(video.getDescription()).isEqualTo("description");
    }

    @Test
    void toStringShouldAoBeTested() {
        VideoEntity video = new VideoEntity("title", "description", "nero");
        assertThat(video.toString()).isEqualTo("VideoEntity(id=null, name=title, description=description, owner=nero)");
    }

    @Test
    void hashCodeShouldNotBeNull() {
        VideoEntity video = new VideoEntity("title", "description", "nero");
        assertThat(video.hashCode()).isNotNull();
    }

    @Test
    void settersShouldMutateField() {
        VideoEntity video = new VideoEntity("title", "description", "nero");
        video.setId(1L);
        video.setName("other name");
        video.setDescription("other description");
        video.setOwner("other owner");
        assertThat(video.getId()).isEqualTo(1L);
        assertThat(video.getName()).isEqualTo("other name");
        assertThat(video.getDescription()).isEqualTo("other description");
        assertThat(video.getOwner()).isEqualTo("other owner");
    }
}