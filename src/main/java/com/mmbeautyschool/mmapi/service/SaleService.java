package com.mmbeautyschool.mmapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mmbeautyschool.mmapi.entity.Sale;
import com.mmbeautyschool.mmapi.exception.CourseAlreadyBuyException;

public interface SaleService {

    String newOrder(long clientID, long courseId) throws JsonProcessingException, CourseAlreadyBuyException;

    String verifyCourseSale(long clientID, long courseId);

    Sale getAllOrders();
}
