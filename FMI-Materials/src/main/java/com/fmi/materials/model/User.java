package com.fmi.materials.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "password", length = 255, nullable = false)
    private String passwordHash;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<CourseList> courseLists;

    public User() {
    }

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
