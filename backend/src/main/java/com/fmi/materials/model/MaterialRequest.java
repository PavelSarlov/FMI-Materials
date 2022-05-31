package com.fmi.materials.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "material_requests")
public class MaterialRequest {
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "section_id")
    private Section section;

    public MaterialRequest() {
    }

    public MaterialRequest(String fileFormat, String fileName, byte[] data, User user, Section section) {
        this(null, fileFormat, fileName, data, user, section);
    }

    public MaterialRequest(Long id, String fileFormat, String fileName, byte[] data, User user, Section section) {
        this.id = id;
        this.fileFormat =fileFormat;
        this.fileName = fileName;
        this.data = data;
        this.user = user;
        this.section = section;
    }
}
