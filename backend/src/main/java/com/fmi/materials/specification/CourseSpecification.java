package com.fmi.materials.specification;

import java.util.List;
import java.util.Locale;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.fmi.materials.model.Course;

import org.springframework.data.jpa.domain.Specification;

public class CourseSpecification implements Specification<Course> {

    private String columnName;
    private List<String> keywords;

    public CourseSpecification(String columnName, List<String> keywords) {
        this.columnName = columnName;
        this.keywords = keywords;
    }

    @Override
    public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> criteria, CriteriaBuilder builder) {
        Predicate result = null;
        for (String word : keywords) {
            if (result != null) {
                result = builder.and(result, builder.like(builder.lower(root.<String>get(this.columnName)),
                        "%" + word.toLowerCase(Locale.ROOT) + "%"));
            } else {
                result = builder.like(builder.lower(root.<String>get(this.columnName)),
                        "%" + word.toLowerCase(Locale.ROOT) + "%");
            }
        }
        return result;
    }
}
