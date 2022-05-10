package com.fmi.materials.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "user_courses_lists")
public class CourseList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "list_name")
    private String listName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "courses__user_courses_lists",
            joinColumns = {
                    @JoinColumn(name = "user_courses_list_id", referencedColumnName = "id", nullable = false, updatable = false),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false, updatable = false),
            })
    private Set<Course> courses = new HashSet<Course>();

    public CourseList() {}

    public CourseList(String listName) {
        this(null, listName);
    }

    public CourseList(Long id, String listName) {
        this.id = id;
        this.listName = listName;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }
}
