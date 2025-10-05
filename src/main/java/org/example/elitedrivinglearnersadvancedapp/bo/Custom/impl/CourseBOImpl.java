package org.example.elitedrivinglearnersadvancedapp.bo.Custom.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.example.elitedrivinglearnersadvancedapp.bo.Custom.CourseBo;
import org.example.elitedrivinglearnersadvancedapp.dto.CourseDto;
import org.example.elitedrivinglearnersadvancedapp.


public class CourseBoImpl implements CourseBo {
    private final CourseDao courseDao = new CourseDaoImpl();

    @Override
    public void addCourse(CourseDto courseDto) {
        String nextId = generateNextCourseId();
        Course course = new Course(nextId, courseDto.getCourseName(), courseDto.getDuration(), courseDto.getFee());
        courseDao.save(course);
    }

    @Override
    public CourseDto getCourseById(String id) {
        Optional<Course> courseOptional = courseDao.findById(id);
        return courseOptional.map(this::mapCourseToDto).orElse(null);
    }

    @Override
    public List<CourseDto> getAllCourses() {
        return courseDao.findAll().stream().map(this::mapCourseToDto).collect(Collectors.toList());
    }

    @Override
    public void updateCourse(CourseDto courseDto) {
        Course course = new Course(courseDto.getCourseId(), courseDto.getCourseName(), courseDto.getDuration(), courseDto.getFee());
        courseDao.update(course);
    }

    @Override
    public void deleteCourse(String id) {
        Optional<Course> courseOptional = courseDao.findById(id);
        courseOptional.ifPresent(courseDao::delete);
    }

    @Override
    public List<String> getAllCourseNames() {
        return courseDao.findAll().stream().map(Course::getCourseName).collect(Collectors.toList());
    }

    @Override
    public CourseDto getCourseByName(String name) {
        return courseDao.findAll().stream()
                .filter(course -> course.getCourseName().equalsIgnoreCase(name))
                .map(this::mapCourseToDto)
                .findFirst()
                .orElse(null);
    }

    @Override
    public long countAllCourses() {
        return courseDao.countAllCourses();
    }

    @Override
    public List<CourseDto> searchCourses(String query) {
        String lowerCaseQuery = query.toLowerCase();
        return courseDao.findAll().stream()
                .filter(course ->
                        course.getCourseId().toLowerCase().contains(lowerCaseQuery) ||
                                course.getCourseName().toLowerCase().contains(lowerCaseQuery) ||
                                course.getDuration().toLowerCase().contains(lowerCaseQuery) ||
                                String.valueOf(course.getFee()).contains(lowerCaseQuery)
                )
                .map(this::mapCourseToDto)
                .collect(Collectors.toList());
    }

    private CourseDto mapCourseToDto(Course course) {
        return new CourseDto(
                course.getCourseId(),
                course.getCourseName(),
                course.getDuration(),
                course.getFee()
        );
    }

    private String generateNextCourseId() {
        List<Course> allCourses = courseDao.findAll();
        if (allCourses.isEmpty()) {
            return "C1001";
        }

        Optional<Integer> maxNumber = allCourses.stream()
                .map(Course::getCourseId)
                .map(id -> {
                    Pattern pattern = Pattern.compile("C(\\d+)");
                    Matcher matcher = pattern.matcher(id);
                    if (matcher.matches()) {
                        return Integer.parseInt(matcher.group(1));
                    }
                    return 0;
                })
                .max(Integer::compare);

        int nextNumber = maxNumber.orElse(1000) + 1;
        return "C" + nextNumber;
    }
}