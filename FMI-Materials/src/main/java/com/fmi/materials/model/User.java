package com.fmi.materials.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user")
    private List<CourseList> courseLists;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "courses__user_courses_lists",
            joinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false, updatable = false),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_courses_list_id", referencedColumnName = "id", nullable = false, updatable = false),
            })
    private Set<UserRole> roles;

    public User() {}

    public User(String name, String passwordHash, String email) {
        this(null, name, passwordHash, email);
    }

    public User(Long id, String name, String passwordHash, String email) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
        this.email = email;
    }
}
