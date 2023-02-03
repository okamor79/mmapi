package com.mmbeautyschool.mmapi.service.impl;

import com.mmbeautyschool.mmapi.entity.Course;
import com.mmbeautyschool.mmapi.entity.enums.CourseSatatus;
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
    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.getCourseById(id);
    }

    @Override
    public long newCourse(Course course) {
        Course newCourse = courseRepository.getCourseByUniqCode(course.getUniqCode());
        if (newCourse == null) {
            newCourse = course;
            newCourse.setUniqCode(newCourse.getUniqCode().toUpperCase());
            courseRepository.save(newCourse);
            return courseRepository.getCourseByUniqCode(newCourse.getUniqCode()).getId();
        } else {
            return 997;
        }
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
