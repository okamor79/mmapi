package com.mmbeautyschool.mmapi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmbeautyschool.mmapi.entity.Course;
import com.mmbeautyschool.mmapi.entity.Sale;
import com.mmbeautyschool.mmapi.exception.CourseAlreadyBuyException;
import com.mmbeautyschool.mmapi.repository.ClientRepository;
import com.mmbeautyschool.mmapi.repository.CourseRepository;
import com.mmbeautyschool.mmapi.repository.SaleRepository;
import com.mmbeautyschool.mmapi.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.*;

@Service
public class SaleServiceImpl implements SaleService {

    @Value("${key.public}")
    private String PUBLIC_KEY;

    @Value("${key.private}")
    private String PRIVATE_KEY;

    @Value("${api.version}")
    private int apiVersion;


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

    public static byte[] toSHA1(String hexstr) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(hexstr.getBytes("utf-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return md.digest();
    }

    public static String base64_encode(byte[] bytes) {
        return DatatypeConverter.printBase64Binary(bytes);
    }

    public static String base64_encode(String str) {
        return base64_encode(str.getBytes());
    }

    @Override
    public String newOrder(long clientId, long courseId) throws JsonProcessingException, CourseAlreadyBuyException {
        UUID uuid = UUID.randomUUID();
        String orderId = uuid.toString().replaceAll("-", "");
        Course course = courseRepository.getCourseById(courseId);

        String amount = String.valueOf(course.getPrice() * course.getDiscount());
        String descript = "Оплата курсу " + course.getUniqCode() + " - " + course.getName();

        List<Sale> clientOrders = saleRepository.getClentsOrder(
                clientRepository.getClientById(clientId),
                courseRepository.getCourseById(courseId));
//        if (!clientOrders.isEmpty()) {
//            throw new CourseAlreadyBuyException("COURSE_ALREDY_BUY");
//        }

        HashMap<String, String> params = new HashMap<>();
        params.put("action", "pay");
        params.put("amount", amount);
        params.put("currency", "UAH");
        params.put("description", descript);
        params.put("order_id", orderId);
        params.put("version", String.valueOf(apiVersion));
        params.put("language", "uk");
        params.put("public_key", PUBLIC_KEY);
        String json = new ObjectMapper().writeValueAsString(params);
        String json64 = Base64.getEncoder().encodeToString(json.getBytes());
        String sign_data = PRIVATE_KEY + json64 + PRIVATE_KEY;
        String signature = base64_encode(toSHA1(sign_data));
        HashMap<String, String> outSignatures = new HashMap<>();
        outSignatures.put("data", json64);
        outSignatures.put("singnature", signature);
        Sale order = new Sale();
        order.setClient(clientRepository.getClientById(clientId));
        order.setOrderId(orderId);
        order.setCourse(course);
        order.setDateBuy(new Date());
        saleRepository.save(order);
        return new ObjectMapper().writeValueAsString(outSignatures);
    }

    @Override
    public String verifyCourseSale(long clientId, long courseId) {
        Sale orderVerify = saleRepository.verifyPayCourse(
                clientRepository.getClientById(clientId),
                courseRepository.getCourseById(courseId), true
        );
        if (orderVerify == null) {
            return "NOT_VERIFY";
        }
        return "VERIFY";
    }

    @Override
    public Sale getAllOrders() {
        return (Sale) saleRepository.findAll();
    }
}
