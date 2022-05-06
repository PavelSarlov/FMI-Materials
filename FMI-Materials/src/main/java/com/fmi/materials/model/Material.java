package com.fmi.materials.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_format")
    private String fileFormat;

    @Column(name = "data")
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

    public Material() {}

    public Material(String fileFormat, byte[] data, Section section) {
        this(null, fileFormat, data, section);
    }

    public Material(Long id, String fileFormat, byte[] data, Section section) {
        this.id = id;
        this.fileFormat = fileFormat;
        this.data = data;
        this.section = section;
    }
}
