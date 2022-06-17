package com.fmi.materials.dto.material;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fmi.materials.validator.SizeByteString;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaterialDto {

    private Long id;
    @NotNull
    @SizeByteString(max=255, min=1, message="File format should be between 1 and 255 bytes")
    private String fileFormat;
    @NotNull
    @SizeByteString(max=100, min=1, message="File name should be between 1 and 100 bytes")
    private String fileName;

    public MaterialDto(Long id, String fileFormat, String fileName) {
        this.id = id;
        this.fileFormat = fileFormat;
        this.fileName = fileName;
    }
}
