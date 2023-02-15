package com.mmbeautyschool.mmapi.controller;

import com.mmbeautyschool.mmapi.entity.Sale;
import com.mmbeautyschool.mmapi.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class Controller {

    @GetMapping("/")
    public String getVersion() {

        return "Version 1.7 \n\nLast modify date: 15.02.2023";
    }

    @Autowired
    private SaleService saleService;

    @GetMapping("/payButton/{ClientId}/{id}")
    public String genPayButton(@PathVariable("ClientId") long clientId, @PathVariable("id") long courseId)  {
        return saleService.newOrder(clientId, courseId);
    }

    @GetMapping("/order/getAllOrders")
    public Sale getAllOrders() {
        return saleService.getAllOrders();
    }

}
