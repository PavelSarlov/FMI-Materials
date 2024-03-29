package com.fmi.materials.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CourseList> courseLists;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MaterialRequest> materialRequests;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_user_roles", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false),
    }, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false, updatable = false),
    })
    private Set<UserRole> roles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_favourite_courses", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false),
    }, inverseJoinColumns = {
            @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false, updatable = false)
    })
    private Set<Course> favouriteCourses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Subscription> subscriptions;

    public User() {
    }

    public User(String name, String passwordHash, String email) {
        this(null, name, passwordHash, email);
    }

    public User(String name, String passwordHash, String email, Set<CourseList> courseLists,
            Set<Course> favouriteCourses, Set<Subscription> subscriptions) {
        this(null, name, passwordHash, email, courseLists, favouriteCourses, subscriptions);
    }

    public User(Long id, String name, String passwordHash, String email) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
        this.email = email;
    }

    public User(Long id, String name, String passwordHash, String email, Set<CourseList> courseLists,
            Set<Course> favouriteCourses, Set<Subscription> subscriptions) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
        this.email = email;
        this.courseLists = courseLists;
        this.favouriteCourses = favouriteCourses;
        this.subscriptions = subscriptions;
    }

    public void addCourse(Course course) {
        if (this.favouriteCourses == null) {
            this.favouriteCourses = new HashSet<Course>();
        }
        this.favouriteCourses.add(course);
    }

    public void removeCourse(Course course) {
        if (this.favouriteCourses == null) {
            return;
        }
        this.favouriteCourses.remove(course);
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

    public void addMaterialRequest(MaterialRequest materialRequest) {
        if (this.materialRequests == null) {
            this.materialRequests = new HashSet<MaterialRequest>();
        }
        this.materialRequests.add(materialRequest);
    }

    public void removeMaterialRequest(MaterialRequest materialRequest) {
        if (this.materialRequests == null) {
            return;
        }
        this.materialRequests.remove(materialRequest);
    }

    public void addRole(UserRole role) {
        if (this.roles == null) {
            this.roles = new HashSet<UserRole>(Arrays.asList(role));
            return;
        }
        this.roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
