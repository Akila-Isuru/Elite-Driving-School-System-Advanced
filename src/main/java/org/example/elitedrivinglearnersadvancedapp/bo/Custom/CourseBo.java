package org.example.elitedrivinglearnersadvancedapp.bo.Custom;


import org.example.elitedrivinglearnersadvancedapp.dto.CourseDto;

import java.util.List;

public interface CourseBo {
    void addCourse(CourseDto courseDto);
    CourseDto getCourseById(String id);
    List<CourseDto> getAllCourses();
    void updateCourse(CourseDto courseDto);
    void deleteCourse(String id);
    List<String> getAllCourseNames();
    CourseDto getCourseByName(String name);
    long countAllCourses();

    List<CourseDto> searchCourses(String query);
}