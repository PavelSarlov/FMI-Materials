package com.fmi.materials.dto.section;

import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.model.Course;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SectionDto {

    private Long id;
    private String name;
    private Course course;
    private List<MaterialDto> materials;

    public SectionDto(Long id, String name, Course course, List<MaterialDto> materialDtos) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.materials = materialDtos;
    }
}
