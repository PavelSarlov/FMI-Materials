package com.fmi.materials.dto.materialrequest;

import com.fmi.materials.dto.section.SectionDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MaterialRequestDto {
    private Long id;
    private String fileFormat;
    private String fileName;
    private SectionDto sectionDto;

    public MaterialRequestDto(Long id, String fileFormat, String fileName, SectionDto sectionDto) {
        this.id = id;
        this.fileFormat = fileFormat;
        this.fileName = fileName;
        this.sectionDto = sectionDto;
    }
}
