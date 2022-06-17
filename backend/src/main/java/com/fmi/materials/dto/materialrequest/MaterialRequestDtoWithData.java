package com.fmi.materials.dto.materialrequest;

import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.validator.SizeByte;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MaterialRequestDtoWithData extends  MaterialRequestDto{
    @NotNull
    @SizeByte(max = 1000000000, message = "Material size should be maximum 1GB.")
    private byte[] data;

    public MaterialRequestDtoWithData(Long id, String fileFormat, String fileName, UserDto userDto, Long sectionId, byte[] data) {
        super(id, fileFormat, fileName, userDto, sectionId);
        this.data = data;
    }
}
