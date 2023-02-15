package com.mmbeautyschool.mmapi.controller;

import com.mmbeautyschool.mmapi.entity.Client;
import com.mmbeautyschool.mmapi.entity.Course;
import com.mmbeautyschool.mmapi.entity.Sale;
import com.mmbeautyschool.mmapi.entity.enums.CourseSatatus;
import com.mmbeautyschool.mmapi.entity.enums.UserStatus;
import com.mmbeautyschool.mmapi.service.ClientService;
import com.mmbeautyschool.mmapi.service.CourseService;
import com.mmbeautyschool.mmapi.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class Controller {

    @GetMapping("/")
    public String getVersion() {

        return "Version 1.7 \n\nLast modify date: 15.02.2023";
    }

    @Autowired
    private CourseService courseService;

    @Autowired
    private SaleService saleService;


    @GetMapping("/course/list")
    public List<Course> getCourseList() {
        return courseService.getAllCourse();
    }

    @GetMapping("/course/info/{id}")
    public ResponseEntity<Course> getCourseInfo(@PathVariable("id") Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.accepted().body(course);
    }

    @PostMapping("/course/change_status/{id}/{status}")
    public void changeCourseStatus(@PathVariable("id") long id, @PathVariable("status") CourseSatatus status) {
        courseService.changeCourseStatus(id, status);
    }

    @PostMapping("/course/add")
    public ResponseEntity<Void> addNewCourse(@RequestBody Course course) {
        long newCourse = courseService.newCourse(course);
        if (newCourse != 997) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.valueOf((int) newCourse));
        }
    }

    @PostMapping("/course/edit")
    public Course modifyCourse(Course course) {
        return courseService.editCourse(course);
    }

    @GetMapping("/payButton/{ClientId}/{id}")
    public String genPayButton(@PathVariable("ClientId") long clientId, @PathVariable("id") long courseId)  {
        return saleService.newOrder(clientId, courseId);
    }

    @GetMapping("/order/getAllOrders")
    public Sale getAllOrders() {
        return saleService.getAllOrders();
    }

}
