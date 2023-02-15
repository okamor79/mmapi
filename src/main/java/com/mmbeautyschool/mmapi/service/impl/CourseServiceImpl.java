package com.mmbeautyschool.mmapi.service.impl;

import com.mmbeautyschool.mmapi.entity.Course;
import com.mmbeautyschool.mmapi.entity.enums.CourseSatatus;
import com.mmbeautyschool.mmapi.exception.CourseAlreadyExistException;
import com.mmbeautyschool.mmapi.exception.CourseNotFoundException;
import com.mmbeautyschool.mmapi.repository.CourseRepository;
import com.mmbeautyschool.mmapi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourse() throws CourseNotFoundException {
        List<Course> courses = courseRepository.findAll();
        if (courses.isEmpty()) {
            throw new CourseNotFoundException("Courses not found");
        }
        return courses;
    }

    @Override
    public Course getCourseById(Long id) throws CourseNotFoundException {
        Course course = courseRepository.getCourseById(id);
        if (course == null) {
            throw new CourseNotFoundException("Course not found");
        }
        return course;
    }

    @Override
    public long newCourse(Course course) throws CourseAlreadyExistException {
        Course newCourse = courseRepository.getCourseByUniqCode(course.getUniqCode());
        if (newCourse != null) {
            throw new CourseAlreadyExistException("Course with this unique code is already exist");
        }
        newCourse = course;
        newCourse.setUniqCode(newCourse.getUniqCode().toUpperCase());
        courseRepository.save(newCourse);
        return newCourse.getId();

    }

    @Override
    public Course editCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void changeCourseStatus(Long id, CourseSatatus status) {
        Course course = courseRepository.getCourseById(id);
        course.setStatus(status);
        courseRepository.save(course);
    }
}
