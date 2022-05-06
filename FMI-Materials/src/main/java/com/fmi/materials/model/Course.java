package com.fmi.materials.model;

import com.fmi.materials.vo.CourseGroup;
import com.fmi.materials.vo.FacultyDepartment;
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
@Table(name = "Courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @Column(name = "Description", length = 255)
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "FacultyDepartment")
    private FacultyDepartment facultyDepartment;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "CourseGroup")
    private CourseGroup courseGroup;

    public Course() {}

    public Course(String name, String description, FacultyDepartment facultyDepartment, CourseGroup courseGroup) {
        this(null, name, description, facultyDepartment, courseGroup);
    }

    public Course(Long id, String name, String description, FacultyDepartment facultyDepartment, CourseGroup courseGroup) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.facultyDepartment = facultyDepartment;
        this.courseGroup = courseGroup;
    }
}
