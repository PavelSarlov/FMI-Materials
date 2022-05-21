package com.fmi.materials.dto.material;

import com.fmi.materials.model.Material;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MaterialDtoWithData extends MaterialDto {
    private byte[] data;

    public MaterialDtoWithData(Long id, String fileFormat, String fileName, byte[] data) {
        super(id, fileFormat, fileName);
        this.data = data;
    }
}
