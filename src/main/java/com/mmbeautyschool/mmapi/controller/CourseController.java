package com.mmbeautyschool.mmapi.controller;

import com.mmbeautyschool.mmapi.entity.Course;
import com.mmbeautyschool.mmapi.entity.enums.CourseSatatus;
import com.mmbeautyschool.mmapi.exception.CourseAlreadyExistException;
import com.mmbeautyschool.mmapi.exception.CourseNotFoundException;
import com.mmbeautyschool.mmapi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/course")
public class CourseController {

    @Autowired
    public CourseService courseService;

    @GetMapping("/list")
    public ResponseEntity<?> getCourseList() {
        try {
          return ResponseEntity.ok().body(courseService.getAllCourse());
        } catch (CourseNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> getCourseInfo(@PathVariable("id") Long id) {
        try {
          return ResponseEntity.ok().body(courseService.getCourseById(id));
        } catch (CourseNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewCourse(@RequestBody Course course) {
        try {
            return ResponseEntity.ok().body(courseService.newCourse(course));
        } catch (CourseAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/change_status/{id}/{status}")
    public void changeCourseStatus(@PathVariable("id") long id, @PathVariable("status") CourseSatatus status) {
        courseService.changeCourseStatus(id, status);
    }

    @PostMapping("/edit")
    public Course modifyCourse(Course course) {
        return courseService.editCourse(course);
    }

}
