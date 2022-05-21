package com.fmi.materials.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "data")
    private byte[] data;

    @Column(name = "section_name")
    private String sectionName;

    @Column(name = "section_id")
    private String courseId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Request() {
    }

    public Request(String fileName, byte[] data, String sectionName, String courseId, User user) {
        this(null, fileName, data, sectionName, courseId, user);
    }

    public Request(Long id, String fileName, byte[] data, String sectionName, String courseId, User user) {
        this.id = id;
        this.fileName = fileName;
        this.data = data;
        this.sectionName = sectionName;
        this.courseId = courseId;
        this.user = user;
    }
}
