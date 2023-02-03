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

        return "Version 1.5";
    }

    @Autowired
    private ClientService clientService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SaleService saleService;


    @GetMapping("/client/list")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping("/client/register")
    public ResponseEntity<Void> registerNewClient(@RequestBody Client client) {
        long registered = clientService.newClient(client);
        if (registered != 999) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.valueOf((int) registered));
        }
    }

    @GetMapping("/client/login/{email}/{password}")
    public ResponseEntity<Client> loginClient(@PathVariable("email") String email, @PathVariable("password") String password) {
        Client client = clientService.clientLogin(email, password);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return ResponseEntity.accepted().body(client);
        }
    }

    @GetMapping("/client/reset/{email}")
    public ResponseEntity<Void> resetPassword(@PathVariable("email") String email) {
        boolean reset = clientService.resetPassword(email);
        return reset ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.valueOf(998));
    }

    @PostMapping("/client/edit")
    public Client editClientInfo(@RequestBody Client client) {
        return clientService.modifyClient(client);
    }

    @GetMapping("/client/info/{email}")
    public Client showClientInfo(@PathVariable("email") String email) {
        return clientService.getClientInfo(email);
    }

    @PostMapping("/client/change_status/{id}/{status}")
    public void changeUserStatus(@PathVariable("id") Long id, @PathVariable("status") UserStatus status) {
        clientService.changeClientStatus(id, status);
    }

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
