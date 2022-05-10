package com.fmi.materials.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "faculty_departments")
public class FacultyDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "facultyDepartment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Course> courses;

    public FacultyDepartment() {}

    public FacultyDepartment(String name) {
        this(null, name);
    }

    public FacultyDepartment(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
