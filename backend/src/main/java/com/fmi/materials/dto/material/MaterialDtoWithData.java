package com.fmi.materials.dto.material;

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
public class MaterialDtoWithData extends MaterialDto {
    @NotNull
    @SizeByte(max = 1000000000, message = "Material size should be maximum 1GB.")
    private byte[] data;

    public MaterialDtoWithData(Long id, String fileFormat, String fileName, byte[] data) {
        super(id, fileFormat, fileName);
        this.data = data;
    }
}
