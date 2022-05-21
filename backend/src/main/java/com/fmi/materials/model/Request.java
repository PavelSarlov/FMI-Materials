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

    @Column(name = "file_format")
    private String fileFormat;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "data")
    private byte[] data;

    @Column(name = "section_id")
    private String sectionId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id")
    private Section section;

    public Request() {
    }

    public Request(String fileFormat, String fileName, byte[] data, String sectionId, User user, Section section) {
        this(null, fileFormat, fileName, data, sectionId, user, section);
    }

    public Request(Long id, String fileFormat, String fileName, byte[] data, String sectionId, User user, Section section) {
        this.id = id;
        this.fileFormat =fileFormat;
        this.fileName = fileName;
        this.data = data;
        this.sectionId = sectionId;
        this.user = user;
        this.section = section;
    }
}
