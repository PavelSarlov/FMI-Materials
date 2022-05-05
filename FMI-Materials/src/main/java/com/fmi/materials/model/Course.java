package com.fmi.materials.model;

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

    @Column(name = "GroupId")
    private Long groupId;

    public Course() {}

    public Course(String name, String description, Long groupId) {
        this(null, name, description, groupId);
    }

    public Course(Long id, String name, String description, Long groupId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.groupId = groupId;
    }
}
