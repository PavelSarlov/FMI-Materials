package com.fmi.materials.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "sections")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Material> materials;

    public Section() {}

    public Section(String name, Course course, List<Material> materials) {
        this(null, name, course, materials);
    }

    public Section(Long id, String name, Course course, List<Material> materials) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.materials =  (materials != null ? materials.stream().collect(Collectors.toSet()) : null);
    }
}
