package com.wbt.learnsp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VideoEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String owner;

    public VideoEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public VideoEntity(String name, String description, String owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VideoEntity that = (VideoEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
