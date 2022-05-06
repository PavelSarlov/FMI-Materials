package com.fmi.materials.model;

import com.fmi.materials.vo.CourseGroup;
import com.fmi.materials.vo.FacultyDepartment;
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
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "faculty_department")
    private FacultyDepartment facultyDepartment;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "course_group")
    private CourseGroup courseGroup;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Section> sections;

    public Course() {}

    public Course(String name, String description, FacultyDepartment facultyDepartment, CourseGroup courseGroup, List<Section> sections) {
        this(null, name, description, facultyDepartment, courseGroup, sections);
    }

    public Course(Long id, String name, String description, FacultyDepartment facultyDepartment, CourseGroup courseGroup, List<Section> sections) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.facultyDepartment = facultyDepartment;
        this.courseGroup = courseGroup;
        this.sections = sections.stream().collect(Collectors.toSet());
    }
}
