package com.fmi.materials.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "materials")
public class Material {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

    public Material() {
    }

    public Material(String fileFormat, String fileName, byte[] data, Section section) {
        this(null, fileFormat, fileName, data, section);
    }

    public Material(Long id, String fileFormat, String fileName, byte[] data, Section section) {
        this.id = id;
        this.fileFormat = fileFormat;
        this.fileName = fileName;
        this.data = data;
        this.section = section;
    }
}
