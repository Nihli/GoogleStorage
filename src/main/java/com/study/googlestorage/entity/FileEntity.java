package com.study.googlestorage.entity;

import org.apache.tomcat.jni.Library;

import javax.persistence.*;

@Entity
@Table(name = "file")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    @Column(name = "file_name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", user=" + user +
                ", name='" + name + '\'' +
                '}';
    }
}
