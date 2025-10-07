package org.example.elitedrivinglearnersadvancedapp.bo.Custom;

import org.example.elitedrivinglearnersadvancedapp.dto.InstructorDto;

import java.util.List;

public interface InstructorBo {
    void addInstructor(InstructorDto instructorDto);
    InstructorDto getInstructorById(Integer id);
    List<InstructorDto> getAllInstructors();
    void updateInstructor(InstructorDto instructorDto);
    void deleteInstructor(Integer id);
    List<InstructorDto> getInstructorsBySpecialization(String specialization);
}