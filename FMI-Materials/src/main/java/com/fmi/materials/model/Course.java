package com.fmi.materials.model;

import com.fmi.materials.vo.CourseGroup;
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

    @Column(name = "created_by")
    private String createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_department_id")
    private FacultyDepartment facultyDepartment;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "course_group")
    private CourseGroup courseGroup;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Section> sections;

    public Course() {}

    public Course(String name, String description, String createdBy, FacultyDepartment facultyDepartment, CourseGroup courseGroup, List<Section> sections) {
        this(null, name, description, createdBy, facultyDepartment, courseGroup, sections);
    }

    public Course(Long id, String name, String description, String createdBy, FacultyDepartment facultyDepartment, CourseGroup courseGroup, List<Section> sections) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.facultyDepartment = facultyDepartment;
        this.courseGroup = courseGroup;
        this.sections = (sections != null ? sections.stream().collect(Collectors.toSet()) : null);
    }
}
