package com.mmbeautyschool.mmapi.service;


import com.mmbeautyschool.mmapi.entity.Course;
import com.mmbeautyschool.mmapi.entity.enums.CourseSatatus;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourse();

    Course getCourseById(Long id);

    long newCourse(Course course);

    Course editCourse(Course course);

    void changeCourseStatus(Long id, CourseSatatus status);
}
