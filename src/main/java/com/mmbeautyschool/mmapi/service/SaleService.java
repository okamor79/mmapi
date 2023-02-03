package com.mmbeautyschool.mmapi.service;

import com.mmbeautyschool.mmapi.entity.Sale;

public interface SaleService {

    String newOrder(long clientID, long courseId);

    Sale getAllOrders();
}
