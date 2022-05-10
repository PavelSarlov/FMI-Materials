package com.fmi.materials.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public CourseList() {}

    public CourseList(Long id, String listName) {
        this.id = id;
        this.listName = listName;
    }
}
