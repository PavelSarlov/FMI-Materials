package com.fmi.materials.vo;

import lombok.Getter;

@Getter
public enum CourseGroup {
    OTHER("Other"),
    CSF("Computer Science Fundamentals"),
    CSC("Computer Science Core"),
    CSP("Computer Science Practicum"),
    MAT("Mathematics"),
    AM("Applied Mathematics");

    String name;

    CourseGroup(String name) {
        this.name = name;
    }
}
