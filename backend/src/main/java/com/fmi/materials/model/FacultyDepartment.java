package com.fmi.materials.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

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

    public FacultyDepartment() {
    }

    public FacultyDepartment(String name) {
        this(null, name);
    }

    public FacultyDepartment(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
