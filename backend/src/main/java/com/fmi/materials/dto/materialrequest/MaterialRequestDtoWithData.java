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
public class MaterialRequestDtoWithData extends  MaterialRequestDto{
    private byte[] data;

    public MaterialRequestDtoWithData(Long id, String fileFormat, String fileName, SectionDto sectionDto, byte[] data) {
        super(id, fileFormat, fileName, sectionDto);
        this.data = data;
    }
}
