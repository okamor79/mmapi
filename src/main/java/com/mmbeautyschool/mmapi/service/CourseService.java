package com.mmbeautyschool.mmapi.service;


import com.mmbeautyschool.mmapi.entity.Course;
import com.mmbeautyschool.mmapi.entity.enums.CourseSatatus;
import com.mmbeautyschool.mmapi.exception.CourseAlreadyExistException;
import com.mmbeautyschool.mmapi.exception.CourseNotFoundException;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourse() throws CourseNotFoundException;

    Course getCourseById(Long id) throws CourseNotFoundException;

    long newCourse(Course course) throws CourseAlreadyExistException;

    Course editCourse(Course course);

    void changeCourseStatus(Long id, CourseSatatus status);
}
