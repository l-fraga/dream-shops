package com.dailycodework.dreamshops.service;

import com.dailycodework.dreamshops.dto.OrderDto;
import com.dailycodework.dreamshops.model.Order;

import java.util.List;

public interface IOrderInterface {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
