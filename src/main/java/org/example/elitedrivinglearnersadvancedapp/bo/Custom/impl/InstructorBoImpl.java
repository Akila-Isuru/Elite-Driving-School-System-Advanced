package org.example.elitedrivinglearnersadvancedapp.bo.Custom.impl;


import org.example.elitedrivinglearnersadvancedapp.bo.Custom.InstructorBo;
import org.example.elitedrivinglearnersadvancedapp.dto.InstructorDto;

public class InstructorBoImpl implements InstructorBo {

    private final InstructorDao instructorDao = new InstructorDaoImpl();

    @Override
    public void addInstructor(InstructorDto instructorDto) {
        instructorDao.save(mapToEntity(instructorDto));
    }

    @Override
    public void addInstructor(InstructorDto instructorDto) {

    }

    @Override
    public InstructorDto getInstructorById(Integer id) {
        return instructorDao.findById(id).map(this::mapToDto).orElse(null);
    }

    @Override
    public List<InstructorDto> getAllInstructors() {
        return instructorDao.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void updateInstructor(InstructorDto instructorDto) {
        Instructor instructor = mapToEntity(instructorDto);
        instructor.setInstructorId(instructorDto.getInstructorId());
        instructorDao.update(instructor);
    }

    @Override
    public void deleteInstructor(Integer id) {
        instructorDao.findById(id).ifPresent(instructorDao::delete);
    }

    @Override
    public List<InstructorDto> getInstructorsBySpecialization(String specialization) {
        return instructorDao.findAll().stream()
                .filter(i -> specialization.equals(i.getSpecialization()))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private InstructorDto mapToDto(Instructor instructor) {
        return new InstructorDto(
                instructor.getInstructorId(),
                instructor.getName(),
                instructor.getContactNumber(),
                instructor.getEmail(),
                instructor.getSpecialization()
        );
    }

    private Instructor mapToEntity(InstructorDto instructorDto) {
        return new Instructor(
                instructorDto.getInstructorId(),
                instructorDto.getName(),
                instructorDto.getContactNumber(),
                instructorDto.getEmail(),
                instructorDto.getSpecialization()
        );
    }
}