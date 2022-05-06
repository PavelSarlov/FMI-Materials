package com.fmi.materials.vo;

import lombok.Getter;

@Getter
public enum FacultyDepartment {
    ALG("Algebra"),
    PORS("Probability, Operations Research and Statistics"),
    GEOM("Geometry"),
    DE("Differential Equations"),
    CS("Computing Systems"),
    IT("Information Technologies"),
    CAT("Complex Analysis and Topology"),
    CI("Computer Informatics"),
    MLA("Mathematical Logic and Applications"),
    MA("Mathematical Analysis"),
    MRM("Mechatronics, Robotics and Mechanics"),
    EMI("Education in Mathematics and Informatics"),
    ST("Software Technologies"),
    NMA("Numerical Methods and Algorithms;");

    String name;

    FacultyDepartment(String name) {
        this.name = name;
    }
}
