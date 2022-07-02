package com.fmi.materials.dto.section;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.materialrequest.MaterialRequestDto;
import com.fmi.materials.validator.SizeByteString;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionDto {

    private Long id;
    @SizeByteString(min = 1, max = 50, message = "Section name should be between 1 and 50 bytes")
    private String name;
    private List<MaterialDto> materialDtos;
    private List<MaterialRequestDto> materialRequestDtos;

    public SectionDto(Long id, String name, List<MaterialDto> materialDtos,
            List<MaterialRequestDto> materialRequestDtos) {
        this.id = id;
        this.name = name;
        this.materialDtos = materialDtos;
        this.materialRequestDtos = materialRequestDtos;
    }
}
