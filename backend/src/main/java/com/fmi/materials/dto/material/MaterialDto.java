package com.fmi.materials.dto.material;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaterialDto {

    private Long id;
    private String fileFormat;
    private String fileName;

    public MaterialDto(Long id, String fileFormat, String fileName) {
        this.id = id;
        this.fileFormat = fileFormat;
        this.fileName = fileName;
    }
}
