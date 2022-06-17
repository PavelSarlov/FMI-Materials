package com.fmi.materials.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "sections")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Material> materials;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MaterialRequest> materialRequests;

    public Section() {}

    public Section(String name, Course course, List<Material> materials, List<MaterialRequest> materialRequests) {
        this(null, name, course, materials, materialRequests);
    }

    public Section(Long id, String name, Course course, List<Material> materials, List<MaterialRequest> materialRequests) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.materials =  (materials != null ? materials.stream().collect(Collectors.toSet()) : null);
        this.materialRequests =  (materialRequests != null ? materialRequests.stream().collect(Collectors.toSet()) : null);
    }

    public void addMaterialRequest(MaterialRequest materialRequest) {
        if (this.materialRequests == null) {
            this.materialRequests = new HashSet<MaterialRequest>();
        }
        this.materialRequests.add(materialRequest);
    }

    public void removeMaterialRequest(MaterialRequest materialRequest) {
        if (this.materialRequests == null) {
            return;
        }
        this.materialRequests.remove(materialRequest);
    }
}
