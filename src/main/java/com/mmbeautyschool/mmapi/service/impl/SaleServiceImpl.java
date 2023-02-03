package com.mmbeautyschool.mmapi.service.impl;
import com.liqpay.LiqPay;

import com.mmbeautyschool.mmapi.entity.Course;
import com.mmbeautyschool.mmapi.entity.Sale;
import com.mmbeautyschool.mmapi.repository.ClientRepository;
import com.mmbeautyschool.mmapi.repository.CourseRepository;
import com.mmbeautyschool.mmapi.repository.SaleRepository;
import com.mmbeautyschool.mmapi.service.SaleService;
import com.mmbeautyschool.mmapi.service.StaticValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final SaleRepository saleRepository;

    @Autowired
    private final ClientRepository clientRepository;

    public SaleServiceImpl(CourseRepository courseRepository, SaleRepository saleRepository, ClientRepository clientRepository) {
        this.courseRepository = courseRepository;
        this.saleRepository = saleRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public String newOrder(long clientId, long courseId) {
        UUID uuid = UUID.randomUUID();
        Course course = courseRepository.getCourseById(courseId);
        String orderId = uuid.toString().replaceAll("-","");
        String liqpayVer = String.valueOf(StaticValue.LIQPAY_VERSION);
        String amount = String.valueOf(course.getPrice()*course.getDiscount());
        String descript = "Оплата курсу " + course.getUniqCode() + "";
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "pay");
        params.put("amount", amount);
        params.put("currency", "UAH");
        params.put("description", descript);
        params.put("order_id", orderId);
        params.put("version", liqpayVer);
        params.put("language","ua");

        LiqPay liqpay = new LiqPay(StaticValue.PUBLIC_KEY, StaticValue.PRIVATE_KEY);
        String htmlFormCode = liqpay.cnb_form(params);

        Sale order = new Sale();
        System.out.println(clientRepository.getClientById(clientId));
        order.setClient(clientRepository.getClientById(clientId));
        order.setOrderId(orderId);
        order.setCourse(course);
        order.setDateBuy(new Date());
        saleRepository.save(order);
        return htmlFormCode;
    }

    @Override
    public Sale getAllOrders() {
        return (Sale) saleRepository.findAll();
    }
}
