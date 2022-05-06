package com.fmi.materials.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "Sections")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CourseId", nullable = false)
    private Course course;

    public Section() {}

    public Section(String name, Course course) {
        this(null, name, course);
    }

    public Section(Long id, String name, Course course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }
}
