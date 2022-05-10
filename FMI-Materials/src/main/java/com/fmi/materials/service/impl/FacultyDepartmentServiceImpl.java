package com.fmi.materials.service.impl;

import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.mapper.FacultyDepartmentDtoMapper;
import com.fmi.materials.model.FacultyDepartment;
import com.fmi.materials.repository.FacultyDepartmentRepository;
import com.fmi.materials.service.FacultyDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FacultyDepartmentServiceImpl implements FacultyDepartmentService {
    private final String NOT_FOUND_MESSAGE = "Department with ID = '%s' not found";

    @Autowired
    FacultyDepartmentRepository facultyDepartmentRepository;
    @Autowired
    FacultyDepartmentDtoMapper facultyDepartmentDtoMapper;

    @Override
    public FacultyDepartmentDto createFacultyDepartment(FacultyDepartmentDto facultyDepartmentDto) {
        FacultyDepartment facultyDepartment = this.facultyDepartmentDtoMapper.convertToEntity(facultyDepartmentDto);
        return this.facultyDepartmentDtoMapper.convertToDto(this.facultyDepartmentRepository.save(facultyDepartment));
    }

    @Override
    public void deleteFacultyDepartment(Long facultyDepartmentId) {
        if(!this.facultyDepartmentRepository.existsById(facultyDepartmentId)) {
            throw new NoSuchElementException(String.format(NOT_FOUND_MESSAGE, facultyDepartmentId));
        }
        this.facultyDepartmentRepository.deleteById(facultyDepartmentId);
    }

    @Override
    public FacultyDepartmentDto updateFacultyDepartment(FacultyDepartmentDto facultyDepartmentDto) {
        if(!this.facultyDepartmentRepository.existsById(facultyDepartmentDto.getId())) {
            throw new NoSuchElementException(String.format(NOT_FOUND_MESSAGE, facultyDepartmentDto.getId()));
        }
        FacultyDepartment facultyDepartment = this.facultyDepartmentDtoMapper.convertToEntity(facultyDepartmentDto);
        return this.facultyDepartmentDtoMapper.convertToDto(this.facultyDepartmentRepository.save(facultyDepartment));
    }

    @Override
    public FacultyDepartmentDto findById(Long facultyDepartmentId) {
        return this.facultyDepartmentDtoMapper.convertToDto(this.facultyDepartmentRepository.findById(facultyDepartmentId)
                .orElseThrow(() -> new NoSuchElementException(String.format(NOT_FOUND_MESSAGE, facultyDepartmentId))));
    }

    @Override
    public List<FacultyDepartmentDto> findAllFacultyDepartments() {
        return this.facultyDepartmentRepository.findAll().stream()
                .map(this.facultyDepartmentDtoMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
