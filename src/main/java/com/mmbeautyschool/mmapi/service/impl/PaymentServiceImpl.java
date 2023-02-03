package com.mmbeautyschool.mmapi.service.impl;

import com.mmbeautyschool.mmapi.entity.Sale;
import com.mmbeautyschool.mmapi.entity.enums.OrderStatus;
import com.mmbeautyschool.mmapi.repository.CourseRepository;
import com.mmbeautyschool.mmapi.repository.SaleRepository;
import com.mmbeautyschool.mmapi.service.PaymentService;
import com.mmbeautyschool.mmapi.service.StaticValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.*;

import com.liqpay.LiqPay;


@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private final SaleRepository saleRepository;

    @Autowired
    private  final CourseRepository courseRepository;

    public PaymentServiceImpl(SaleRepository saleRepository, CourseRepository courseRepository) {
        this.saleRepository = saleRepository;
        this.courseRepository = courseRepository;
    }

    private static final Collection<OrderStatus> orderStatuses = new ArrayList<>(Arrays.asList(OrderStatus.ORDER_WAIT, OrderStatus.ORDER_CANCEL));


    @Override
    @Scheduled(cron = "*/5 * * * * *")
    public void checkPayment() throws Exception {
        LiqPay liqPay = new LiqPay(StaticValue.PUBLIC_KEY, StaticValue.PRIVATE_KEY);
        Collection<OrderStatus> orderStatuses = new ArrayList<>(Arrays.asList(OrderStatus.ORDER_WAIT, OrderStatus.ORDER_CANCEL));
        List<Sale> orderList = saleRepository.getUnpaidOrders(orderStatuses);
        for (int i = 0; i<orderList.size();i++) {
            HashMap<String, String> params = new HashMap<>();
            Sale order = orderList.get(i);
            params.put("action","status");
            params.put("version", String.valueOf(StaticValue.LIQPAY_VERSION));
            params.put("order_id",order.getOrderId());
            HashMap<String, Object> resultRequest = (HashMap<String, Object>) liqPay.api("request", params);
            if (resultRequest.get("status").equals("success")) {
                order.setStatus(OrderStatus.ORDER_COMPLETE);
                String parseDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                        .format(new Date((Long) resultRequest.get("end_date")));
                Date payDate =  new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(parseDate);
                int duration = courseRepository.getCourseById(order.getCourse().getId()).getDayAccess();
                Date expDate = new Date();
                expDate.setTime(payDate.getTime() + (long) duration * 1000 * 60 * 60 * 24);
                order.setDatePayment(payDate);
                order.setExpireDate(expDate);
                order.setPayCheck(true);
                order.setCheckCode(String.valueOf(resultRequest.get("payment_id")));
            } else {
                order.setStatus(OrderStatus.ORDER_CANCEL);
            }
            saleRepository.save(order);
            params.clear();
        }

    }

    @Override
    @Scheduled(cron = "@daily")
    public void deleteNotPayment() {
        List<Sale> orderList = saleRepository.getUnpaidOrders(orderStatuses);
        saleRepository.deleteAll(orderList);
    }
}
