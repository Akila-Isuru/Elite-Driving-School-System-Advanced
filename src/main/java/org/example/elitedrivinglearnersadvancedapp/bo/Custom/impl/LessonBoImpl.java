package org.example.elitedrivinglearnersadvancedapp.bo.Custom.impl;


import edu.icet.elite.bo.custom.LessonBo;
import edu.icet.elite.dao.custom.CourseDao;
import edu.icet.elite.dao.custom.InstructorDao;
import edu.icet.elite.dao.custom.LessonDao;
import edu.icet.elite.dao.custom.StudentDao;
import edu.icet.elite.dao.custom.impl.CourseDaoImpl;
import edu.icet.elite.dao.custom.impl.InstructorDaoImpl;
import edu.icet.elite.dao.custom.impl.LessonDaoImpl;
import edu.icet.elite.dao.custom.impl.StudentDaoImpl;
import edu.icet.elite.dto.LessonDto;
import edu.icet.elite.entity.Course;
import edu.icet.elite.entity.Instructor;
import edu.icet.elite.entity.Lesson;
import edu.icet.elite.entity.Student;
import edu.icet.elite.exception.SchedulingConflictException;

import java.util.List;
import java.util.stream.Collectors;

public class LessonBoImpl implements LessonBo {

    private final LessonDao lessonDao = new LessonDaoImpl();
    private final StudentDao studentDao = new StudentDaoImpl();
    private final InstructorDao instructorDao = new InstructorDaoImpl();
    private final CourseDao courseDao = new CourseDaoImpl();

    @Override
    public void scheduleLesson(LessonDto lessonDto) throws SchedulingConflictException {

        Student student = studentDao.findById(lessonDto.getStudentId())
                .orElseThrow(() -> new SchedulingConflictException("Student not found."));
        Instructor instructor = instructorDao.findById(lessonDto.getInstructorId())
                .orElseThrow(() -> new SchedulingConflictException("Instructor not found."));
        Course course = courseDao.findById(lessonDto.getCourseId())
                .orElseThrow(() -> new SchedulingConflictException("Course not found."));


        if (lessonDao.isInstructorBusy(instructor.getInstructorId(), lessonDto.getScheduledTime(), course.getDuration())) {
            throw new SchedulingConflictException("The selected instructor is busy at the scheduled time.");
        }

        // Create and save the new lesson
        Lesson lesson = new Lesson();
        lesson.setStudent(student);
        lesson.setInstructor(instructor);
        lesson.setCourse(course);
        lesson.setScheduledTime(lessonDto.getScheduledTime());
        lesson.setStatus("SCHEDULED");

        lessonDao.save(lesson);
    }

    @Override
    public void rescheduleLesson(Integer lessonId, LessonDto lessonDto) throws SchedulingConflictException {
        Lesson lesson = lessonDao.findById(lessonId)
                .orElseThrow(() -> new SchedulingConflictException("Lesson not found to reschedule."));


        Student student = studentDao.findById(lessonDto.getStudentId())
                .orElseThrow(() -> new SchedulingConflictException("Student not found."));
        Instructor instructor = instructorDao.findById(lessonDto.getInstructorId())
                .orElseThrow(() -> new SchedulingConflictException("Instructor not found."));
        Course course = courseDao.findById(lessonDto.getCourseId())
                .orElseThrow(() -> new SchedulingConflictException("Course not found."));


        if (lessonDao.isInstructorBusy(instructor.getInstructorId(), lessonDto.getScheduledTime(), course.getDuration(), lessonId)) {
            throw new SchedulingConflictException("The selected instructor is busy at the new scheduled time.");
        }


        lesson.setStudent(student);
        lesson.setInstructor(instructor);
        lesson.setCourse(course);
        lesson.setScheduledTime(lessonDto.getScheduledTime());
        lesson.setStatus("SCHEDULED");

        lessonDao.update(lesson);
    }

    @Override
    public void cancelLesson(Integer lessonId) {
        lessonDao.findById(lessonId).ifPresent(lesson -> {
            lesson.setStatus("CANCELED");
            lessonDao.update(lesson);
        });
    }

    @Override
    public List<LessonDto> getAllLessons() {
        return lessonDao.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }


    private LessonDto mapToDto(Lesson lesson) {
        return new LessonDto(
                lesson.getLessonId(),
                lesson.getStudent().getStudentId(),
                lesson.getInstructor().getInstructorId(),
                lesson.getCourse().getCourseId(),
                lesson.getScheduledTime(),
                lesson.getStatus()
        );
    }
}