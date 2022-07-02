package com.fmi.materials.dto.materialrequest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fmi.materials.dto.user.UserDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaterialRequestDtoWithCourseId extends MaterialRequestDto {

    private Long courseId;

    public MaterialRequestDtoWithCourseId(Long id, String fileFormat, String fileName, UserDto userDto, Long sectionId,
            Long courseId) {
        super(id, fileFormat, fileName, userDto, sectionId);
        this.courseId = courseId;
    }
}
