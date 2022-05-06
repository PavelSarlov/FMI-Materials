package com.fmi.materials.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "Materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @Column(name = "FileType", nullable = false, length = 10)
    private String fileType;

    @Column(name = "Data", nullable = false)
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SectionId", nullable = false)
    private Section section;

    public Material() {}

    public Material(String fileType, byte[] data, Section section) {
        this(null, fileType, data, section);
    }

    public Material(Long id, String fileType, byte[] data, Section section) {
        this.id = id;
        this.fileType = fileType;
        this.data = data;
        this.section = section;
    }
}
