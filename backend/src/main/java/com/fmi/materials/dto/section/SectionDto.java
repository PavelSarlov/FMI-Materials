package com.fmi.materials.dto.section;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fmi.materials.dto.material.MaterialDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionDto {

    @NotNull
    private Long id;
    @Size(min=4,max=50, message="Section name should be between 4 and 50 characters")
    private String name;
    private List<MaterialDto> materials;

    public SectionDto(Long id, String name, List<MaterialDto> materialDtos) {
        this.id = id;
        this.name = name;
        this.materials = materialDtos;
    }
}
