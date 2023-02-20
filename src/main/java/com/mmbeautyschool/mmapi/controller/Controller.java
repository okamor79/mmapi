package com.mmbeautyschool.mmapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mmbeautyschool.mmapi.entity.Sale;
import com.mmbeautyschool.mmapi.exception.CourseAlreadyBuyException;
import com.mmbeautyschool.mmapi.service.PaymentService;
import com.mmbeautyschool.mmapi.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class Controller {

    @GetMapping("/")
    public String getVersion() {

        return "Version 1.7.2 \n\nLast modify date: 17.02.2023";
    }

    @Autowired
    private SaleService saleService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payButton/{ClientId}/{id}")
    public ResponseEntity genPayButton(@PathVariable("ClientId") long clientId, @PathVariable("id") long courseId) throws JsonProcessingException {
        try {
            return ResponseEntity.ok().body(saleService.newOrder(clientId, courseId));
        } catch (CourseAlreadyBuyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/payCheck/{ClientId}/{Courseid}")
    public ResponseEntity checkPay(@PathVariable("ClientId") long clientId, @PathVariable("Courseid") long courseId) throws JsonProcessingException {
        try {
            return ResponseEntity.ok().body(String.valueOf(saleService.verifyCourseSale(clientId, courseId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/payTest/{id}")
    public void changeOrderStatus(@PathVariable("id") long id) {
        paymentService.changeStatus(id);

    }

    @GetMapping("/order/getAllOrders")
    public Sale getAllOrders() {
        return saleService.getAllOrders();
    }

}
