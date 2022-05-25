package com.fmi.materials.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user_courses_lists")
public class CourseList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "list_name")
    private String listName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "courses__user_courses_lists",
            joinColumns = {
                    @JoinColumn(name = "user_courses_list_id", referencedColumnName = "id", nullable = false, updatable = false),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false, updatable = false),
            })
    private Set<Course> courses;

    public CourseList() {}

    public CourseList(String listName, Set<Course> courses) {
        this(null, listName, courses);
    }

    public CourseList(Long id, String listName, Set<Course> courses) {
        this.id = id;
        this.listName = listName;
        this.courses = courses;
    }

    public void addCourse(Course course) {
        if (this.courses == null) {
            this.courses = new HashSet<Course>();
        }
        this.courses.add(course);
    }

    public void removeCourse(Course course) {
        if (this.courses == null) {
            return;
        }
        this.courses.remove(course);
    }
}
