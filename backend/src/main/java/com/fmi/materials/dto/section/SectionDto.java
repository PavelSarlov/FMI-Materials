package com.fmi.materials.dto.section;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fmi.materials.dto.material.MaterialDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionDto {

    private Long id;
    private String name;
    private List<MaterialDto> materials;

    public SectionDto(Long id, String name, List<MaterialDto> materialDtos) {
        this.id = id;
        this.name = name;
        this.materials = materialDtos;
    }
}
