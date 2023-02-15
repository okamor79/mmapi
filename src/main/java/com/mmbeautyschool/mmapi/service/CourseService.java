package com.mmbeautyschool.mmapi.service;


import com.mmbeautyschool.mmapi.entity.Course;
import com.mmbeautyschool.mmapi.entity.enums.CourseSatatus;
import com.mmbeautyschool.mmapi.exception.CourseAlreadyExistException;
import com.mmbeautyschool.mmapi.exception.CourseNotFound;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourse() throws CourseNotFound;

    Course getCourseById(Long id) throws CourseNotFound;

    long newCourse(Course course) throws CourseAlreadyExistException;

    Course editCourse(Course course);

    void changeCourseStatus(Long id, CourseSatatus status);
}
