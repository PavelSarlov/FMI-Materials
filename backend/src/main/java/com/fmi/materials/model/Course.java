package com.fmi.materials.model;

import com.fmi.materials.vo.CourseGroup;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Section> sections;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CourseList> courseLists;

    @ManyToMany(mappedBy = "favouriteCourses", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> users;

    public Course() {}

    public Course(String name, String description, String createdBy, FacultyDepartment facultyDepartment, CourseGroup courseGroup) {
        this(null, name, description, createdBy, facultyDepartment, courseGroup);
    }

    public Course(Long id, String name, String description, String createdBy, FacultyDepartment facultyDepartment, CourseGroup courseGroup) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.facultyDepartment = facultyDepartment;
        this.courseGroup = courseGroup;
    }

    public void addCourseList(CourseList courseList) {
        if (this.courseLists == null) {
            this.courseLists = new HashSet<CourseList>();
        }
        this.courseLists.add(courseList);
    }

    public void removeCourseList(CourseList courseList) {
        if (this.courseLists == null) {
            return;
        }
        this.courseLists.remove(courseList);
    }

    public void addUser(User user) {
        if (this.users == null) {
            this.users = new HashSet<User>();
        }
        this.users.add(user);
    }

    public void removeUser(User user) {
        if (this.users == null) {
            return;
        }
        this.users.remove(user);
    }
}
