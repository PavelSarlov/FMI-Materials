package com.fmi.materials.dto.courselist;

import com.fmi.materials.model.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CourseListDto {
    private Long id;
    private String listName;

    public CourseListDto(Long id, String listName) {
        this.id = id;
        this.listName = listName;
    }
}
