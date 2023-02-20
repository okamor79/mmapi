package com.mmbeautyschool.mmapi.service;


import com.mmbeautyschool.mmapi.entity.Sale;

public interface PaymentService {
    void checkPayment() throws Exception;

    void deleteNotPayment();

//    boolean checkCourseSale(long clientId, long courseId, boolean check);

    Sale changeStatus(long id);
}
