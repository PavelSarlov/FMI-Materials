package com.fmi.materials.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
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
    NMA("Numerical Methods and Algorithms");

    String name;

    FacultyDepartment(String name) {
        this.name = name;
    }

    @JsonCreator
    public static FacultyDepartment fromNode(JsonNode node) {
        if(!node.has("id"))
            return null;

        String id = node.get("id").asText();

        return FacultyDepartment.valueOf(id);
    }

    @JsonProperty
    public String getId() {
        return name();
    }
}
