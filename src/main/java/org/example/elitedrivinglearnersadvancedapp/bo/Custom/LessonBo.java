package org.example.elitedrivinglearnersadvancedapp.bo.Custom;

import org.example.elitedrivinglearnersadvancedapp.dto.LessonDto;

import java.util.List;

public interface LessonBo {
    void scheduleLesson(LessonDto lessonDto) throws SchedulingConflictException;
    void rescheduleLesson(Integer lessonId, LessonDto lessonDto) throws SchedulingConflictException;
    void cancelLesson(Integer lessonId);
    List<LessonDto> getAllLessons();

}