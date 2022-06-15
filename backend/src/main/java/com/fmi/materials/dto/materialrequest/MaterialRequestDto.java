package com.fmi.materials.dto.materialrequest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fmi.materials.dto.user.UserDto;
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
public class MaterialRequestDto {
    private Long id;
    @SizeByteString(max=255, min=1, message="File format should be between 1 and 255 bytes")
    private String fileFormat;
    @SizeByteString(max=100, min=1, message="File name should be between 1 and 100 bytes")
    private String fileName;
    private UserDto userDto;
    private Long sectionId;

    public MaterialRequestDto(Long id, String fileFormat, String fileName, UserDto userDto, Long sectionId) {
        this.id = id;
        this.fileFormat = fileFormat;
        this.fileName = fileName;
        this.userDto = userDto;
        this.sectionId = sectionId;
    }
}
