package com.fmi.materials.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
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

    @JsonCreator
    public static CourseGroup fromNode(JsonNode node) {
        if (!node.has("id"))
            return null;

        String id = node.get("id").asText();

        return CourseGroup.valueOf(id);
    }

    @JsonProperty
    public String getId() {
        return name();
    }
}
